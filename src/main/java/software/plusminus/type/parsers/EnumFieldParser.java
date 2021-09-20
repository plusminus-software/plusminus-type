package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.EnumField;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EnumFieldParser implements FieldParser<EnumField> {

    @Override
    public boolean supports(JavaField javaField) {
        return Enum.class.isAssignableFrom(javaField.getType());
    }

    @Override
    public EnumField parse(JavaField javaField) {
        EnumField enumField = new EnumField();
        List<String> enumValues = Stream.of(javaField.getType().getEnumConstants())
                .map(e -> (Enum) e)
                .map(Enum::name)
                .collect(Collectors.toList());
        enumField.setEnumValues(enumValues);
        return enumField;
    }
}
