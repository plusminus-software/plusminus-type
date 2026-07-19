package software.plusminus.type.parsers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import software.plusminus.type.ParseException;
import software.plusminus.type.ParseService;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.ArrayField;
import software.plusminus.type.model.field.TextField;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayFieldParserTest {

    @Mock
    private ParseService parseService;

    @InjectMocks
    private ArrayFieldParser parser;

    @Captor
    private ArgumentCaptor<JavaField> javaFieldCaptor;

    @Test
    public void supportsArraysAndIterables() {
        assertThat(parser.supports(javaField(String[].class))).isTrue();
        assertThat(parser.supports(javaField(List.class))).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void parsesArrayFieldByComponentType() {
        JavaField javaField = javaField(String[].class);
        TextField arrayType = new TextField();
        when(parseService.parseField(any(JavaField.class))).thenReturn(arrayType);

        ArrayField field = parser.parse(javaField);

        assertThat(field.getArrayType()).isSameAs(arrayType);
        assertThat(field.getValidation()).isNotNull();
        verify(parseService).parseField(javaFieldCaptor.capture());
        assertThat(javaFieldCaptor.getValue().getType()).isEqualTo(String.class);
    }

    @Test
    public void parsesIterableFieldByGenericType() {
        JavaField javaField = javaField(List.class);
        JavaField generic = javaField(String.class);
        javaField.setGeneric(generic);
        TextField arrayType = new TextField();
        when(parseService.parseField(generic)).thenReturn(arrayType);

        ArrayField field = parser.parse(javaField);

        assertThat(field.getArrayType()).isSameAs(arrayType);
    }

    @Test
    public void throwsParseExceptionOnIterableFieldWithoutGeneric() {
        JavaField javaField = javaField(List.class);

        Throwable thrown = catchThrowable(() -> parser.parse(javaField));

        assertThat(thrown).isInstanceOf(ParseException.class)
                .hasMessageContaining("without generic type");
    }

    private JavaField javaField(Class<?> type) {
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(new Annotation[0]);
        return javaField;
    }
}
