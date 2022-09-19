package BinarySearchTree;

import java.util.ArrayList;
import java.util.HashMap;

public class BinarySearchTree {
    Node root;

    public static ArrayList<Node> nodes2 = new ArrayList<>();
    public static ArrayList<Node> nodes1 = new ArrayList<>();
    public static ArrayList<Node> nodes0 = new ArrayList<>();

    public BinarySearchTree() {
        root = null;
    }
    public boolean clear() {
        return root == null;
    }



    public void insert(String word) {
        root = insert(root, word, null);
    }

    public Node insert(Node root, String word, Node parent) {
        if (root == null) {
            root = new Node(word, parent);
            return root;
        } else if (word.compareTo(root.word) < 0) {
            root.left = insert(root.left, word, root);

        } else if (word.compareTo(root.word) > 0) {
            root.right = insert(root.right, word, root);
        }
        return root;
    }


    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        for (String word : args) {
            tree.insert(word);
        }
        tree.traverse();

        System.out.println(nodes0);
        System.out.println(nodes1);
        System.out.println(nodes2);

    }

    public void traverse() {
        traverse(root);
    }

    public void traverse(Node root) {

        if (root != null) {
            switch (Node.getDepth(root)) {
                case 0:
                    nodes0.add(root);
                    break;
                case 1:
                    nodes1.add(root);
                    break;
                case 2:
                    nodes2.add(root);
                    break;
            }
            //System.out.println(root.word);
            traverse(root.left);
            traverse(root.right);
        }


        //sortedNodes.forEach((k,v) -> System.out.println("level: "+k+" value:"+v));

        /*for (HashMap.Entry<Integer, Node> entry : sortedNodes.entrySet()) {
            Integer level = entry.getKey();
            Node node = entry.getValue();

            //System.out.println(level);

            for (int i = 0; i<4; i++) {
                if (level == i) {
                    System.out.println(node);
                }
            }
            // ...
        }*/

    }

}

class Node {
    String word;
    Node left;
    Node right;
    Node parent;

    public Node(String word, Node parent) {
        this.word = word;
        this.left = null;
        this.right = null;
        this.parent = parent;
    }

    public static int getDepth(Node node) {
        int depthCounter = -1;
        while (node != null) {
            node = node.parent;
            depthCounter++;
        }

        return depthCounter;
    }

    @Override
    public String toString() {
        return word;
    }
}