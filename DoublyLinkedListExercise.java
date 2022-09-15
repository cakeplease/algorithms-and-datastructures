public class Oppgave4 {
    public static void main(String[] args) {
        String number1 = args[0];
        String typeOfOperation = args[1];
        String number2 = args[2];

        DoublyLinkedList number1List = new DoublyLinkedList();
        DoublyLinkedList number2List = new DoublyLinkedList();
        DoublyLinkedList result = new DoublyLinkedList();

        for (int i = number1.length() - 1; i>=0; i--) {
            number1List.setBefore(Integer.parseInt(String.valueOf(number1.charAt(i))));
        }

        for (int i = number2.length() - 1; i>=0; i--) {
            number2List.setBefore(Integer.parseInt(String.valueOf(number2.charAt(i))));
        }

        int sizeList1 = number1List.getListSize();
        int sizeList2 = number2List.getListSize();

        int highestListSize = sizeList1;

        if (sizeList2>sizeList1) {
            highestListSize = sizeList2;
        }




        System.out.println(number1List.getListSize());
        System.out.println(number2List.getListSize());


        System.out.println(number1+" "+typeOfOperation + " "+number2);
    }
}

class Node {
    Node next;
    Node prev;
    int data;

    public Node(int data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public int getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }
}

class DoublyLinkedList {
    private int listSize = 0;
    private Node head = null;
    private Node tail = null;


    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.listSize = 0;
    }


    public void setBefore(int data) {
        this.head = new Node(data, this.head, null);

        if (this.tail == null) {
            this.tail = this.head;
        } else {
            this.head.next.prev = this.head;
        }

        ++listSize;
    }

    public void setAfter(int data) {
        Node newNode = new Node(data, null, tail);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }

        tail = newNode;
        ++listSize;
    }

    public int getListSize() {
        return listSize;
    }

    public Node getHead() {
        return head;
    }
    public Node getTail() {
        return tail;
    }
}
