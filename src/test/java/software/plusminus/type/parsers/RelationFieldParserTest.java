package software.plusminus.type.parsers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.plusminus.type.ParseService;
import software.plusminus.type.TitleFieldService;
import software.plusminus.type.helpers.RelatedEntity;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.Type;
import software.plusminus.type.model.field.RelationField;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RelationFieldParserTest {

    @Mock
    private ParseService parseService;
    @Mock
    private TitleFieldService titleFieldService;

    @InjectMocks
    private RelationFieldParser parser;

    @Test
    public void supportsFieldsOfEntityType() {
        assertThat(parser.supports(javaField(RelatedEntity.class))).isTrue();
        assertThat(parser.supports(javaField(Object.class))).isFalse();
    }

    @Test
    public void parsesRelationField() {
        JavaField javaField = javaField(RelatedEntity.class);
        Type type = new Type();
        when(parseService.parse(RelatedEntity.class)).thenReturn(type);
        when(titleFieldService.getTitleField(javaField)).thenReturn("name");

        RelationField field = parser.parse(javaField);

        assertThat(field.getRelationType()).isSameAs(type);
        assertThat(field.getTitleField()).isEqualTo("name");
        assertThat(field.getValidation()).isNotNull();
    }

    private JavaField javaField(Class<?> type) {
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(new Annotation[0]);
        return javaField;
    }
}
