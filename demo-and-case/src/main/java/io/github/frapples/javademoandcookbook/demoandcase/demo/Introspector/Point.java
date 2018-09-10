package io.github.frapples.javademoandcookbook.demoandcase.demo.introspector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
class Point {

    private Integer x;
    private Integer y;
    private String comment;
}
