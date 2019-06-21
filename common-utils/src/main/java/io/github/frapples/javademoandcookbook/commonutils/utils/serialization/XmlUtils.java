package io.github.frapples.javademoandcookbook.commonutils.utils.serialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/12
 */
public class XmlUtils {

    /**
     * 将bean编码成xml。
     * @param mapper jackson的mapper。借由mapper，可以定制诸如对null的处理、字段名称定制、字段值序列化定制等。
     * @param rootName 根节点
     * @param bean bean对象
     * @param encoding xml编码。
     * @param pretty 如果设为 true，则会生成带换行和缩进的xml，可读性更好；否则生成不带冗余空格、换行的xml
     * @return 返回xml字节串，该字节串编码为encoding指定的编码
     */
    public static byte[] encode(ObjectMapper mapper, String rootName, Object bean, String encoding, boolean pretty) {
        Map<String, Object> map = mapper.convertValue(bean, new TypeReference<Map<String, Object>>(){{}});
        return encodeMapImpl(rootName, map, encoding, pretty, false);
    }

    /**
     * 将Map编码成xml
     * @param rootName 根节点名称
     * @param map 层次化结构的map
     * 1. map的key不允许为null、空串，否则会抛出异常
     * 2. 递归的层次结构只有三种类型：Map, List, 其它。对于其它类型，则用toString()当成字符串处理
     * @param encoding xml编码
     * @param pretty 如果设为 true，则会生成带换行和缩进的xml，可读性更好；否则生成不带冗余空格、换行的xml
     * @return 返回xml字节串，该字节串编码为encoding指定的编码
     */
    @SneakyThrows
    static byte[] encodeMapImpl(String rootName, Map<String, Object> map, String encoding, boolean pretty, boolean listWrap) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(rootName);
        encodeMapImpl(root, map, listWrap);
        document.setXMLEncoding(encoding);
        return toXml(document, pretty);
    }

    private static void encodeMapImpl(Element element, Map<?, ?> map, boolean listWrap) {
        for (Map.Entry<?, ?> pair : map.entrySet()) {
            if (!(pair.getKey() instanceof String) || StringUtils.isEmpty((String)pair.getKey())) {
                throw new RuntimeJsonMappingException("Key must be String and not empty");
            }

            String key = (String) pair.getKey();
            if (pair.getValue() instanceof Map<?, ?>) {
                Element e = element.addElement(key);
                encodeMapImpl(e, (Map<?, ?>) pair.getValue(), listWrap);
            } else if(pair.getValue() instanceof List) {
                encodeListImpl(element, key, (List<?>)pair.getValue(), listWrap);
            } else {
                Element e = element.addElement(key);
                e.addText(Objects.toString(pair.getValue()));
            }
        }
    }

    private static void encodeListImpl(Element element, String key, List<?> list, boolean listWrap) {
        if (listWrap) {
            element = element.addElement(key);
        }
        for (Object o : list) {
            Element e = element.addElement(key);
            if (o instanceof Map<?, ?>) {
                encodeMapImpl(e, (Map<?, ?>) o, listWrap);
            } else if(o instanceof List) {
                throw new RuntimeJsonMappingException("A list cannot contains other list");
            } else {
                e.addText(Objects.toString(o));
            }
        }
    }

    /**
     * 将xml格式的数据解析成Map
     * Map的key为String，Map的value有两种情况：
     * 1. 如果含有子节点，则为另一个结构相同的Map
     * 2. 如果不含有字节点，则为不为null的String
     * @param xml xml数据
     * @return 返回解析完成的Map
     * @throws DocumentException 解析失败
     */

    public static <T> T decode(ObjectMapper mapper, byte[] xml, TypeReference<T> type) throws DocumentException {
        Map<String, Object> map = decodeToMap(LinkedHashMap::new, xml);
        return mapper.convertValue(map, type);
    }

    public static <T> T decode(ObjectMapper mapper, byte[] xml, String[] paths, TypeReference<T> type) throws DocumentException {
        Map<String, Object> map = decodeToMap(LinkedHashMap::new, xml);
        for (int i = 0; i < paths.length - 1; i++) {
            String path = paths[i];
            Object v = map.get(path);
            if (v == null || !(v instanceof Map<?, ?>)) {
                // TODO
                return null;
            }
            map = (Map<String, Object>) v;

        }
        return mapper.convertValue(map.get(paths[paths.length - 1]), type);
    }

    public static <T> T decode(ObjectMapper mapper, byte[] xml, Class<T> clazz) throws DocumentException {
        Map<String, Object> map = decodeToMap(LinkedHashMap::new, xml);
        return mapper.convertValue(map, clazz);
    }

    static Map<String, Object> decodeToMap(Supplier<Map<String, Object>> mapSupplier, byte[] xml) throws DocumentException {
        Document document = parse(xml);
        Map<String, Object> map = mapSupplier.get();
        decodeMapImpl(mapSupplier, document.getRootElement(), map);
        return map;
    }

    private static void decodeMapImpl(Supplier<Map<String, Object>> mapSupplier, Element pElement, Map<String, Object> map) {
        for (Iterator i = pElement.elementIterator(); i.hasNext();) {
            Element element = (Element) i.next();
            Object v;
            if (element.isTextOnly()) {
                v = ObjectUtils.defaultIfNull(element.getText(), "");
            } else {
                Map<String, Object> cmap = mapSupplier.get();
                decodeMapImpl(mapSupplier, element, cmap);
                v = cmap;
            }

            if (map.containsKey(element.getName())) {
                Object oldV = map.get(element.getName());
                if (oldV instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>)oldV;
                    list.add(v);
                } else {
                    map.put(element.getName(), new ArrayList<>(Arrays.asList(oldV, v)));
                }

            } else {
                map.put(element.getName(), v);
            }
        }
    }


    public static Document parse(byte[] xml) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(new ByteArrayInputStream(xml));
    }

    public static byte[] toXml(Document document, boolean pretty) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        toXml(document, os, pretty);
        return os.toByteArray();
    }

    public static void toXml(Document document, OutputStream os, boolean pretty) throws IOException {
        OutputFormat format;
        if (pretty) {
            format = OutputFormat.createPrettyPrint();
        } else {
            format = OutputFormat.createCompactFormat();
        }
        format.setEncoding(document.getXMLEncoding());
        XMLWriter writer = new XMLWriter(os, format);
        writer.write(document);
    }

    @SneakyThrows
    public static byte[] pretty(byte[] xml) {
        Document document = parse(xml);
        return toXml(document, true);
    }


}
