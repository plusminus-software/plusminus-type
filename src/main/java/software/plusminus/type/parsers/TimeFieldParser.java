package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.TimeField;
import software.plusminus.type.model.validation.TimeValidation;

import java.time.LocalTime;

@Component
public class TimeFieldParser implements FieldParser<TimeField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType() == LocalTime.class;
    }

    @Override
    public TimeField parse(JavaField javaField) {
        TimeField timeField = new TimeField();
        timeField.setValidation(getValidation(javaField));
        return timeField;
    }

    @SuppressWarnings("PMD")
    private TimeValidation getValidation(JavaField javaField) {
        TimeValidation validation = new TimeValidation();
        // TODO
        return validation;
    }
}
