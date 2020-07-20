package com.ghostdb.sdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AVLTree {

	private Node node = null;
	private int height = -1;
	private int balance = 0;

	public AVLTree() {
	}

	public void insertNode(long index, VirtualPoint vp) {

		if (this.node == null) {
			this.node = new Node(index, vp);
			this.node.setLeft(new AVLTree());
			this.node.setRight(new AVLTree());
		} else if (index < this.node.getIndex()) {
			this.node.getLeft().insertNode(index, vp);
		} else if (index > this.node.getIndex()) {
			this.node.getRight().insertNode(index, vp);
		}
		rebalance();
	}


	public void removeNode(long index) {
		if (node != null) {
			if (index == node.getIndex()) {

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
					if (successor != null) {
						this.node.setIndex(successor.getIndex());
						this.node.getRight().removeNode(successor.getIndex());
					}
				}

			} else if (index < node.getIndex()) {
				this.node.getLeft().removeNode(index);
			} else if (index > node.getIndex()) {
				this.node.getRight().removeNode(index);
			}
			rebalance();
		}
	}

	//TODO not sure if this should return Node
	public Pair minimumPair() {
		if (node == null)
			return null;
		Node currentNode = node;
		while (currentNode.getLeft().getNode() != null) {
			currentNode = currentNode.getLeft().getNode();
		}
		return new NodePair(currentNode.getIndex(), currentNode.getVp());
	}

	public Node nextPair(long key) {
		Node gteNode = this.getNextNode(node, key);
		if (gteNode == null) {
			return null;
		}
		return new Node(gteNode.getIndex(), gteNode.getVp());
	}

	public boolean isBalanced() {
		if (node == null) return true;
		int lst = node.getLeft().getHeight();
		int rst = node.getRight().getHeight();

		return (Math.abs(lst - rst) <= 1) && node.getLeft().isBalanced() && node.getRight().isBalanced();
	}

	public List<VirtualPoint> getNodes() {
		if (node == null) return Collections.emptyList();
		final ArrayList<VirtualPoint> nodes = new ArrayList<>();
		nodes.addAll(node.getLeft().getNodes());
		nodes.add(node.getVp());
		nodes.addAll(node.getRight().getNodes());
		return nodes;
	}

	public List<Long> inOrderTraverse() {
		if (node == null) return Collections.emptyList();
		final ArrayList<Long> keys = new ArrayList<>();
		keys.addAll(node.getLeft().inOrderTraverse());
		keys.add(node.getVp().getIndex());
		keys.addAll(node.getRight().inOrderTraverse());
		return keys;
	}

	public List<Long> preOrderTraverse() {
		if (node == null) return Collections.emptyList();
		final ArrayList<Long> keys = new ArrayList<>();
		keys.add(node.getVp().getIndex());
		keys.addAll(node.getLeft().preOrderTraverse());
		keys.addAll(node.getRight().preOrderTraverse());
		return keys;
	}

	public List<Long> postOrderTraverse() {
		/*
		func (this *AVLTree) postOrder(params *Params) []string {
	if (params.node != nil) {
		var old *Node = params.node

		params.node = old.left.node
		this.postOrder(params)

		params.node = old.right.node
		this.postOrder(params)

		params.output = append(params.output, old.index)
	}
		 */
		if (node == null) return Collections.emptyList();
		final ArrayList<Long> keys = new ArrayList<>();
		keys.addAll(node.getLeft().postOrderTraverse());
		keys.addAll(node.getRight().postOrderTraverse());
		keys.add(node.getVp().getIndex());
		return keys;
	}

	private Node getNextNode(Node node, long key) {
		if (node == null) {
			return null;
		}
		Node after = null;
		if (key < node.getIndex()) {
			if (node.getLeft() != null) {
				after = getNextNode(node.getLeft().getNode(), key);
				if (after == null)
					after = node;
			}
		} else if (key > node.getIndex()) {
			if (node.getRight() != null) {
				after = getNextNode(node.getRight().getNode(), key);
			}
		} else if (node.getIndex() == key) {
			after = node;
		}
		return after;
	}

	private void rebalance() {
		this.updateHeights();
		this.updateBalances();

		while (this.balance < -1 || this.balance > 1) {
			if (this.balance > 1) {
				if (this.node.getLeft().getBalance() < 0) {
					this.node.getLeft().rotateLeft();
					this.updateHeights();
					this.updateBalances();
				}
				this.rotateRight();
				this.updateHeights();
				this.updateBalances();
			}
			if (this.balance < -1) {
				if (this.node.getRight().getBalance() > 0) {
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

	private void updateHeights() {
		if (this.node != null) {
			if (this.node.getLeft() != null) {
				this.node.getLeft().updateHeights();
			}
			if (this.node.getRight() != null) {
				this.node.getRight().updateHeights();
			}
			this.height = 1 + Math.max(this.node.getLeft().getHeight(), this.node.getRight().getHeight());
		} else {
			this.height = -1;
		}
	}

	private void updateBalances() {
		if (this.node != null) {
			if (this.node.getLeft() != null) {
				this.node.getLeft().updateBalances();
			}
			if (this.node.getRight() != null) {
				this.node.getRight().updateBalances();
			}
			this.balance = this.node.getLeft().getHeight() - this.node.getRight().getHeight();
		} else {
			this.balance = 0;
		}
	}

	private void rotateRight() {
		final Node newRoot = this.node.getLeft().getNode();
		final Node newLeftSub = newRoot.getRight().getNode();
		final Node oldRoot = this.node;
		this.node = newRoot;
		oldRoot.getLeft().node = newLeftSub;
		newRoot.getRight().node = oldRoot;
	}

	private void rotateLeft() {
		final Node newRoot = this.node.getRight().getNode();
		final Node newRightSub = newRoot.getLeft().getNode();
		final Node oldRoot = this.node;

		this.node = newRoot;
		oldRoot.getRight().node = newRightSub;
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
