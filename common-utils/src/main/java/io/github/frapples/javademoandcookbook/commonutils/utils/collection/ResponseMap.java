package io.github.frapples.javademoandcookbook.commonutils.utils.collection;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */
public class ResponseMap extends FluentMap<String, Object> {

    public static ResponseMap of() {
        return new ResponseMap();
    }
}
