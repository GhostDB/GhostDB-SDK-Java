package com.ghostdb.sdk;

/**
 * Node
 */
public class Node {

    private long index;
    private final VirtualPoint vp;
    private AVLTree left;
    private AVLTree right;

    public Node(long index, VirtualPoint vp) {
        this.index = index;
        this.vp = vp;
    }

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public VirtualPoint getVp() {
        return vp;
    }

    public AVLTree getLeft() {
        return left;
    }

    public void setLeft(AVLTree left) {
        this.left = left;
    }

    public AVLTree getRight() {
        return right;
    }

    public void setRight(AVLTree right) {
        this.right = right;
    }
}
