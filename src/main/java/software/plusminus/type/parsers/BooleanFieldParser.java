package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.BooleanField;
import software.plusminus.type.model.validation.BooleanValidation;

@Component
public class BooleanFieldParser implements FieldParser<BooleanField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType() == boolean.class || javaField.getType() == Boolean.class;
    }

    @Override
    public BooleanField parse(JavaField javaField) {
        BooleanField booleanField = new BooleanField();
        booleanField.setValidation(getValidation(javaField));
        return booleanField;
    }

    @SuppressWarnings("PMD")
    private BooleanValidation getValidation(JavaField javaField) {
        BooleanValidation validation = new BooleanValidation();
        // TODO
        return validation;
    }
}
