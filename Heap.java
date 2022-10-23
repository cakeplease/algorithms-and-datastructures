/*class Heap {
    int length;
    Node nodes[];

    public Heap(Node[] n) {
        nodes = n;
        length = n.length;
        heapSort();
    }

    public void heapSort() {
        createHeap();
        int l = length;
        while(length > 1) {
            Node x = getMin();
            nodes[length] = x;
        }
        length = l;
    }

    public void createHeap() {
        int i = length / 2;
        while(i--> 0) {
            fixHeap(i);
        }
    }

    int over(int i) {
        return (i-1)>>1;
    }

    int left(int i) {
        return (i<<1)+1;
    }

    int right(int i) {
        return (i+1)<<1;
    }

    public static void swap(Node[] t, int i, int j) {
        Node k = t[j];
        t[j] = t[i];
        t[i] = k;
    }

    public Node getMin() {
        Node min = nodes[0];
        nodes[0] = nodes[--length];
        fixHeap(0);
        return min;
    }

    public void fixHeap(int i) {
        int m = left(i);
        if (m < length) {
            int h = m+1;
            if (h < length && nodes[h].distance < nodes[m].distance) {
                m=h;
            }
            if (nodes[m].distance < nodes[i].distance) {
                swap(nodes,i,m);
                fixHeap(m);
            }
        }
    }

    @Override
    public String toString() {
        String nodesContent = "";
        for (Node node : nodes) {
            nodesContent+= node+"\n";
        }
        return "Heap size: "+length+"\n" +
                "Content: \n" + nodesContent;
    }
}*/
