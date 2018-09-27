package io.github.frapples.javademoandcookbook.commonutils.utils.join;

import io.github.frapples.javademoandcookbook.commonutils.entity.Point;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/13
 */
class JoinUtilsTest {

    @Test
    void leftJoin() {

        List<DemoDTO> demos = this.selectDemos();
        List<DemoDTO> result = LeftJoin.<DemoDTO, Point,DemoDTO, Integer>of()
            .left(demos, DemoDTO::getId)
            .right(this::selectPointByXs, Point::getX)
            .result(
                DemoDTO::new,
                BeanUtils::copyProperties,
                (point, r) -> r.setY(point.getY()));

        result.forEach(System.out::println);
    }

    private List<DemoDTO> selectDemos() {
        return Arrays.asList(
            new DemoDTO(1),
            new DemoDTO(3),
            new DemoDTO(5));
    }

    private List<Point> selectPointByXs(List<Integer> xs) {
        return Arrays.asList(
            Point.of(1, 2, ""),
            Point.of(3, 4, ""),
            Point.of(3, 5, "")
        );
    }

    @Data
    @NoArgsConstructor
    public static class DemoDTO {
        private Integer id;
        private Integer y;

        DemoDTO(Integer id) {
            this.id = id;
        }
    }

}