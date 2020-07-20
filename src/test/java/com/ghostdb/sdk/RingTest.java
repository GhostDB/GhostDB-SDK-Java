package com.ghostdb.sdk;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class RingTest {

	@Test
	void testKeyHash(){

		String key = "10.23.20.2";
		long hash = Ring.keyHash(key);
		assertEquals("d80ceccd", Long.toHexString(hash));

		key = "10.23.34.4";
		hash = Ring.keyHash(key);
		assertEquals("8eda8641", Long.toHexString(hash));
	}

	@Test
	void testRingAddNode(){
		final Ring ring = new Ring(null, 1);
		ring.add("10.23.20.2");
		ring.add("10.23.34.4");
		String key1 = "TEST_KEY";
		String key2 = "ANOTHER_KEY";

		Pair point = ring.getPoint(key1);
		assertEquals("10.23.34.4", point.getValue().getIp());
		point = ring.getPoint(key2);
		assertEquals("10.23.20.2", point.getValue().getIp());
	}

	@Test
	void testRingDeleteNode(){
		final Ring ring = new Ring(null, 1);
		ring.add("10.23.20.2");
		ring.add("10.23.34.4");
		String key1 = "TEST_KEY";
		String key2 = "ANOTHER_KEY";

		ring.delete("10.23.20.2");
		Pair point = ring.getPoint(key1);
		assertEquals("10.23.34.4", point.getValue().getIp());
		point = ring.getPoint(key2);
		assertEquals("10.23.34.4", point.getValue().getIp());
	}

	@Test
	void testRingInitFromConfig() throws URISyntaxException {
		final Path clusterConfig = Paths.get(getClass().getClassLoader().getResource("testconfig.conf").getFile());
		final Ring ring = new Ring(clusterConfig, 1);
		final long[] index = ring.getPoints().stream().mapToLong(VirtualPoint::getIndex).toArray();
		final long[] expectedIndex = {Long.parseUnsignedLong("95412376",16), Long.parseUnsignedLong("af102aa1",16)};

		assertArrayEquals(expectedIndex, index);
	}

}
