package io.github.frapples.javademoandcookbook.springboot.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import io.github.frapples.javademoandcookbook.springboot.common.base.dto.IPageDto;
import io.github.frapples.javademoandcookbook.springboot.common.base.vo.PageVo;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Function;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */
public abstract class AbstractBaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Override
    public <E> PageVo<E> page(IPage<T> page, Wrapper<T> queryWrapper, Function<? super T, E> converter) {
        Preconditions.checkArgument(converter != null, "参数不能为null");
        Preconditions.checkArgument(queryWrapper != null, "参数不能为null");
        Preconditions.checkArgument(page != null, "参数不能为null");

        IPage<T> result = this.page(page, queryWrapper);
        return PageVo.ofPage(result, converter);
    }

    @Override
    public <E> PageVo<E> page(IPageDto pageDto, Wrapper<T> queryWrapper, Function<? super T, E> converter) {
        Preconditions.checkArgument(converter != null, "参数不能为null");
        Preconditions.checkArgument(queryWrapper != null, "参数不能为null");
        Preconditions.checkArgument(pageDto != null, "参数不能为null");

        IPage<T> page = IPageDto.toPage(pageDto);
        IPage<T> result = this.page(page, queryWrapper);
        return PageVo.ofPage(result, converter);
    }

    @Override
    public PageVo<T> page(IPageDto pageDto, Wrapper<T> queryWrapper) {
        Preconditions.checkArgument(queryWrapper != null, "参数不能为null");
        Preconditions.checkArgument(pageDto != null, "参数不能为null");

        IPage<T> page = IPageDto.toPage(pageDto);
        IPage<T> result = this.page(page, queryWrapper);
        return PageVo.ofPage(result);
    }

    @Override
    public PageVo<T> optionalPage(IPageDto pageDto, Wrapper<T> queryWrapper) {
        PageVo<T> pageVo;
        if (pageDto.getPage() != null && pageDto.getPageSize() != null) {
            pageVo = this.page(pageDto, queryWrapper);
        } else {
            pageVo = PageVo.ofSinglePage(this.list(queryWrapper));
        }
        return pageVo;
    }

    @Override
    public <E> boolean updateBatchByCandidateKeys(T updated, SFunction<T, E> field, Collection<E> candidateKeys) {
        if (candidateKeys.isEmpty()) {
            return true;
        } else {
            return this.update(updated, new LambdaQueryWrapper<T>()
                .in(field, candidateKeys));
        }
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        HashSet<? extends Serializable> ids = new HashSet<>(idList);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        } else {
            return super.listByIds(ids);
        }
    }
}
