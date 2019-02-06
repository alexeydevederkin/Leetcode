package leetcode;

import java.util.HashMap;
import java.util.Map;


/*
 *  Implementation for Least Recently Used (LRU) cache
 *
 *  https://en.wikipedia.org/wiki/Cache_replacement_policies#LRU
 *
 */
public class LRUCache {
    class LRUNode {
        int key;
        int val;
        LRUNode next;
        LRUNode prev;

        LRUNode() {}

        LRUNode(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    class LRUList {
        private LRUNode head = new LRUNode();
        private LRUNode tail = new LRUNode();

        LRUList() {
            head.next = tail;
            tail.prev = head;
        }

        void moveToTail(LRUNode movingNode) {
            // if the node exists in list, then cut it
            if (movingNode.prev != null) {
                movingNode.prev.next = movingNode.next;
                movingNode.next.prev = movingNode.prev;
            }

            // append the node as last element in list
            // head <-> [node1] <-> [node2] <-> [prevLastNode] <-> tail
            // head <-> [node1] <-> [node2] <-> [prevLastNode] <-> [movingNode] <-> tail
            LRUNode prevLastNode = tail.prev;
            movingNode.next = tail;
            movingNode.prev = prevLastNode;
            prevLastNode.next = tail.prev = movingNode;
        }

        LRUNode removeHead() {
            // head <-> tail
            if (head.next == tail) {
                return null;
            }

            // head <-> [first] <-> [second] <-> [third] <-> tail
            // head <-> [second] <-> [third] <-> tail
            LRUNode first = head.next;
            LRUNode second = first.next;

            head.next = second;
            second.prev = head;

            return first;
        }
    }

    private Map<Integer, LRUNode> cache = new HashMap<>();
    private LRUList list = new LRUList();
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) return -1;
        LRUNode node = cache.get(key);
        list.moveToTail(node);
        return node.val;
    }

    public void put(int key, int val) {
        LRUNode node;

        if (cache.containsKey(key)) {
            // if key exists - update value
            node = cache.get(key);
            node.val = val;
        } else {
            // maintaining correct size of cache
            if (cache.size() >= capacity) {
                LRUNode leastRecentlyUsed = list.removeHead();
                cache.remove(leastRecentlyUsed.key);
            }

            // if key does not exist - create
            node = new LRUNode(key, val);
            cache.put(key, node);
        }

        list.moveToTail(node);
    }
}
