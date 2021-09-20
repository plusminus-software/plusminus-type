package software.plusminus.type.parsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.plusminus.type.TitleFieldService;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.AnyRelationField;
import software.plusminus.type.model.validation.AnyRelationValidation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Component
public class AnyRelationFieldParser implements FieldParser<AnyRelationField> {

    private static final List<String> ANNOTATIONS = Collections.singletonList("Any");

    @Autowired
    private TitleFieldService titleFieldService;

    @Override
    public boolean supports(JavaField javaField) {
        return Stream.of(javaField.getAnnotations())
                .anyMatch(a -> ANNOTATIONS.contains(a.annotationType().getSimpleName()));
    }

    @Override
    public AnyRelationField parse(JavaField javaField) {
        AnyRelationField relationField = new AnyRelationField();
        relationField.setTitleField(titleFieldService.getTitleField(javaField));
        relationField.setValidation(getValidation(javaField));
        return relationField;
    }

    @SuppressWarnings("PMD")
    private AnyRelationValidation getValidation(JavaField javaField) {
        AnyRelationValidation validation = new AnyRelationValidation();
        // TODO
        return validation;
    }
}
