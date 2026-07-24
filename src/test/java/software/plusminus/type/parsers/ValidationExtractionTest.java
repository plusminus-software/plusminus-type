package software.plusminus.type.parsers;

import org.junit.Test;
import software.plusminus.type.helpers.ValidatedFieldsEntity;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.NumberField;
import software.plusminus.type.model.field.TextField;
import software.plusminus.type.model.validation.NumberValidation;
import software.plusminus.type.model.validation.TextValidation;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationExtractionTest {

    @Test
    public void textParserExtractsRequiredSizeAndPattern() throws NoSuchFieldException {
        TextField field = new TextFieldParser().parse(javaField(String.class, "name"));

        TextValidation validation = field.getValidation();
        assertThat(validation.isRequired()).isTrue();
        assertThat(validation.getMin()).isEqualTo(2);
        assertThat(validation.getMax()).isEqualTo(10);
        assertThat(validation.getPattern()).isEqualTo("[a-z]+");
    }

    @Test
    public void textParserLeavesValidationEmptyForPlainField() throws NoSuchFieldException {
        TextField field = new TextFieldParser().parse(javaField(String.class, "plain"));

        TextValidation validation = field.getValidation();
        assertThat(validation.isRequired()).isFalse();
        assertThat(validation.getMin()).isNull();
        assertThat(validation.getMax()).isNull();
        assertThat(validation.getPattern()).isNull();
    }

    @Test
    public void numberParserExtractsRequiredMinAndMax() throws NoSuchFieldException {
        NumberField field = new NumberFieldParser().parse(javaField(Integer.class, "count"));

        NumberValidation validation = field.getValidation();
        assertThat(validation.isRequired()).isTrue();
        assertThat(validation.getMin()).isEqualTo(1);
        assertThat(validation.getMax()).isEqualTo(100);
    }

    @Test
    public void numberParserExtractsDecimalMaxAndDefaultsRequiredToFalse() throws NoSuchFieldException {
        NumberField field = new NumberFieldParser().parse(javaField(Long.class, "amount"));

        NumberValidation validation = field.getValidation();
        assertThat(validation.isRequired()).isFalse();
        assertThat(validation.getMin()).isNull();
        assertThat(validation.getMax()).isEqualTo(50);
    }

    private JavaField javaField(Class<?> type, String fieldName) throws NoSuchFieldException {
        Annotation[] annotations = ValidatedFieldsEntity.class.getDeclaredField(fieldName).getAnnotations();
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(annotations);
        return javaField;
    }
}
