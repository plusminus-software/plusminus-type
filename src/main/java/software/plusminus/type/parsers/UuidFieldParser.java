package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.UuidField;
import software.plusminus.type.model.validation.UuidValidation;

import java.util.UUID;

@Component
public class UuidFieldParser implements FieldParser<UuidField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType() == UUID.class;
    }

    @Override
    public UuidField parse(JavaField javaField) {
        UuidField uuidField = new UuidField();
        uuidField.setValidation(getValidation(javaField));
        return uuidField;
    }

    @SuppressWarnings("PMD")
    private UuidValidation getValidation(JavaField javaField) {
        UuidValidation validation = new UuidValidation();
        // TODO
        return validation;
    }
}
