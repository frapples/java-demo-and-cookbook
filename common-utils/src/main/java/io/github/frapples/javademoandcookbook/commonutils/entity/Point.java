package io.github.frapples.javademoandcookbook.commonutils.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Point {

    @Getter
    @Setter
    private Integer x;
    @Getter
    @Setter
    private Integer y;
    private String privateVar;
}
