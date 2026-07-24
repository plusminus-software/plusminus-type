package software.plusminus.type.parsers;

import org.junit.Test;
import software.plusminus.type.helpers.AnnotatedFieldsEntity;
import software.plusminus.type.helpers.TestEnum;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.BooleanField;
import software.plusminus.type.model.field.EnumField;
import software.plusminus.type.model.field.NumberField;
import software.plusminus.type.model.field.TextField;
import software.plusminus.type.model.field.UuidField;

import java.lang.annotation.Annotation;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueFieldParsersTest {

    @Test
    public void textParserSupportsCharSequenceOnly() {
        TextFieldParser parser = new TextFieldParser();

        assertThat(parser.supports(javaField(String.class))).isTrue();
        assertThat(parser.supports(javaField(Integer.class))).isFalse();
    }

    @Test
    public void textParserParsesPlainTextField() {
        TextField field = new TextFieldParser().parse(javaField(String.class));

        assertThat(field.isClob()).isFalse();
        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void textParserParsesLobFieldAsClob() throws NoSuchFieldException {
        JavaField javaField = javaField(String.class);
        javaField.setAnnotations(
                AnnotatedFieldsEntity.class.getDeclaredField("lob").getAnnotations());

        TextField field = new TextFieldParser().parse(javaField);

        assertThat(field.isClob()).isTrue();
    }

    @Test
    public void numberParserSupportsNumberClassesOnly() {
        NumberFieldParser parser = new NumberFieldParser();

        assertThat(parser.supports(javaField(Integer.class))).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void numberParserParsesNumberField() {
        NumberField field = new NumberFieldParser().parse(javaField(Long.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void booleanParserSupportsPrimitiveAndWrapper() {
        BooleanFieldParser parser = new BooleanFieldParser();

        assertThat(parser.supports(javaField(boolean.class))).isTrue();
        assertThat(parser.supports(javaField(Boolean.class))).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void booleanParserParsesBooleanField() {
        BooleanField field = new BooleanFieldParser().parse(javaField(Boolean.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void uuidParserSupportsUuidOnly() {
        UuidFieldParser parser = new UuidFieldParser();

        assertThat(parser.supports(javaField(UUID.class))).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void uuidParserParsesUuidField() {
        UuidField field = new UuidFieldParser().parse(javaField(UUID.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void enumParserSupportsEnumsOnly() {
        EnumFieldParser parser = new EnumFieldParser();

        assertThat(parser.supports(javaField(TestEnum.class))).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void enumParserRejectsRawEnumType() {
        EnumFieldParser parser = new EnumFieldParser();

        assertThat(parser.supports(javaField(Enum.class))).isFalse();
    }

    @Test
    public void enumParserParsesEnumValues() {
        EnumField field = new EnumFieldParser().parse(javaField(TestEnum.class));

        assertThat(field.getEnumValues()).containsExactly("FIRST", "SECOND");
    }

    private JavaField javaField(Class<?> type) {
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(new Annotation[0]);
        return javaField;
    }
}
