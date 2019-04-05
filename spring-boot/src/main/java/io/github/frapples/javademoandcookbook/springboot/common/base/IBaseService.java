package io.github.frapples.javademoandcookbook.springboot.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.frapples.javademoandcookbook.springboot.common.base.dto.IPageDto;
import io.github.frapples.javademoandcookbook.springboot.common.base.vo.PageVo;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */

public interface IBaseService<T> extends IService<T> {

    /**
     * 分页查询。通过page指定的分页信息，queryWrapper指定的查询条件，查询符合条件的分页段数据
     *
     * @param page         分页信息
     * @param queryWrapper 查询条件
     * @param converter    转换函数
     * @return 返回的分页段数据，通过converter转换后，由PageVo封装。
     */
    <E> PageVo<E> page(IPage<T> page, Wrapper<T> queryWrapper, Function<? super T, E> converter);

    /**
     * 分页查询。通过page指定的分页信息，queryWrapper指定的查询条件，查询符合条件的分页段数据
     *
     * @param pageDto      分页信息
     * @param queryWrapper 查询条件
     * @param converter    转换函数
     * @return 返回的分页段数据，通过converter转换后，由PageVo封装。
     */
    <E> PageVo<E> page(IPageDto pageDto, Wrapper<T> queryWrapper, Function<? super T, E> converter);

    PageVo<T> page(IPageDto pageDto, Wrapper<T> queryWrapper);

    /**
     * 类似page方法。但是不同的是，如果pageDto中没有分页数据，则全量查询。
     * 此方法慎用，确保数据量不大时使用。
     */
    PageVo<T> optionalPage(IPageDto pageDto, Wrapper<T> queryWrapper);

    /**
     * 指定一组候选码集合，对其进行批量更新。
     * 适合一组数据被批量更新的字段和内容一样，并且通过某个候选码筛选的场景
     *
     * @param updated 要更新的实体
     * @param field 要查询的字段
     * @param candidateKeys 要查询的集合
     * @return 是否更新成功
     */
    <E> boolean updateBatchByCandidateKeys(T updated, SFunction<T, E> field, Collection<E> candidateKeys);

    /**
     * 对原有listByIds的改进，如果传入的id列表为空，返回空数据.
     */
    Collection<T> listByIds(Collection<? extends Serializable> idList);
}

