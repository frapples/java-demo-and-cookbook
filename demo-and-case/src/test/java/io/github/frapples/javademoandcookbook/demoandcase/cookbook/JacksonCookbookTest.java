package io.github.frapples.javademoandcookbook.demoandcase.cookbook;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator.Feature;
import io.github.frapples.javademoandcookbook.commonutils.utils.collection.fluentmap.ResponseMap;
import io.github.frapples.javademoandcookbook.commonutils.utils.datetime.DateTimeFormat;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/20
 */
public class JacksonCookbookTest {

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

    @Test
    public void testTest() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.USE_STD_BEAN_NAMING, true)
            .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
            .setSerializationInclusion(Include.NON_NULL)
            .setDateFormat(new SimpleDateFormat(DateTimeFormat.yyyy_MM_dd))
            .registerModule(new SimpleModule()
                .addSerializer(Number.class, ToStringSerializer.instance));

        TestEntity e = new TestEntity();
        Map<String, Object> map = mapper.convertValue(e, new TypeReference<Map<String, Object>>() {{}});
        System.out.println(map);
        TestEntity entity = mapper.convertValue(map, TestEntity.class);
        System.out.println(entity);

        mapper.createObjectNode()
            .put("a", 1)
            .put("def", new BigDecimal(1))
            .put("abc", mapper.createObjectNode());
    }

    @Test
    public void testXml() throws IOException, XMLStreamException {
        JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setXMLTextElementName("name");

        XmlMapper xmlMapper = (XmlMapper) new XmlMapper()
            .configure(MapperFeature.USE_STD_BEAN_NAMING, true)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
            .setSerializationInclusion(Include.NON_NULL)
            .setDateFormat(new SimpleDateFormat(DateTimeFormat.yyyy_MM_dd))
            .registerModule(new SimpleModule()
                .addSerializer(Number.class, ToStringSerializer.instance))
            .registerModule(xmlModule);

        xmlMapper.configure(Feature.WRITE_XML_DECLARATION, true);

        {
            String xml = xmlMapper.writeValueAsString(new TestEntity());
            System.out.println(xml);
        }

        {

            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            StringWriter out = new StringWriter();
            XMLStreamWriter sw = xmlOutputFactory.createXMLStreamWriter(out);

            xmlMapper = xmlMapper.copy();
            xmlMapper.configure(Feature.WRITE_XML_DECLARATION, false);
            sw.writeStartElement("root");

            ResponseMap map = ResponseMap.of()
                .fPut("a", 1)
                .fPut("b", 1.1)
                .fPut("c", new BigDecimal("2.3"))
                .fPut("list", Arrays.asList(1, 2, 3))
                .fPut("list2", Arrays.asList(
                    ResponseMap.of()
                        .fPut("ddd", 1)
                        .fPut("eee", 1),
                    ResponseMap.of()
                        .fPut("ddd", 1)
                        .fPut("eee", 1)
                ));
            xmlMapper.writeValue(sw, map);
            sw.writeEndElement();
            sw.writeEndDocument();
            System.out.println(out.toString());
        }
    }

}