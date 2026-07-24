package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.DatetimeLocalField;
import software.plusminus.type.model.validation.DatetimeLocalValidation;

import java.time.LocalDateTime;

@Component
public class DatetimeLocalFieldParser implements FieldParser<DatetimeLocalField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType() == LocalDateTime.class;
    }

    @Override
    public DatetimeLocalField parse(JavaField javaField) {
        DatetimeLocalField datetimeLocalField = new DatetimeLocalField();
        datetimeLocalField.setValidation(getValidation(javaField));
        return datetimeLocalField;
    }

    private DatetimeLocalValidation getValidation(JavaField javaField) {
        DatetimeLocalValidation validation = new DatetimeLocalValidation();
        validation.setRequired(Validations.isRequired(javaField));
        return validation;
    }
}
