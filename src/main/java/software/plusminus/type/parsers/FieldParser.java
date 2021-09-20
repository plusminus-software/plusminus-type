package software.plusminus.type.parsers;

import software.plusminus.type.model.Field;
import software.plusminus.type.model.JavaField;

public interface FieldParser<T extends Field> {

    boolean supports(JavaField javaField);

    T parse(JavaField javaField);

}
