package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.TextField;
import software.plusminus.type.model.validation.TextValidation;

import java.util.stream.Stream;

@Component
public class TextFieldParser implements FieldParser<TextField> {

    @Override
    public boolean supports(JavaField javaField) {
        return CharSequence.class.isAssignableFrom(javaField.getType());
    }

    @Override
    public TextField parse(JavaField javaField) {
        TextField textField = new TextField();
        if (Stream.of(javaField.getAnnotations())
                .anyMatch(a -> a.annotationType().getSimpleName().equals("Lob"))) {
            textField.setClob(true);
        }
        textField.setValidation(getValidation(javaField));
        return textField;
    }

    @SuppressWarnings("PMD")
    private TextValidation getValidation(JavaField javaField) {
        TextValidation validation = new TextValidation();
        // TODO
        return validation;
    }
}
