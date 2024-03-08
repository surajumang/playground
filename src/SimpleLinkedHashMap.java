import java.util.ArrayList;
import java.util.List;

public class SimpleLinkedHashMap {

    Node head;
    Node tail;
    int capacity = 16;
    private List< List<Node>> table = new ArrayList<>(capacity);

    SimpleLinkedHashMap() {
        for (int i = 0; i < capacity; i++) {
            table.add(new ArrayList<>());
        }
    }

    public String get(String key) {
        int hash = key.hashCode();
        List<Node> nodes = table.get(hash % capacity);
        for(Node node : nodes) {
            if(node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    private Node getNode(String key) {
        int hash = key.hashCode();
        List<Node> nodes = table.get(hash % capacity);
        for(Node node : nodes) {
            if(node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    //Need to maintain insertion order
    public void put(String key, String value) {
        Node node = getNode(key);
        if(node == null) {
            //new insertion
            Node n = new Node(key, value);
            List<Node> nodes = table.get(key.hashCode() % capacity);
            nodes.add(n);
            insertNode(n);
        }else{
            //update
            node.value = value;
        }
    }

    //Insert this at the tail of the Doubly Linked list
    private void insertNode(Node node) {
        //if no items yet, first item.
        if(head == null) {
            head = node;
            tail = node;
        }else { // tail N
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    private void removeNode(Node node) {
        //removing the only item
        if(head == null) return;
        if(head == tail) {
            head = null;
            tail = null;
        }else if(head == node) {
            head.next.prev = null;
            head = head.next;
            node.next = null;
        }else if(tail == node) {
            tail.prev.next = null;
            tail = tail.prev;
            node.prev = null;
        }else {
            Node p = node.prev;
            Node n = node.next;
            node.prev = null;
            node.next = null;

            p.next = n;
            n.prev = p;
        }
    }

    public void iterate() {
        Node current = head;
        while(current != null) {
            System.out.printf("FOund element with key: %s and value %s%n", current.key, current.value);
            current = current.next;
        }
    }

    public void remove(String key) {
        Node node = getNode(key);
        if(node != null) {
            removeNode(node);
        }
        List<Node> nodes = table.get(key.hashCode() % capacity);
        nodes.remove(node);
    }

    static class Node {
        Node prev;
        Node next;
        String key;
        String value;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
