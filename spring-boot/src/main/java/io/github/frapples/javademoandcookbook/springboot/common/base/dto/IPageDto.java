package io.github.frapples.javademoandcookbook.springboot.common.base.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */
public interface IPageDto {

    /**
     *
     * @return 分页页数，从1开始。
     */
    Integer getPage();

    /**
     *
     * @return 每页大小。
     */
    Integer getPageSize();


    public static <T extends IPageDto, E> IPage<E> toPage(T basePageDto) {

        Preconditions.checkArgument(basePageDto != null, "传入参数不能为null");
        Preconditions.checkArgument(basePageDto.getPage() != null, "page不能为null");
        Preconditions.checkArgument(basePageDto.getPageSize() != null, "pageSize不能为null");

        Page<E> p = new Page<>();
        p.setCurrent(basePageDto.getPage());
        p.setSize(basePageDto.getPageSize());
        return p;
    }


}
