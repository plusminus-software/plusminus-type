package software.plusminus.type.parsers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import software.plusminus.type.ParseService;
import software.plusminus.type.helpers.AnnotatedFieldsEntity;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.TextField;
import software.plusminus.type.model.field.UrlField;
import software.plusminus.type.model.field.VideoField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/* Spring injects the parser list sorted by AnnotationAwareOrderComparator,
   so the @Order(0) annotation-driven parsers (Url, Video) must win over
   TextFieldParser for annotated String fields */
public class ParserOrderingTest {

    private ParseService parseService;

    @Before
    public void setUp() {
        /* Adversarial initial order: TextFieldParser first, as its class name sorts before Url/Video */
        List<FieldParser<? extends Field>> parsers = new ArrayList<>(Arrays.asList(
                new TextFieldParser(), new UrlFieldParser(), new VideoFieldParser()));
        AnnotationAwareOrderComparator.sort(parsers);
        parseService = new ParseService(parsers);
    }

    @Test
    public void urlAnnotatedStringFieldIsParsedAsUrlField() throws NoSuchFieldException {
        assertThat(parseService.parseField(javaField("url"))).isInstanceOf(UrlField.class);
    }

    @Test
    public void videoAnnotatedStringFieldIsParsedAsVideoField() throws NoSuchFieldException {
        assertThat(parseService.parseField(javaField("video"))).isInstanceOf(VideoField.class);
    }

    @Test
    public void plainStringFieldIsStillParsedAsTextField() throws NoSuchFieldException {
        assertThat(parseService.parseField(javaField("lob"))).isInstanceOf(TextField.class);
    }

    private JavaField javaField(String name) throws NoSuchFieldException {
        java.lang.reflect.Field field = AnnotatedFieldsEntity.class.getDeclaredField(name);
        JavaField javaField = new JavaField();
        javaField.setName(name);
        javaField.setType(field.getType());
        javaField.setAnnotations(field.getAnnotations());
        return javaField;
    }
}
