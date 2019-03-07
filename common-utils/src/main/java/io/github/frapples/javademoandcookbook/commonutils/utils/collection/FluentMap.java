package io.github.frapples.javademoandcookbook.commonutils.utils.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 */
public class FluentMap<K, V> extends LinkedHashMap<K, V> {

    public FluentMap<K, V> fluentPut(K key, V value) {
        this.put(key, value);
        return this;
    }

    public FluentMap<K, V> fluentPutAll(Map<? extends K, ? extends V> m) {
        this.putAll(m);
        return this;
    }

    public FluentMap<K, V> fluentClear() {
        this.clear();
        return this;
    }

    public FluentMap<K, V> fluentRemove(Object key) {
        this.remove(key);
        return this;
    }

    public FluentMap<K, V> fPut(K key, V value) {
        return this.fluentPut(key, value);
    }

    public FluentMap<K, V> fPutAll(Map<? extends K, ? extends V> m) {
        return this.fluentPutAll(m);
    }

    public FluentMap<K, V> fClear() {
        return fluentClear();
    }

    public FluentMap<K, V> fRemove(Object key) {
        return this.fluentRemove(key);
    }
}
