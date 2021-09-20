package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.RangeField;
import software.plusminus.type.model.validation.RangeValidation;

@Component
public class RangeFieldParser implements FieldParser<RangeField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType().getSimpleName().equals("Range");
    }

    @Override
    public RangeField parse(JavaField javaField) {
        RangeField rangeField = new RangeField();
        rangeField.setValidation(getValidation(javaField));
        return rangeField;
    }

    @SuppressWarnings("PMD")
    private RangeValidation getValidation(JavaField javaField) {
        RangeValidation validation = new RangeValidation();
        // TODO
        return validation;
    }
}
