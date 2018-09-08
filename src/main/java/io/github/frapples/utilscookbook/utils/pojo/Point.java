package io.github.frapples.utilscookbook.utils.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Getter
    @Setter
    private Integer x;
    @Getter
    @Setter
    private Integer y;
    private Integer privateVar;
}
