package software.plusminus.type.parsers;

import org.junit.Test;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.DateField;
import software.plusminus.type.model.field.DatetimeField;
import software.plusminus.type.model.field.DatetimeLocalField;
import software.plusminus.type.model.field.TimeField;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class TemporalFieldParsersTest {

    @Test
    public void dateParserSupportsLocalDateOnly() {
        DateFieldParser parser = new DateFieldParser();

        assertThat(parser.supports(javaField(LocalDate.class))).isTrue();
        assertThat(parser.supports(javaField(LocalDateTime.class))).isFalse();
    }

    @Test
    public void dateParserParsesDateField() {
        DateField field = new DateFieldParser().parse(javaField(LocalDate.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void datetimeParserSupportsDatetimeClasses() {
        DatetimeFieldParser parser = new DatetimeFieldParser();

        assertThat(parser.supports(javaField(Date.class))).isTrue();
        assertThat(parser.supports(javaField(java.sql.Date.class))).isTrue();
        assertThat(parser.supports(javaField(Instant.class))).isTrue();
        assertThat(parser.supports(javaField(OffsetDateTime.class))).isTrue();
        assertThat(parser.supports(javaField(ZonedDateTime.class))).isTrue();
        assertThat(parser.supports(javaField(LocalDate.class))).isFalse();
    }

    @Test
    public void datetimeParserParsesDatetimeField() {
        DatetimeField field = new DatetimeFieldParser().parse(javaField(Instant.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void datetimeLocalParserSupportsLocalDateTimeOnly() {
        DatetimeLocalFieldParser parser = new DatetimeLocalFieldParser();

        assertThat(parser.supports(javaField(LocalDateTime.class))).isTrue();
        assertThat(parser.supports(javaField(LocalDate.class))).isFalse();
    }

    @Test
    public void datetimeLocalParserParsesDatetimeLocalField() {
        DatetimeLocalField field = new DatetimeLocalFieldParser().parse(javaField(LocalDateTime.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void timeParserSupportsLocalTimeOnly() {
        TimeFieldParser parser = new TimeFieldParser();

        assertThat(parser.supports(javaField(LocalTime.class))).isTrue();
        assertThat(parser.supports(javaField(LocalDate.class))).isFalse();
    }

    @Test
    public void timeParserParsesTimeField() {
        TimeField field = new TimeFieldParser().parse(javaField(LocalTime.class));

        assertThat(field.getValidation()).isNotNull();
    }

    private JavaField javaField(Class<?> type) {
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(new Annotation[0]);
        return javaField;
    }
}
