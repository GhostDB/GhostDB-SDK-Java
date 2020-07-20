package com.ghostdb.sdk;

import java.util.zip.CRC32;

/**
 * Node
 */
public class Node {

    private CRC32 index;
    private final VirtualPoint vp;
    private AVLTree left;
    private AVLTree right;

    public Node(CRC32 index, VirtualPoint vp) {
        this.index = index;
        this.vp = vp;
    }

    public CRC32 getIndex() {
        return index;
    }

    public void setIndex(CRC32 index) {
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