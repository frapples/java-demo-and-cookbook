package io.github.frapples.javademoandcookbook.springboot.common.utils.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.google.common.base.CaseFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */
public class QueryUtils {

    private QueryUtils() {
        throw new UnsupportedOperationException();

    }

    public static String column(String tableAlign, String column) {
        return tableAlign + "." + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);
    }

    public static String column(Class<?> tableEntity, String column) {
        return column(tableEntity.getAnnotation(TableName.class).value(), column);
    }

    public static <T> String column(Class<?> tableEntity, SFunction<T, ?> column) {
        return column(tableEntity, columnToString(column));
    }

    public static <T> String column(SFunction<T, ?> column) {
        SerializedLambda lambda = LambdaUtils.resolve(column);
        String col = getColumn(lambda, true);
        return column(lambda.getImplClass(), col);
    }

    public static <T> LambdaQueryWrapper<T> falseQueryWrapper() {
        return new ExtLambdaQueryWrapper<T>().contradictoryFormula();
    }

    public static <T, E> LambdaQueryWrapper<T> inCompatibleEmpty(LambdaQueryWrapper<T> query, SFunction<T, E> field, Collection<E> items) {
        if (items.isEmpty()) {
            return new ExtLambdaQueryWrapper<T>().contradictoryFormula();
        } else {
            return query.in(field, items);
        }
    }

    /**
     * 工具函数。辅助构造查询条件：时间field在start于end之间。
     * 若start为null，视为无穷小，若end为null，视为无穷大。
     * @param query mybatis plus的条件构造器
     * @param field 以整数形式存储unix时间戳的数据库字段
     * @param start 起始时间，精确到天
     * @param end 结束时间，精确到天
     */
    public static <T> LambdaQueryWrapper<T> timestampBetweenDate(LambdaQueryWrapper<T> query, SFunction<T, Integer> field, Date start, Date end) {

        if (start != null) {
            long fromDate = start.getTime() / 1000L;
            query = query.ge(field, fromDate);
        }
        if (end != null) {
            long toDate = DateUtils.addDays(end, 1).getTime() / 1000;
            query = query.lt(field, toDate);
        }
        return query;
    }

    public static <T> QueryWrapper<T> timestampBetweenDate(QueryWrapper<T> query, String field, Date start, Date end) {

        if (start != null) {
            long fromDate = start.getTime() / 1000L;
            query = query.ge(field, fromDate);
        }
        if (end != null) {
            long toDate = DateUtils.addDays(end, 1).getTime() / 1000;
            query = query.lt(field, toDate);
        }
        return query;
    }

    public static <T> String columnToString(SFunction<T, ?> column) {
        return columnToString(column, true);
    }

    public static <T> String columnToString(SFunction<T, ?> column, boolean onlyColumn) {
        return getColumn(LambdaUtils.resolve(column), onlyColumn);
    }

    private static String getColumn(SerializedLambda lambda, boolean onlyColumn) {
        String fieldName = StringUtils.resolveFieldName(lambda.getImplMethodName());
        String entityClassName = lambda.getImplClassName();
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClassName);
        Assert.notEmpty(columnMap, "cannot find column's cache for \"%s\", so you cannot used \"%s\"!",
            entityClassName, lambda.getImplClassName());
        return Optional.ofNullable(columnMap.get(fieldName.toUpperCase(Locale.ENGLISH)))
            .map(onlyColumn ? ColumnCache::getColumn : ColumnCache::getColumnSelect)
            .orElseThrow(() -> ExceptionUtils.mpe("your property named \"%s\" cannot find the corresponding database column name!", fieldName));
    }
}
