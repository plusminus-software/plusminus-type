package software.plusminus.type;

import org.junit.Test;
import software.plusminus.type.helpers.AnnotatedFieldsEntity;
import software.plusminus.type.helpers.InheritedNameEntity;
import software.plusminus.type.helpers.NamedFieldEntity;
import software.plusminus.type.helpers.NotTitledNameEntity;
import software.plusminus.type.helpers.TitledFieldEntity;
import software.plusminus.type.helpers.TitledTypeEntity;
import software.plusminus.type.model.JavaField;

import java.lang.annotation.Annotation;

import static org.assertj.core.api.Assertions.assertThat;

public class TitleFieldServiceTest {

    private TitleFieldService titleFieldService = new TitleFieldService();

    @Test
    public void findsTitleFieldOnCurrentFieldAnnotation() throws NoSuchFieldException {
        JavaField javaField = javaField(Object.class);
        javaField.setAnnotations(
                AnnotatedFieldsEntity.class.getDeclaredField("titled").getAnnotations());

        String result = titleFieldService.getTitleField(javaField);

        assertThat(result).isEqualTo("custom");
    }

    @Test
    public void findsTitleFieldOnTypeAnnotation() {
        JavaField javaField = javaField(TitledTypeEntity.class);

        String result = titleFieldService.getTitleField(javaField);

        assertThat(result).isEqualTo("someField");
    }

    @Test
    public void findsTitleFieldByAnnotationOnTypeField() {
        JavaField javaField = javaField(TitledFieldEntity.class);

        String result = titleFieldService.getTitleField(javaField);

        assertThat(result).isEqualTo("label");
    }

    @Test
    public void findsTitleFieldByDefaultFieldName() {
        JavaField javaField = javaField(NamedFieldEntity.class);

        String result = titleFieldService.getTitleField(javaField);

        assertThat(result).isEqualTo("name");
    }

    @Test
    public void findsTitleFieldOnSuperclassField() {
        JavaField javaField = javaField(InheritedNameEntity.class);

        String result = titleFieldService.getTitleField(javaField);

        assertThat(result).isEqualTo("name");
    }

    @Test
    public void skipsDefaultFieldNameMarkedAsNotTitleField() {
        JavaField javaField = javaField(NotTitledNameEntity.class);

        String result = titleFieldService.getTitleField(javaField);

        assertThat(result).isNull();
    }

    @Test
    public void returnsNullIfNoTitleFieldFound() {
        JavaField javaField = javaField(Object.class);

        String result = titleFieldService.getTitleField(javaField);

        assertThat(result).isNull();
    }

    private JavaField javaField(Class<?> type) {
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(new Annotation[0]);
        return javaField;
    }
}
