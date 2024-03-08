import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private int capacity;

    Node head;
    Node tail;
    // Map nodes can be linked as a doubly linked list such that
    //1) Most recently accessed item is at the head.
    //2) Each Get or Put method will bring the node to the head of the list.
    private Map<String, Node> map = new HashMap<>(capacity);

    LRUCache(int capacity) {
        this.capacity = capacity;

    }

    public String get(String key) {
        Node node = map.get(key);
        if(node != null) {
            bringToFront(node);
            return node.value;
        }
        return null;
    }

    public void put(String key, String value) {
        //evict in case the size is equal to the capacity.
        //eviction means removing the node pointed by Tail.
        // removing from the Map would be done by map.remove(key)
        Node node = map.get(key);
        if(node != null) {
            node.value = value;
            bringToFront(node);
        }else{
            node = new Node(key, value);
            if(map.size() == capacity) { // Head -> n1 -> n2 -> n3 -> Tail
                Node toBeDeleted = tail;
                tail = tail.previous;
                String key1 = toBeDeleted.key;
                map.remove(key1);
                System.out.println("Removed key " + key1);
            }

            map.put(key, node);
            addNode(node);
        }

    }

    private void bringToFront (Node node) {
        System.out.println("Bringing to front " + node.key);
        if(head == node) {
            return;
        }

        Node p = node.previous;
        Node n = node.next;
        node.next = null;
        node.previous = null;

        if(n == null) {
            p.next = null;
            tail = p;
            addNode(node);
            return;
        }
        p.next = n;
        n.previous = p;
        addNode(node);
    }

    private void addNode(Node node) {
        System.out.println("Adding to front " + node.key);
        if(head == null) {
            head = node;
            tail = node;
        }else {
            node.next = head;
            head.previous = node;
            head = node;
        }
    }

    //delete at the end of list
    private void deleteNode() {
        if(tail == null) {
            return;
        }
        //only one item to delete, affects both head and tail
        if(tail.previous == null) {

            head = null;
            tail = null;
        }else {
            Node temp = tail.previous;
            temp.next = null;
            tail.previous = null;
            tail = temp;
        }
    }

    private static class Node {
        String key;
        String value;
        Node next;
        Node previous;

        Node(){

        }

        Node(String key, String value){
            this.key = key;
            this.value = value;
        }
    }

}
