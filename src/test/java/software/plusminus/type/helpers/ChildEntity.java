package software.plusminus.type.helpers;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TestValueAnnotation("type-value")
@TestMarkerAnnotation
public class ChildEntity extends ParentEntity {

    @TestValueAnnotation("field-value")
    private String text;
    private int number;

}
