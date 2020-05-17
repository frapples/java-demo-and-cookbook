package io.github.frapples.script.lifetask.bootstrap.config;

import java.util.AbstractMap;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2020/5/16
 */
public class TreapTreeMap<K, V> extends AbstractMap<K, V> {

    private int size;
    private AtomicReference<Node<K, V>> root = new AtomicReference<>();
    private Random random = new Random(256);

    private static class Node<K, V> {
        K k;
        V v;
        int prop;
        final AtomicReference<Node<K, V>> left = new AtomicReference<>();
        final AtomicReference<Node<K, V>> right = new AtomicReference<>();

        Node(K k, V v, int prop) {
            this.k = k;
            this.v = v;
            this.prop = prop;
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V put(K key, V value) {
        treeAdd(root, key, value);
        return null;
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }


    @Override
    public V get(Object key) {
        AtomicReference<Node<K, V>> node = treeLocate(root, (K)key);
        return node == null ? null : node.get().v;
    }

    @Override
    public V remove(Object key) {
        AtomicReference<Node<K, V>> node = treeLocate(root, (K)key);
        if (node != null) {
            while (node.get().left.get() != null && node.get().right.get() != null) {
                rotate(node, node.get().left.get().prop > node.get().right.get().prop);
            }
            // 实现技巧：有一个节点的情况和没有节点的情况可以合并
            AtomicReference<Node<K, V>> son = node.get().left.get() != null ? node.get().left : node.get().right;
            V v = node.get().v;
            node.set(son.get());
            size--;
            return v;
        }
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> consumer) {
        forEach(root.get(), consumer);
    }

    private void forEach(Node<K, V> node, BiConsumer<? super K, ? super V> consumer) {
        if (node != null) {
            forEach(node.left.get(), consumer);
            consumer.accept(node.k, node.v);
            forEach(node.right.get(), consumer);
        }
    }

    private V treeAdd(AtomicReference<Node<K, V>> pNode, K key, V value) {
        if (pNode.get() == null) {
            pNode.set(new Node<>(key, value, random.nextInt()));
            size++;
            return null;
        }
        Node<K, V> node = pNode.get();

        int cmp = compareKey(key, node.k);
        V prevV;
        if (cmp < 0) {
            prevV = treeAdd(node.left, key, value);
        } else if (cmp > 0) {
            prevV = treeAdd(node.right, key, value);
        } else {
            prevV = node.v;
            node.v = value;
        }

        // 实现注意点：注意NULL
        if (node.left.get() != null && node.prop > node.left.get().prop) {
            rotate(pNode, true);
        } else if (node.right.get() != null && node.prop > node.right.get().prop) {
            rotate(pNode, false);
        }
        return prevV;
    }

    private AtomicReference<Node<K, V>> treeLocate(AtomicReference<Node<K, V>> root, K key) {
        AtomicReference<Node<K, V>> node = root;
        while (node.get() != null) {
            int cmp = compareKey(key, node.get().k);
            if (cmp < 0) {
                node = node.get().left;
            } else if (cmp > 0) {
                node = node.get().right;
            } else {
                return node;
            }
        }
        return null;
    }

    private void rotate(AtomicReference<Node<K, V>> root, boolean isLeft) {
        if (isLeft) {
            Node<K, V> son = root.get().left.get();
            root.get().left.set(son.right.get());
            son.right.set(root.get());
            root.set(son);
        } else {
            Node<K, V> son = root.get().right.get();
            root.get().right.set(son.left.get());
            son.left.set(root.get());
            root.set(son);
        }
    }

    private int compareKey(K a, K b) {
        return ((Comparable<K>)a).compareTo(b);
    }
}
