package io.github.frapples.javademoandcookbook.commonutils.utils.collection.fluentmap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/12/3
 *
 * Thanks for https://stackoverflow.com/questions/23731270/fluent-api-with-inheritance-and-generics
 */
public abstract class BaseFluentMap<K, V, THIS extends BaseFluentMap<K, V, THIS>> extends LinkedHashMap<K, V> {

    public THIS fluentPut(K key, V value) {
        this.put(adviceKeyOnPut(key), value);
        return self();
    }

    public THIS fluentPutAll(Map<? extends K, ? extends V> m) {
        // this.putAll(m);
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
        return self();
    }

    public THIS fluentClear() {
        this.clear();
        return self();
    }

    public THIS fluentRemove(Object key) {
        this.remove(key);
        return self();
    }

    public THIS fPut(K key, V value) {
        return this.fluentPut(key, value);
    }

    public THIS fPutAll(Map<? extends K, ? extends V> m) {
        return this.fluentPutAll(m);
    }

    public THIS fClear() {
        return fluentClear();
    }

    public THIS fRemove(Object key) {
        return this.fluentRemove(key);
    }

    abstract THIS self();

    protected K adviceKeyOnPut(K key) {
        return key;
    }
}
