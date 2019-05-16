package io.github.frapples.javademoandcookbook.commonutils.utils.excel;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.export.ExcelBatchExportService;
import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import io.github.frapples.javademoandcookbook.commonutils.utils.datetime.DateTimeFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/12
 */
@Slf4j
public class ExcelUtils {


    public static <T> InputStream export(BiFunction<Integer, Integer, List<? extends T>> dataSupplier, int total, Class<? super T> tClass) throws IOException {
        log.info("开始导出，导出数据总数: {}, 被导出的Bean名:{}",total, tClass.getName());

        ExcelBatchExportService service = new ExcelBatchExportService();
        service.init(new ExportParams(), tClass);

        int blockNum = 10000;
        int count = 0;
        do {
            int nextBlock = Math.min(blockNum, total - count);
            log.info("准备获取被导出的数据，数据范围：[{}, {})", count, count + nextBlock);
            List<? extends T> list = dataSupplier.apply(count, nextBlock);
            service.appendData(list);
            count += nextBlock;
        } while (count < total);

        Workbook wb = service.appendData(Collections.emptyList());
        File file = File.createTempFile("mms-excel-export", ".xls");
        file.deleteOnExit();

        try (OutputStream outputStream = new FileOutputStream(file)) {
            wb.write(outputStream);
        }

        log.info("导出结束，已写入临时流");

        return new FileInputStream(file) {
            @Override
            public void close() throws IOException {
                super.close();
                if (file.exists() && !file.delete()) {
                    log.error("临时文件删除失败：{}", file.getPath());
                }
            }
        };
    }

    public static <T> InputStream export(List<? extends T> list, Class<? super T> tClass) throws IOException {
        return export((start, count) -> list.subList(start, start + count), list.size(), tClass);
    }

    public static <D> ExcelImportResult<D> importFile(File file, Class<? super D> clazz) throws IOException {
        // 校验文件格式
        List<String> legalExtensions = Arrays.asList("xls", "xlsx");
        String ext = FilenameUtils.getExtension(file.getAbsolutePath());
        if (!legalExtensions.contains(ext)) {
            throw new RuntimeException("只支持excel格式文件!");
        }

        InputStream inputStream = new FileInputStream(file);
        ExcelImportResult<D> result = importFile(inputStream, clazz);

        skipEndEmptyLine(inputStream, result);
        return result;

    }

    @SneakyThrows
    private static <T> void skipEndEmptyLine(InputStream inputStream, ExcelImportResult<T> result) {
        if (result.getList() == null) {
            result.setList(new ArrayList<>());
        }

        ImportParams params = new ImportParams();
        List<Map<String, Object>> list = ExcelImportUtil.importExcel(inputStream, Map.class, params);

        int end;
        if (list.size() > 0) {
            int i;
            boolean emptyLine = true;
            for (i = list.size() - 1; i >= 0 && emptyLine; i--) {
                Map<String, Object> item = list.get(0);
                emptyLine = item == null || item.values().stream().allMatch(v -> v == null || StringUtils.isBlank(v.toString()));
            }
            end = i + 2;
        } else {
            end = 0;
        }
        result.setList(result.getList().subList(0, end));
    }

    public static <D> ExcelImportResult<D> importFile(InputStream inputStream, Class<? super D> clazz) {
        ImportParams importParams = new ImportParams();
        importParams.setKeyIndex(null);
        importParams.setDataHandler(customEasyPoiDataHandler(clazz));
            // 单行作为一条数据
        try {
            return ExcelImportUtil.importExcelMore(inputStream, clazz, importParams);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static IExcelDataHandler customEasyPoiDataHandler(Class<?> clazz) {
        Map<String, Field> excelAnnotationMap = FieldUtils.getFieldsListWithAnnotation(clazz, Excel.class).stream()
            .collect(Collectors.toMap(c -> c.getAnnotation(Excel.class).name(), c -> c));
        String[] excelNames = excelAnnotationMap.keySet().toArray(new String[0]);

        return new ExcelDataHandlerDefaultImpl() {
            @Override
            public String[] getNeedHandlerFields() {
                return excelNames;
            }

            @Override
            public Object importHandler(Object obj, String name, Object value) {
                Field field = excelAnnotationMap.get(name);
                Excel anno = field == null ? null : field.getAnnotation(Excel.class);
                if (anno != null
                    && value instanceof Date && String.class.isAssignableFrom(field.getType())) {
                    String format =  anno.format().isEmpty() ? DateTimeFormat.yyyy_MM_dd : anno.format();
                    return new SimpleDateFormat(format).format(value);
                }
                return value;
            }
        };
    }
}
