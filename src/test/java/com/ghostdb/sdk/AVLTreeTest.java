package com.ghostdb.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest {

	@Test
	void testAvlTree(){
		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");

		tree.insertNode(2, vp2);
		assertEquals(2, tree.getNode().getIndex());
		assertEquals("127.0.0.2", tree.getNode().getVp().getIp());
		assertEquals(2, tree.getNode().getVp().getIndex());
		assertNull(tree.getNode().getLeft().getNode());
		assertNull(tree.getNode().getRight().getNode());

		tree.insertNode(1, vp1);
		assertEquals(1,tree.getNode().getLeft().getNode().getIndex());
	}

	@Test
	void testAvlTreeRebalanceLeftRotation(){
		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");
		final VirtualPoint vp3 = new VirtualPoint(3, "127.0.0.3");

		tree.insertNode(3, vp3);
		tree.insertNode(2, vp2);
		tree.insertNode(1, vp1);

		assertEquals(2, tree.getNode().getIndex());
	}

	/*
	 * func TestAvlTreeRebalanceRightRotation(t *testing.T) {
	 * 	var tree *AVLTree = NewAvlTree()
	 * 	var vp1 *VirtualPoint = NewVirtualPoint("127.0.0.1", "1")
	 * 	var vp2 *VirtualPoint = NewVirtualPoint("127.0.0.2", "2")
	 * 	var vp3 *VirtualPoint = NewVirtualPoint("127.0.0.3", "3")
	 * 	var vp4 *VirtualPoint = NewVirtualPoint("127.0.0.4", "4")
	 * 	var vp5 *VirtualPoint = NewVirtualPoint("127.0.0.5", "5")
	 *
	 * 	tree.InsertNode("3", vp3)
	 * 	tree.InsertNode("2", vp2)
	 * 	tree.InsertNode("1", vp1)
	 * 	tree.InsertNode("4", vp4)
	 * 	tree.InsertNode("5", vp5)
	 *
	 * 	AssertEqual(t, tree.node.right.node.index, "4", "")
	 * }
	 */

	@Test
	void testAvlTreeRebalanceRightRotation() {
		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");
		final VirtualPoint vp3 = new VirtualPoint(3, "127.0.0.3");
		final VirtualPoint vp4 = new VirtualPoint(4, "127.0.0.4");
		final VirtualPoint vp5 = new VirtualPoint(5, "127.0.0.5");

		tree.insertNode(3, vp3);
		tree.insertNode(2, vp2);
		tree.insertNode(1, vp1);
		tree.insertNode(4, vp4);
		tree.insertNode(5, vp5);

		assertEquals(4, tree.getNode().getRight().getNode().getIndex());
	}

	@Test
	void testAvlTreeRemoveNode() {
		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");

		tree.insertNode(2, vp2);
		tree.insertNode(1, vp1);
		tree.removeNode(2);

		assertEquals(1, tree.getNode().getIndex());
	}

	@Test
	void testAvlTreeRemoveNodeWithTwoChildren() {
		//        2               3
		//       / \             / \
		//      1   4   ---->   1   4
		//         /
		//        3
		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");
		final VirtualPoint vp3 = new VirtualPoint(3, "127.0.0.3");
		final VirtualPoint vp4 = new VirtualPoint(4, "127.0.0.4");

		tree.insertNode(4, vp4);
		tree.insertNode(2, vp2);
		tree.insertNode(1, vp1);
		tree.insertNode(3, vp3);

		tree.removeNode(2);

		assertEquals(3, tree.getNode().getIndex());
	}

	@Test
	void testAvlTreeInOrderTraverse() {

		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");
		final VirtualPoint vp3 = new VirtualPoint(3, "127.0.0.3");
		final VirtualPoint vp4 = new VirtualPoint(4, "127.0.0.4");

		tree.insertNode(4, vp4);
		tree.insertNode(2, vp2);
		tree.insertNode(1, vp1);
		tree.insertNode(3, vp3);

		final Long[] output = tree.inOrderTraverse().toArray(Long[]::new);
		final Long[] expectedOutput = new Long[]{1L, 2L, 3L, 4L};

		assertArrayEquals(expectedOutput, output);
	}

	@Test
	void testAvlTreePostOrderTraverse() {

		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");
		final VirtualPoint vp3 = new VirtualPoint(3, "127.0.0.3");
		final VirtualPoint vp4 = new VirtualPoint(4, "127.0.0.4");

		tree.insertNode(4, vp4);
		tree.insertNode(2, vp2);
		tree.insertNode(1, vp1);
		tree.insertNode(3, vp3);

		final Long[] output = tree.postOrderTraverse().toArray(Long[]::new);
		final Long[] expectedOutput = new Long[]{1L, 3L, 4L, 2L};

		assertArrayEquals(expectedOutput, output);
	}

	@Test
	void testAvlTreeGetNodes() {

		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");
		final VirtualPoint vp3 = new VirtualPoint(3, "127.0.0.3");
		final VirtualPoint vp4 = new VirtualPoint(4, "127.0.0.4");

		tree.insertNode(4, vp4);
		tree.insertNode(2, vp2);
		tree.insertNode(1, vp1);
		tree.insertNode(3, vp3);

		final Long[] output = tree.getNodes().stream()
			.map(VirtualPoint::getIndex)
			.toArray(Long[]::new);

		assertEquals(4, output.length);

		final Long[] expectedOutput = new Long[]{1L, 2L, 3L, 4L};

		assertArrayEquals(expectedOutput, output);
	}

	@Test
	void testAvlTreeGetMinPair() {

		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");
		final VirtualPoint vp3 = new VirtualPoint(3, "127.0.0.3");
		final VirtualPoint vp4 = new VirtualPoint(4, "127.0.0.4");
		final VirtualPoint vp5 = new VirtualPoint(5, "127.0.0.5");
		final VirtualPoint vp6 = new VirtualPoint(6, "127.0.0.6");

		tree.insertNode(1, vp1);
		tree.insertNode(2, vp2);
		tree.insertNode(3, vp3);
		tree.insertNode(4, vp4);
		tree.insertNode(5, vp5);
		tree.insertNode(6, vp6);

		final Pair node = tree.minimumPair();
		assertEquals(1, node.index());
	}

	@Test
	void testAvlTreeGetNextPair() {

		final AVLTree tree = new AVLTree();
		final VirtualPoint vp1 = new VirtualPoint(1, "127.0.0.1");
		final VirtualPoint vp2 = new VirtualPoint(2, "127.0.0.2");
		final VirtualPoint vp3 = new VirtualPoint(3, "127.0.0.3");
		final VirtualPoint vp4 = new VirtualPoint(4, "127.0.0.4");
		final VirtualPoint vp5 = new VirtualPoint(5, "127.0.0.5");
		final VirtualPoint vp7 = new VirtualPoint(7, "127.0.0.7");

		tree.insertNode(1, vp1);
		tree.insertNode(2, vp2);
		tree.insertNode(3, vp3);
		tree.insertNode(4, vp4);
		tree.insertNode(5, vp5);
		tree.insertNode(7, vp7);

		final Node node = tree.nextPair(6);
		assertEquals(7, node.getIndex());
	}

}
