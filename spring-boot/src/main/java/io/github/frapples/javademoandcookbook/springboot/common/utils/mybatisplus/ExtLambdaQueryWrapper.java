package io.github.frapples.javademoandcookbook.springboot.common.utils.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import java.util.Collection;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */
public class ExtLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {

    LambdaQueryWrapper<T> contradictoryFormula() {
        return doIt(true, () -> "1", SqlKeyword.NE, () -> "1");
    }

    @Override
    public LambdaQueryWrapper<T> in(SFunction<T, ?> column, Collection<?> value) {
        return null;
    }
}
