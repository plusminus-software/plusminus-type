package software.plusminus.type;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import software.plusminus.type.helpers.ChildEntity;
import software.plusminus.type.helpers.CollectionEntity;
import software.plusminus.type.helpers.TestValueAnnotation;
import software.plusminus.type.model.Annotation;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.Type;
import software.plusminus.type.model.field.NumberField;
import software.plusminus.type.model.field.TextField;
import software.plusminus.type.parsers.FieldParser;
import software.plusminus.type.parsers.NumberFieldParser;
import software.plusminus.type.parsers.TextFieldParser;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParseServiceTest {

    @Spy
    private List<FieldParser<? extends Field>> fieldParsers = new ArrayList<>();
    @Mock
    private FieldParser<TextField> firstParser;
    @Mock
    private FieldParser<TextField> secondParser;

    @InjectMocks
    private ParseService parseService;

    @Captor
    private ArgumentCaptor<JavaField> javaFieldCaptor;

    @Before
    public void setUp() {
        fieldParsers.add(new TextFieldParser());
        fieldParsers.add(new NumberFieldParser());
    }

    @Test
    public void parsePopulatesNameAndNamespace() {
        Type type = parseService.parse(ChildEntity.class);

        assertThat(type.getName()).isEqualTo("ChildEntity");
        assertThat(type.getNamespace()).isEqualTo("software.plusminus.type.helpers");
    }

    @Test
    public void parseHandlesTypeWithoutPackage() {
        Type type = parseService.parse(int[].class);

        assertThat(type.getNamespace()).isNull();
    }

    @Test
    public void parsePopulatesTypeAnnotations() {
        Type type = parseService.parse(ChildEntity.class);

        assertThat(type.getAnnotations()).hasSize(2);
        Annotation valueAnnotation = type.getAnnotationsMap().get("TestValueAnnotation");
        assertThat(valueAnnotation).isNotNull();
        assertThat(valueAnnotation.getValue()).isEqualTo("type-value");
        Annotation markerAnnotation = type.getAnnotationsMap().get("TestMarkerAnnotation");
        assertThat(markerAnnotation).isNotNull();
        assertThat(markerAnnotation.getValue()).isNull();
    }

    @Test
    public void parsePopulatesFields() {
        Type type = parseService.parse(ChildEntity.class);

        assertThat(type.getFields()).hasSize(2);
        Field text = findField(type, "text");
        assertThat(text).isInstanceOf(TextField.class);
        assertThat(text.getAnnotations()).hasSize(1);
        assertThat(text.getAnnotations().get(0).getName()).isEqualTo("TestValueAnnotation");
        assertThat(text.getAnnotations().get(0).getValue()).isEqualTo("field-value");
        Field number = findField(type, "number");
        assertThat(number).isInstanceOf(NumberField.class);
        assertThat(number.getAnnotations()).isEmpty();
    }

    @Test
    public void parsePopulatesParentType() {
        Type type = parseService.parse(ChildEntity.class);

        Type parent = type.getParent();
        assertThat(parent).isNotNull();
        assertThat(parent.getName()).isEqualTo("ParentEntity");
        assertThat(parent.getParent()).isNull();
        assertThat(parent.getFields()).hasSize(1);
        assertThat(parent.getFields().get(0)).isInstanceOf(NumberField.class);
        assertThat(parent.getFields().get(0).getName()).isEqualTo("parentNumber");
    }

    @Test
    public void parseReturnsCachedTypeOnRepeatedCall() {
        Type first = parseService.parse(ChildEntity.class);
        Type second = parseService.parse(ChildEntity.class);

        assertThat(second).isSameAs(first);
    }

    @Test
    public void parsePopulatesGenericOfParametrizedField() {
        when(firstParser.supports(any(JavaField.class))).thenReturn(true);
        when(firstParser.parse(any(JavaField.class))).thenReturn(new TextField());
        fieldParsers.add(firstParser);

        parseService.parse(CollectionEntity.class);

        verify(firstParser).parse(javaFieldCaptor.capture());
        JavaField javaField = javaFieldCaptor.getValue();
        assertThat(javaField.getName()).isEqualTo("texts");
        assertThat(javaField.getType()).isEqualTo(List.class);
        assertThat(javaField.getGeneric()).isNotNull();
        assertThat(javaField.getGeneric().getType()).isEqualTo(String.class);
        assertThat(javaField.getGeneric().getAnnotations()).hasSize(1);
        TestValueAnnotation generic = (TestValueAnnotation) javaField.getGeneric().getAnnotations()[0];
        assertThat(generic.value()).isEqualTo("generic-value");
    }

    @Test
    public void parseFieldReturnsResultOfFirstSupportingParser() {
        fieldParsers.clear();
        fieldParsers.add(firstParser);
        fieldParsers.add(secondParser);
        JavaField javaField = new JavaField();
        TextField expected = new TextField();
        when(firstParser.supports(javaField)).thenReturn(true);
        when(firstParser.parse(javaField)).thenReturn(expected);

        Field result = parseService.parseField(javaField);

        assertThat(result).isSameAs(expected);
        verify(secondParser, never()).parse(any(JavaField.class));
    }

    @Test
    public void parseFieldSkipsNonSupportingParsers() {
        fieldParsers.clear();
        fieldParsers.add(firstParser);
        fieldParsers.add(secondParser);
        JavaField javaField = new JavaField();
        TextField expected = new TextField();
        when(firstParser.supports(javaField)).thenReturn(false);
        when(secondParser.supports(javaField)).thenReturn(true);
        when(secondParser.parse(javaField)).thenReturn(expected);

        Field result = parseService.parseField(javaField);

        assertThat(result).isSameAs(expected);
        verify(firstParser, never()).parse(any(JavaField.class));
    }

    @Test
    public void parseFieldThrowsParseExceptionIfNoParserSupportsField() {
        JavaField javaField = new JavaField();
        javaField.setName("unsupported");
        javaField.setType(Object.class);

        Throwable thrown = catchThrowable(() -> parseService.parseField(javaField));

        assertThat(thrown).isInstanceOf(ParseException.class)
                .hasMessageContaining("Can't find parser");
    }

    private Field findField(Type type, String name) {
        return type.getFields().stream()
                .filter(field -> name.equals(field.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Field not found: " + name));
    }
}
