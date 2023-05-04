package wol;

import java.util.Comparator;
public class ULTreeMap<K,V> implements java.lang.Cloneable{
    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private Comparator<K> comparator;
    private int size;
    public ULTreeMap() {
        root = null;
    }


    public ULTreeMap(java.util.Comparator<K> compare) {
        root = null;
        comparator = compare;
        size = 0;
    }

    public ULTreeMap<K,V> clone() {
        ULTreeMap<K, V> clone = new ULTreeMap<K, V>();
        clone.root = cloneNode(root);
        clone.comparator = this.comparator;
        clone.size = this.size;
        return clone;
    }

    private Node cloneNode(Node node) {
        if (node == null) {
            return null;
        }
        Node newNode = new Node(node.key, node.value);
        newNode.left = cloneNode(node.left);
        newNode.right = cloneNode(node.right);
        return newNode;
    }

    public void insert(K key, V value) throws DuplicateKeyException{
        // Throw NullPointerException if the key is null
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        // Call the private recursive insert method
        root = insert(root, key, value);
    }
    private Node insert(Node node, K key, V value) throws DuplicateKeyException {
        // If the current node is null, create a new node with the given key and value
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        // Compare the given key with the key of the current node
        int cmp = compare(key, node.key);

        // If the given key is less than the key of the current node, recursively insert the node to the left
        if (cmp < 0) {
            node.left = insert(node.left, key, value);
        }
        // If the given key is greater than the key of the current node, recursively insert the node to the right
        else if (cmp > 0) {
            node.right = insert(node.right, key, value);
        }
        // If the given key is equal to the key of the current node, throw DuplicateKeyException
        else {
            throw new DuplicateKeyException("Duplicate key found: " + key);
        }

        return node;
    }

    public void put(K key, V value){
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = compare(key, node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    public boolean containsKey(K key){
        return get(root, key) != null;
    }

    public V lookup(K key){
        Node node = get(root, key);
        if (node != null) {
            return node.value;
        } else {
            return null;
        }
    }


    public void erase(K key){
        root = erase(root, key);
    }
    private Node erase(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = compare(key, node.key);
        if (cmp < 0) {
            node
                    .left = erase(node.left, key);
        } else if (cmp > 0) {
            node.right = erase(node.right, key);
        } else {
            if (node.left == null) {
                size--;
                return node.right;
            } else if (node.right == null) {
                size--;
                return node.left;
            } else {
                Node min = node.right;
                while (min.left != null) {
                    min = min.left;
                }
                node.key = min.key;
                node.value = min.value;
                node.right = erase(node.right, min.key);
            }
        }
        return node;
    }

    public java.util.Collection<K> keys(){
        java.util.List<K> list = new java.util.ArrayList<K>();
        inorderTraversal(root, list);
        return list;
    }
    private void inorderTraversal(Node node, java.util.List<K> list) {
        if (node != null) {
            inorderTraversal(node.left, list);
            list.add(node.key);
            inorderTraversal(node.right, list);
        }
    }

    public int size(){
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    public void clear(){
        root = null;
        size = 0;
    }
    private int compare(K a, K b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable<K>) a).compareTo(b);
        }
    }

    private Node get(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = compare(key, node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return get(node.right, key);
        }
    }
}
