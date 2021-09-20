package software.plusminus.type.parsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.plusminus.type.ParseService;
import software.plusminus.type.TitleFieldService;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.EmbeddedField;

import java.util.stream.Stream;

@Component
public class EmbeddedFieldParser implements FieldParser<EmbeddedField> {

    @Autowired
    private ParseService parseService;
    @Autowired
    private TitleFieldService titleFieldService;

    @Override
    public boolean supports(JavaField javaField) {
        return Stream.of(javaField.getAnnotations())
                .anyMatch(a -> a.annotationType().getSimpleName().equals("Embedded"))
                || Stream.of(javaField.getType().getAnnotations())
                .anyMatch(a -> a.annotationType().getSimpleName().equals("Embeddable"));
    }

    @Override
    public EmbeddedField parse(JavaField javaField) {
        EmbeddedField embeddedField = new EmbeddedField();
        embeddedField.setType(parseService.parse(javaField.getType()));
        embeddedField.setTitleField(titleFieldService.getTitleField(javaField));
        return embeddedField;
    }
}
