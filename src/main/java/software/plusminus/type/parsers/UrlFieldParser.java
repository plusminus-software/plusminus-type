package software.plusminus.type.parsers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.UrlField;
import software.plusminus.type.model.validation.UrlValidation;

import java.net.URL;
import java.util.stream.Stream;

/* Ordered before the type-driven parsers: a @Url field may be a String,
   which TextFieldParser would otherwise claim first */
@Order(0)
@Component
public class UrlFieldParser implements FieldParser<UrlField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType() == URL.class
                || Stream.of(javaField.getAnnotations())
                .anyMatch(a -> a.annotationType().getSimpleName().equals("Url"));
    }

    @Override
    public UrlField parse(JavaField javaField) {
        UrlField urlField = new UrlField();
        urlField.setValidation(getValidation(javaField));
        return urlField;
    }

    private UrlValidation getValidation(JavaField javaField) {
        UrlValidation validation = new UrlValidation();
        validation.setRequired(Validations.isRequired(javaField));
        return validation;
    }
}
