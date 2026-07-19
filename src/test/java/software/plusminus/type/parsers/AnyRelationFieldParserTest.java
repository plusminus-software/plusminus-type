package software.plusminus.type.parsers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.plusminus.type.TitleFieldService;
import software.plusminus.type.helpers.AnnotatedFieldsEntity;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.AnyRelationField;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnyRelationFieldParserTest {

    @Mock
    private TitleFieldService titleFieldService;

    @InjectMocks
    private AnyRelationFieldParser parser;

    @Test
    public void supportsFieldsWithAnyAnnotation() throws NoSuchFieldException {
        JavaField annotated = javaField(Object.class);
        annotated.setAnnotations(annotationsOf("any"));

        assertThat(parser.supports(annotated)).isTrue();
        assertThat(parser.supports(javaField(Object.class))).isFalse();
    }

    @Test
    public void parsesAnyRelationField() {
        JavaField javaField = javaField(Object.class);
        when(titleFieldService.getTitleField(javaField)).thenReturn("name");

        AnyRelationField field = parser.parse(javaField);

        assertThat(field.getTitleField()).isEqualTo("name");
        assertThat(field.getValidation()).isNotNull();
    }

    private JavaField javaField(Class<?> type) {
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(new Annotation[0]);
        return javaField;
    }

    private Annotation[] annotationsOf(String fieldName) throws NoSuchFieldException {
        return AnnotatedFieldsEntity.class.getDeclaredField(fieldName).getAnnotations();
    }
}
