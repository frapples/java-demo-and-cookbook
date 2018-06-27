package io.github.frapples.utilscookbook.utils.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
class Point {

    @Getter
    @Setter
    private Integer x;
    @Getter
    @Setter
    private Integer y;
    private Integer privateVar;
}
