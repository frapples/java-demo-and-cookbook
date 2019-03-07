package io.github.frapples.javademoandcookbook.springboot.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Preconditions;
import io.github.frapples.javademoandcookbook.springboot.common.dto.IPageDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> {

    private Integer pageCount;

    private Integer count;

    private List<T> list;

    public static <T> PageVo<T> ofEmpty() {
        return ofSinglePage(Collections.emptyList());
    }

    public static <T> PageVo<T> ofSinglePage(List<T> list) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.pageCount = 1;
        pageVo.count = list.size();
        pageVo.list = list;
        return pageVo;
    }

    public <E> PageVo<E> map(Function<? super T, E> converter) {
        PageVo<E> pageVo = new PageVo<>();
        pageVo.pageCount = pageCount;
        pageVo.count = count;
        if (list != null) {
            pageVo.list = list.stream().map(converter).collect(Collectors.toList());
        }
        return pageVo;
    }

    public static <T> PageVo<T> ofPage(IPage<T> page) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.pageCount = (int)page.getPages();
        pageVo.count = (int)page.getTotal();
        pageVo.list = page.getRecords();
        return pageVo;
    }

    public static <T, E> PageVo<E> ofPage(IPage<T> page, Function<? super T, E> converter) {

        PageVo<E> pageVo = new PageVo<>();
        pageVo.pageCount = (int)page.getPages();
        pageVo.count = (int) page.getTotal();
        if (page.getRecords() != null) {
            pageVo.list = page.getRecords().stream().map(converter).collect(Collectors.toList());
        }
        return pageVo;
    }

    public static <E> PageVo<E> ofFullList(IPageDto page, List<? extends E> fullList) {

        Preconditions.checkArgument(page.getPage() != null && page.getPage() >= 1);
        Preconditions.checkArgument(page.getPageSize() != null && page.getPageSize() > 0);

        PageVo<E> pageVo = new PageVo<>();
        int start = (page.getPage() - 1) * page.getPageSize();
        int end = Math.min(start + page.getPageSize(), fullList.size());
        pageVo.list = new ArrayList<>(fullList.subList(start, end));
        pageVo.pageCount = fullList.size() / page.getPageSize() + 1;
        pageVo.count = fullList.size();
        return pageVo;
    }
}
