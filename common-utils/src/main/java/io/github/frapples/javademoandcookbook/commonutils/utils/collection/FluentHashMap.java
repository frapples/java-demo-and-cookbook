package io.github.frapples.javademoandcookbook.commonutils.utils.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */
public class FluentHashMap<K, V> extends HashMap<K, V> {

    public FluentHashMap<K, V> fluentPut(K key, V value) {
        this.put(key, value);
        return this;
    }

    public FluentHashMap<K, V> fluentPutAll(Map<? extends K, ? extends V> m) {
        this.putAll(m);
        return this;
    }

    public FluentHashMap<K, V> fluentClear() {
        this.clear();
        return this;
    }

    public FluentHashMap<K, V> fluentRemove(Object key) {
        this.remove(key);
        return this;
    }

}
