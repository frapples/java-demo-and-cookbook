package io.github.frapples.utilscookbook.utils;


import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
class Point {

    @Getter @Setter private Integer x;
    @Getter @Setter private Integer y;
    private Integer privateVar;
}

public class Main {

    public static void convertDemo() {
        Map map = Maps.newHashMap();
        map.put("x", 1);
        map.put("y", 2);
        System.out.println(ConvertUtils.convertFromMap(map, Point.class));
        System.out.println(ConvertUtils.convertToMap(new Point(3, 4, 0)));
    }

    public static void fileDemo() {
        try {
            System.out.println(FileUtils.readCalssPathFile("files/customerFile.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        convertDemo();
        fileDemo();
    }
}
