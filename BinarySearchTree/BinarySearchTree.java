package BinarySearchTree;
import java.util.LinkedList;

public class BinarySearchTree {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(String word) {
        root = insert(root, word, null);
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

    public void levelOrder() {
        levelOrder(root);
    }

    public boolean isQueueAllNull(LinkedList<Node> queue) {
        boolean result = true;
        Object[] nodes = queue.toArray();
        for (Object node : nodes) {
            if (node != null) {
                result = false;
            }
        }
        return result;
    }

    public void levelOrder(Node root) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        double space = Math.max(Math.pow(2, getHeight(root)), 64);
        int counter = 0;
        while(!queue.isEmpty()) {
           if (counter > 3) {
                break;
            }
            if (!isQueueAllNull(queue)) {
                int nodeCount = queue.size();

                while (nodeCount > 0) {
                    if (!isQueueAllNull(queue)) {
                        Node thisNode = queue.pollFirst();
                        if (thisNode != null) {
                            Node.format(thisNode.word, space);
                            queue.add(thisNode.left);
                            queue.add(thisNode.right);

                        } else {
                            Node.format("", space);
                            queue.add(null);
                            queue.add(null);
                        }

                        nodeCount--;
                    } else {
                        nodeCount = 0;
                    }
                }

                space = space/2;
                System.out.println();
            } else {
                queue.clear();
            }
            counter++;
        }
    }

    //Compile this program and send the words in args like this: java BinarySearchTree her kan du skrive ord med mellomrom
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        for (String word : args) {
            tree.insert(word);
        }

        tree.levelOrder();

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

    public static void format(String value, double space) {
        if (value.length() != 0) {
            space = (space - value.length()) / 2;
            for (int i=0; i<space; i++) {
                System.out.print(" ");
            }
            System.out.print(value);

            for (int i=0; i<space; i++) {
                System.out.print(" ");
            }
        } else {
            for (int i=0; i<space; i++) {
                System.out.print(" ");
            }
        }
    }

    @Override
    public String toString() {
        return word;
    }
}

