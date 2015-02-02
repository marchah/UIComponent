package com.marchah.uicomponent.Object;

import java.util.Map;

/**
 * Created by marcha on 28/01/15.
 */
public class BasicEntry<K, V> implements Map.Entry<K, V>{

    private final K key;
    private V value;

    public BasicEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
