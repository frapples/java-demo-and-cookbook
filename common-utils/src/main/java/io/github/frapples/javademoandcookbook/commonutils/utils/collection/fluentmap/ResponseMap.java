package io.github.frapples.javademoandcookbook.commonutils.utils.collection.fluentmap;

import com.google.common.base.CaseFormat;
import lombok.AllArgsConstructor;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */
@AllArgsConstructor
public class ResponseMap extends BaseFluentMap<String, Object, ResponseMap> {

    private CaseFormat targetCaseFormat;

    public static ResponseMap of() {
        return new ResponseMap(null);
    }

    public static ResponseMap of(CaseFormat targetCaseFormat) {
        return new ResponseMap(targetCaseFormat);
    }

    @Override
    ResponseMap self() {
        return this;
    }

    @Override
    protected String adviceKeyOnPut(String key) {
        if (targetCaseFormat == null || key == null || key.isEmpty()) {
            return key;
        }
        char first = key.charAt(0);

        if (targetCaseFormat == CaseFormat.LOWER_UNDERSCORE) {
            if (Character.isUpperCase(first)) {
                return CaseFormat.UPPER_CAMEL.to(targetCaseFormat, key);
            } else if (Character.isLowerCase(first)) {
                return CaseFormat.LOWER_CAMEL.to(targetCaseFormat, key);
            } else {
                return key;
            }
        } else if (targetCaseFormat == CaseFormat.LOWER_CAMEL) {
            return CaseFormat.UPPER_UNDERSCORE.to(targetCaseFormat, key);
        } else {
            return key;
        }
    }
}
