package wol;

public class ULTreeMap<K,V> implements java.lang.Cloneable{
    public ULTreeMap(){}

    public ULTreeMap(java.util.Comparator<K> compare){}

    public ULTreeMap<K,V> clone(){}

    public void insert(K key, V value) throws DuplicateKeyException{}

    public void put(K key, V value){}

    public boolean containsKey(K key){}

    public V lookup(K key){}

    public void erase(K key){}

    public java.util.Collection<K> keys(){}

    public int size(){}

    public boolean empty(){}

    public void clear(){}
}
