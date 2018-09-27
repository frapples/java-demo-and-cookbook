package io.github.frapples.javademoandcookbook.commonutils.utils.join;

import io.github.frapples.javademoandcookbook.commonutils.utils.join.JoinUtils.Consumer3;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/13
 */
public class LeftJoin<A, B, R, P> {

    public static <A, B, R, P> LeftJoin<A, B, R, P> of() {
        return new LeftJoin<>();
    }

    private List<A> listA;
    private Function<A, P> getterA;

    private List<B> listB;
    private Function<List<P>, List<B>> selectBfunc;
    private Function<B, P> getterB;

    public LeftJoin<A, B, R, P> left(List<A> listA, Function<A, P> getterA) {
        this.listA = listA;
        this.getterA = getterA;
        return this;
    }

    public LeftJoin<A, B, R, P> right(List<B> listB, Function<B, P> getterB) {
        this.listB = listB;
        this.getterB = getterB;
        return this;
    }

    public LeftJoin<A, B, R, P> right(Function<List<P>, List<B>> selectBfunc, Function<B, P> getterB) {
        this.selectBfunc = selectBfunc;
        this.getterB = getterB;
        return this;
    }

    public List<R> result(BiFunction<A, B, R> mergeFunc) {
        if (this.listB == null) {
            this.listB = this.selectBfunc.apply(this.listA.stream().map(getterA).collect(Collectors.toList()));
        }
        return JoinUtils.leftJoin(listA, listB, getterA, getterB, mergeFunc);
    }

    public List<R> result(Supplier<R> newFunc, Consumer3<A, B, R> mergeFunc) {
        if (this.listB == null) {
            this.listB = this.selectBfunc.apply(this.listA.stream().map(getterA).collect(Collectors.toList()));
        }
        return JoinUtils.leftJoin(listA, listB, getterA, getterB, newFunc, mergeFunc);
    }

    public List<R> result(Supplier<R> newFunc, BiConsumer<A, R> mergeAFunc, BiConsumer<B, R> mergeBFunc) {
        return this.result(newFunc, (itemA, itemB, r) -> {
            mergeAFunc.accept(itemA, r);
            if (itemB != null) {
                mergeBFunc.accept(itemB, r);
            }
        });
    }
}
