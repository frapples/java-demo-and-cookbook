package io.github.frapples.javademoandcookbook.commonutils.utils.collection.fluentmap;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/4/2
 */
public class FluentMap<K, V> extends BaseFluentMap<K, V, FluentMap<K, V>> {

    @Override
    FluentMap<K, V> self() {
        return this;
    }
}
