package io.github.frapples.javademoandcookbook.commonutils.utils.serialization;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BigDecimalDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Lists;
import io.github.frapples.javademoandcookbook.commonutils.utils.collection.fluentmap.ResponseMap;
import io.github.frapples.javademoandcookbook.commonutils.utils.datetime.DateTimeFormat;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/20
 */
class XmlUtilsTest {

    @Test
    void encodeFromMap() throws IOException {
        String charset = "UTF-8";
        {
            Map<String, Object> map = ResponseMap.of()
                .fluentPut("a", 1)
                .fluentPut("b", 2)
                .fluentPut("c", ResponseMap.of()
                    .fluentPut("c1", "abc")
                    .fluentPut("d2", "def"));

            byte[] r = XmlUtils.encodeMapImpl("root", map, charset, true, false);
            System.out.println(new String(r, charset));

            r = XmlUtils.encodeMapImpl("root", map, charset, false, true);
            System.out.println(new String(r, charset));
        }

        {
            ResponseMap map = ResponseMap.of()
                .fluentPut("a", Arrays.asList(
                    1,
                    2))
                .fluentPut("b", Lists.newArrayList(
                    ResponseMap.of()
                        .fluentPut("bb", 1)
                        .fluentPut("dd", 2),
                    ResponseMap.of()
                        .fluentPut("bb", 1)
                        .fluentPut("dd", 2)
                ))
                .fluentPut("c", 3);
            byte[] r = XmlUtils.encodeMapImpl("root", map, charset, true, true);
            System.out.println(new String(r, charset));
        }
    }

    @Data
    public static class TestEntity {
        @JsonSerialize(using = ToStringSerializer.class)
        private BigDecimal bigDecimal = new BigDecimal("0.03");

        private Double aDouble = 0.03;

        Integer integer = 100;

        Date date = new Date();

        String str = "teststr";

        List<String> list = Arrays.asList("1", "2", "3");

    }

    @Data
    public static class TestEntity2 {
        @JsonSerialize(using = ToStringSerializer.class)
        @JsonDeserialize(using = BigDecimalDeserializer.class)
        private BigDecimal bigDecimal = new BigDecimal("0.03");

        private Double aDouble = 0.03;

        Integer integer = 100;

        Date date = new Date();

        String str = "teststr";

        String list = "1";

    }


    @Test
    void encode() throws IOException {
        {
            byte[] xmlBytes = XmlUtils.encode(mapper(), "root", new TestEntity(), "GBK", false);
            String xml = new String(xmlBytes, "GBK");
            String excepted = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n"
                + "<root><bigDecimal>0.03</bigDecimal><aDouble>0.03</aDouble><integer>100</integer><date>2019-06-21</date><str>teststr</str><list><list>1</list><list>2</list><list>3</list></list></root>";
            Assertions.assertEquals(excepted, xml);
            System.out.println(new String(XmlUtils.pretty(xmlBytes), "GBK"));
        }

        {
            ResponseMap map = ResponseMap.of()
                .fPut("a", 1)
                .fPut("b", Arrays.asList(
                    new TestEntity(),
                    new TestEntity()
                ))
                .fPut("testEntity", new TestEntity());
            byte[] xmlBytes = XmlUtils.encode(mapper(), "root", map, "GBK", false);
            String xml = new String(xmlBytes, "GBK");
            String excepted = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n"
                +
                "<root><a>1</a><b><b><bigDecimal>0.03</bigDecimal><aDouble>0.03</aDouble><integer>100</integer><date>2019-06-21</date><str>teststr</str><list><list>1</list><list>2</list><list>3</list></list></b><b><bigDecimal>0.03</bigDecimal><aDouble>0.03</aDouble><integer>100</integer><date>2019-06-21</date><str>teststr</str><list><list>1</list><list>2</list><list>3</list></list></b></b><testEntity><bigDecimal>0.03</bigDecimal><aDouble>0.03</aDouble><integer>100</integer><date>2019-06-21</date><str>teststr</str><list><list>1</list><list>2</list><list>3</list></list></testEntity></root>";
            Assertions.assertEquals(excepted, xml);
            System.out.println(new String(XmlUtils.pretty(xmlBytes), "GBK"));
        }
    }

    @Test
    void decode() throws UnsupportedEncodingException, DocumentException {
        {
            byte[] xml = XmlUtils.encode(mapper(), "root", new TestEntity2(), "GBK", true);
            System.out.println(new String(xml, "GBK"));
            TestEntity e = XmlUtils.decode(mapper(), xml, TestEntity.class);
            toString();
        }

        {
            byte[] xml = XmlUtils.encode(mapper(), "root",
                ResponseMap.of()
                .fPut("a", Arrays.asList(new TestEntity2(), new TestEntity2()))
                , "GBK", true);
            System.out.println(new String(xml, "GBK"));

            List<TestEntity> e = XmlUtils.decode(mapper(), xml, new String[]{"a"}, new TypeReference<List<TestEntity>>() {{
            }});
            toString();
        }
    }


    private ObjectMapper mapper() {
        return new ObjectMapper()
            .configure(MapperFeature.USE_STD_BEAN_NAMING, true)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
            .setSerializationInclusion(Include.NON_NULL)
            .setDateFormat(new SimpleDateFormat(DateTimeFormat.yyyy_MM_dd))
            .registerModule(new SimpleModule()
                .addSerializer(Number.class, ToStringSerializer.instance));
    }
}