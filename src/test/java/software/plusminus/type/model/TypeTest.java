package software.plusminus.type.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeTest {

    @Test
    public void annotationsMapKeepsFirstOnDuplicateSimpleName() {
        Annotation first = new Annotation();
        first.setName("Same");
        first.setValue("first");
        Annotation second = new Annotation();
        second.setName("Same");
        second.setValue("second");
        Type type = new Type();
        type.setAnnotations(Arrays.asList(first, second));

        Map<String, Annotation> map = type.getAnnotationsMap();

        assertThat(map).hasSize(1);
        assertThat(map.get("Same").getValue()).isEqualTo("first");
    }
}
