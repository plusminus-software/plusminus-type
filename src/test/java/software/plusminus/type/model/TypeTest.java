package software.plusminus.type.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TypeTest {

    @Test
    public void annotationsMapContainsAnnotationsByName() {
        Annotation first = new Annotation();
        first.setName("First");
        first.setValue("one");
        Annotation second = new Annotation();
        second.setName("Second");
        second.setValue("two");
        Type type = new Type();
        type.setAnnotations(Arrays.asList(first, second));

        Map<String, Annotation> map = type.getAnnotationsMap();

        assertThat(map).hasSize(2);
        assertThat(map.get("First").getValue()).isEqualTo("one");
        assertThat(map.get("Second").getValue()).isEqualTo("two");
    }

    @Test
    public void annotationsMapFailsOnDuplicateName() {
        Annotation first = new Annotation();
        first.setName("Same");
        first.setValue("first");
        Annotation second = new Annotation();
        second.setName("Same");
        second.setValue("second");
        Type type = new Type();
        type.setAnnotations(Arrays.asList(first, second));

        assertThatThrownBy(type::getAnnotationsMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Same");
    }
}
