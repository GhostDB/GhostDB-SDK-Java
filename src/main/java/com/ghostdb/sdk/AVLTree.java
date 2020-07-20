package com.ghostdb.sdk;

import java.util.zip.CRC32;


public class AVLTree {
    private Node node;
    private int height;
    private int balance;

    public AVLTree() {
        this.node = null;
        this.height = -1;
        this.balance = 0;
    }

    public void insertNode(CRC32 index, VirtualPoint vp) {

        if (this.node == null) {
            this.node = new Node(index, vp);
            this.node.setLeft(new AVLTree());
            this.node.setRight(new AVLTree());
        } else if (index.getValue() < this.node.getIndex().getValue()) {
            this.node.getLeft().insertNode(index, vp);
        } else if (index.getValue() > this.node.getIndex().getValue()) {
            this.node.getRight().insertNode(index, vp);
        }
    }


    public void removeNode(CRC32 index) {
        if (node != null) {
            if (index.getValue() == node.getIndex().getValue()) {

                if (this.node.getLeft().getNode() == null && node.getRight().getNode() == null) {
                    this.node = null;
                } else if (this.node.getLeft().getNode() == null) {
                    this.node = this.node.getRight().getNode();
                } else if (this.node.getRight().getNode() == null) {
                    this.node = this.node.getLeft().getNode();
                } else {
                    Node successor = this.node.getRight().getNode();
                    while (successor != null && successor.getLeft().getNode() != null) {
                        successor = successor.getLeft().getNode();
                    }
                    if (successor != null){
                        this.node.setIndex(successor.getIndex());
                        this.node.getRight().removeNode(successor.getIndex());
                    }
                }

            } else if (index.getValue() < node.getIndex().getValue()) {
                this.node.getLeft().removeNode(index);
            } else if (index.getValue() > node.getIndex().getValue()) {
                this.node.getRight().removeNode(index);
            }
            rebalance();
        }
    }

    private void rebalance() {
        this.updateHeights();
        this.updateBalances();

        while (this.balance < -1
                || this.balance > 1) {
            if (this.balance > 1) {
                if (this.node.getLeft().balance < 0) {
                    this.node.getLeft().rotateLeft();
                    this.updateHeights();
                    this.updateBalances();
                }
                this.rotateRight();
                this.updateHeights();
                this.updateBalances();
            }
            if (this.balance < -1) {
                if (this.node.getRight().balance > 0) {
                    this.node.getRight().rotateRight();
                    this.updateHeights();
                    this.updateBalances();
                }
                this.rotateLeft();
                this.updateHeights();
                this.updateBalances();
            }
        }
    }

    public void minimumPair() {
    }

    public void nextGTEPair() {
    }

    public void isBalanced() {
    }

    public void getNodes() {
    }

    public void inorderTraverse() {
    }

    public void preorderTraverse() {
    }

    public void postorderTraverse() {
    }

    public void minPair() {
    }

    public void nextPair(CRC32 key) {
    }

    private void _nextPair() {
    }

    private void updateHeights() {
        if (this.node != null) {
            if (this.node.getLeft() != null) {
                this.node.getLeft().updateHeights();
            }
            if (this.node.getRight() != null) {
                this.node.getRight().updateHeights();
            }
            this.height = Math.max(this.node.getLeft().getHeight(), this.node.getRight().getHeight());
        } else {
            this.height = -1;
        }
    }

    private void updateBalances() {
        if (this.node != null) {
            if (this.node.getLeft() != null) {
                this.node.getLeft().updateBalances();
            }
            if (this.node.getRight()!= null) {
                this.node.getRight().updateBalances();
            }
            this.balance = this.node.getLeft().getHeight() - this.node.getRight().getHeight();
        } else {
            this.balance = 0;
        }
    }

    private void rotateRight() {
        final Node newRoot = this.node.getLeft().getNode();
        final Node newLeftSub  = newRoot.getRight().getNode();
        final Node oldRoot  = this.node;
        this.node = newRoot;
        oldRoot.getLeft().node = newLeftSub;
        newRoot.getRight().node = oldRoot;
    }

    private void rotateLeft() {
        final Node newRoot = this.node.getRight().getNode();
        final Node newLeftSub = newRoot.getLeft().getNode();
        final Node oldRoot = this.node;

        this.node = newRoot;
        oldRoot.getRight().node = newLeftSub;
        newRoot.getLeft().node = oldRoot;
    }

    public Node getNode() {
        return node;
    }

    public int getHeight() {
        return height;
    }

    public int getBalance() {
        return balance;
    }

}