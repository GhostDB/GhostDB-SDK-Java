package com.ghostdb.sdk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.CRC32;

public class Ring {

	private final int replicas;
	private final AVLTree ring;

	public Ring(Path clusterConfig, int replicas){
		ring = new AVLTree();
		this.replicas = replicas;
		if (clusterConfig != null){
			initRing(clusterConfig);
		}
	}

	private void initRing(Path clusterConfig) {
		final List<String> nodes;
		try {
			nodes = Files.readAllLines(clusterConfig);
		} catch (IOException e) {
			//TODO replace exception
			throw new RuntimeException(e);
		}
		if (!nodes.isEmpty()){
			nodes.forEach(this::add);
		}else{
			//TODO replace exception
			throw new RuntimeException("noElements");
		}
	}

	public void add(String node) {
		for (int i = 0; i < replicas; i++) {
			final long index = keyHash(node, i);
			final VirtualPoint virtualPoint = new VirtualPoint(index, node);
			ring.insertNode(index, virtualPoint);
		}
	}

	public void delete(String node){
		for (int i = 0; i < replicas; i++) {
			long index = keyHash(node, i);
			ring.removeNode(index);
		}
	}

	public Pair getPoint(String key){
		if (ring.inOrderTraverse().isEmpty()){
			return null;
		}
		final long index = keyHash(key);
		Pair node = ring.nextPair(index);
		if (node == null){
			node = ring.minimumPair();
		}
		return node;
	}

	public List<VirtualPoint> getPoints(){
		return ring.getNodes();
	}

	public static long keyHash(String key, int index) {
		return keyHash(String.format("%s:%d", key, index));
	}

	public static long keyHash(String key) {
		//TODO check compatibility with other SDKs
		final CRC32 crc32 = new CRC32();
		crc32.update(key.getBytes());
		return crc32.getValue();
	}

}
