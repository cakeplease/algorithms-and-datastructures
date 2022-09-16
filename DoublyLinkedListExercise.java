import static java.util.Objects.isNull;

public class DoublyLinkedListExercise {
    public static void main(String[] args) {
        String number1 = args[0];
        String typeOfOperation = args[1];
        String number2 = args[2];


        DoublyLinkedList higherNumberList = new DoublyLinkedList();
        DoublyLinkedList lowerNumberList = new DoublyLinkedList();
        DoublyLinkedList result = new DoublyLinkedList();

        String highestNumber = number1;
        String lowestNumber = number2;

        if (number1.length()<number2.length()) {
            highestNumber = number2;
            lowestNumber = number1;
        }

        for (int i = highestNumber.length() - 1; i >= 0; i--) {
            int index = i - (highestNumber.length()-lowestNumber.length());
            if (index>= 0) {
                lowerNumberList.setBefore(Integer.parseInt(String.valueOf(lowestNumber.charAt(index))));
            } else {
                lowerNumberList.setBefore(0);
            }

            higherNumberList.setBefore(Integer.parseInt(String.valueOf(highestNumber.charAt(i))));
        }

        /*
        Iterator.iterate(higherNumberList);
        Iterator.iterate(lowerNumberList);
        */

        int sizeList1 = higherNumberList.getListSize();
        int sizeList2 = lowerNumberList.getListSize();

        int highestListSize = sizeList1;

        if (sizeList2>sizeList1) {
            highestListSize = sizeList2;
        }


        boolean rest = false;
        int i = 0;

        Node tail1 = higherNumberList.getTail();
        Node tail2 = lowerNumberList.getTail();

        int sum = tail1.getData() + tail2.getData();

        //If sum is over 9
        if (sum > 9) {
            result.setBefore(sum%10);
            rest = true;
        } else {
            result.setBefore(sum);
        }

        Node prev1 = tail1.getPrev();
        Node prev2 = tail2.getPrev();
        while (i < (highestListSize - 1)) {
            sum = prev1.getData() + prev2.getData();

            if (rest) {
                sum += 1;
            }
            //If sum is over 9
            if (sum > 9) {
                result.setBefore(sum%10);
                rest = true;
            } else {
                result.setBefore(sum);
                rest = false;
            }

            if (!isNull(prev1.prev) && !isNull(prev2.prev)) {
                prev1 = prev1.getPrev();
                prev2 = prev2.getPrev();
            }

            i++;
        }

        if (rest) {
            result.setBefore(1);
        }

        System.out.println(number1+" "+typeOfOperation + " "+number2);
        Iterator.iterate(result);

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

    @Override
    public String toString() {
        return "Data: "+data;
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


class Iterator {

    public static void iterate(DoublyLinkedList list) {
        Node node = list.getHead();
        System.out.print(node.data);

        while (node.next != null) {
            node = node.next;
            System.out.print(node.data);
        }
        System.out.println("");
    }
}