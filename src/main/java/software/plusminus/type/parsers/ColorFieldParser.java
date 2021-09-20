package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.ColorField;
import software.plusminus.type.model.validation.ColorValidation;

import java.awt.Color;


@Component
public class ColorFieldParser implements FieldParser<ColorField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType() == Color.class;
    }

    @Override
    public ColorField parse(JavaField javaField) {
        ColorField colorField = new ColorField();
        colorField.setValidation(getValidation(javaField));
        return colorField;
    }

    @SuppressWarnings("PMD")
    private ColorValidation getValidation(JavaField javaField) {
        ColorValidation validation = new ColorValidation();
        // TODO
        return validation;
    }
}
