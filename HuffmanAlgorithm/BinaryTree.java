package HuffmanAlgorithm;

public class BinaryTree {
    Node root;

    public BinaryTree() {
        root = null;
    }

    public void insert(char character, int frequency) {
        root = insert(root, character, frequency, null);
    }

    public int getHeight(Node node) {
        if (node == null)
            return -1;
        else {
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);

            return Math.max(leftHeight,rightHeight) + 1;
        }
    }

    //TODO change insert method accordingly to exercise
    public Node insert(Node root, char character, int frequency, Node parent) {
        /*if (root == null) {
            root = new Node(character, frequency, parent);
            return root;
        } else if (frequency.compareTo(root.frequency) < 0) {
            root.left = insert(root.left, character, frequency, root);

        } else if (frequency.compareTo(root.frequency) > 0) {
            root.right = insert(root.right, character, frequency, root);
        }*/
        return root;
    }
}

class Node {
    char character;
    int frequency;
    Node left;
    Node right;
    Node parent;

    public Node(char character, int freq, Node parent) {
        this.character = character;
        this.frequency = freq;
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
}


