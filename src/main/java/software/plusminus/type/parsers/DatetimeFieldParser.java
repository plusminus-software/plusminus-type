package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.DatetimeField;
import software.plusminus.type.model.validation.DatetimeValidation;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class DatetimeFieldParser implements FieldParser<DatetimeField> {

    @Override
    public boolean supports(JavaField javaField) {
        return Stream.of(Date.class, java.sql.Date.class, Instant.class, OffsetDateTime.class, ZonedDateTime.class)
                .anyMatch(c -> javaField.getType() == c);
    }

    @Override
    public DatetimeField parse(JavaField javaField) {
        DatetimeField datetimeField = new DatetimeField();
        datetimeField.setValidation(getValidation(javaField));
        return datetimeField;
    }

    @SuppressWarnings("PMD")
    private DatetimeValidation getValidation(JavaField javaField) {
        DatetimeValidation validation = new DatetimeValidation();
        // TODO
        return validation;
    }
}
