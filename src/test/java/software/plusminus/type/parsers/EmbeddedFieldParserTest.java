package software.plusminus.type.parsers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.plusminus.type.ParseService;
import software.plusminus.type.TitleFieldService;
import software.plusminus.type.helpers.AnnotatedFieldsEntity;
import software.plusminus.type.helpers.EmbeddableEntity;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.Type;
import software.plusminus.type.model.field.EmbeddedField;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmbeddedFieldParserTest {

    @Mock
    private ParseService parseService;
    @Mock
    private TitleFieldService titleFieldService;

    @InjectMocks
    private EmbeddedFieldParser parser;

    @Test
    public void supportsFieldsWithEmbeddedAnnotation() throws NoSuchFieldException {
        JavaField annotated = javaField(Object.class);
        annotated.setAnnotations(
                AnnotatedFieldsEntity.class.getDeclaredField("embedded").getAnnotations());

        assertThat(parser.supports(annotated)).isTrue();
    }

    @Test
    public void supportsFieldsOfEmbeddableType() {
        assertThat(parser.supports(javaField(EmbeddableEntity.class))).isTrue();
        assertThat(parser.supports(javaField(Object.class))).isFalse();
    }

    @Test
    public void parsesEmbeddedField() {
        JavaField javaField = javaField(EmbeddableEntity.class);
        Type type = new Type();
        when(parseService.parse(EmbeddableEntity.class)).thenReturn(type);
        when(titleFieldService.getTitleField(javaField)).thenReturn("name");

        EmbeddedField field = parser.parse(javaField);

        assertThat(field.getType()).isSameAs(type);
        assertThat(field.getTitleField()).isEqualTo("name");
    }

    private JavaField javaField(Class<?> type) {
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(new Annotation[0]);
        return javaField;
    }
}
