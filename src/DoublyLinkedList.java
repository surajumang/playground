public class DoublyLinkedList {

    Node head;
    Node tail;

    DoublyLinkedList() {
        //Initially Head and tail are null

    }

    //Adds the given node at Head.
    private void addNode(Node node) {
        if(head == null) {
            head = node;
            tail = node;
        }else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    //delete at the end of list
    private void deleteNode() {
        if(tail == null) {
            return;
        }
        //only one item to delete, affects both head and tail
        if(tail.prev == null) {

            head = null;
            tail = null;
        }else {
            Node temp = tail.prev;
            temp.next = null;
            tail.prev = null;
            tail = temp;
        }
    }

    static class Node {
        String val;
        Node next;
        Node prev;

        Node() {

        }

        Node(String val) {
            this.val = val;
        }
    }
}
