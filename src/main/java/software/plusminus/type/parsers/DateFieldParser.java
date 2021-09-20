package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.DateField;
import software.plusminus.type.model.validation.DateValidation;

import java.time.LocalDate;

@Component
public class DateFieldParser implements FieldParser<DateField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType() == LocalDate.class;
    }

    @Override
    public DateField parse(JavaField javaField) {
        DateField dateField = new DateField();
        dateField.setValidation(getValidation(javaField));
        return dateField;
    }

    @SuppressWarnings("PMD")
    private DateValidation getValidation(JavaField javaField) {
        DateValidation validation = new DateValidation();
        // TODO
        return validation;
    }
}
