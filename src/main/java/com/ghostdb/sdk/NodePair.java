package com.ghostdb.sdk;

public class NodePair implements Pair {

	private final long index;
	private final VirtualPoint vp;

	public NodePair(long index, VirtualPoint vp) {
		this.index = index;
		this.vp = vp;
	}

	@Override
	public long getIndex() {
		return index;
	}

	@Override
	public VirtualPoint getValue() {
		return vp;
	}
}
