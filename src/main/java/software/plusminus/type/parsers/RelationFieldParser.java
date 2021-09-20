package software.plusminus.type.parsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.plusminus.type.ParseService;
import software.plusminus.type.TitleFieldService;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.RelationField;
import software.plusminus.type.model.validation.RelationValidation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class RelationFieldParser implements FieldParser<RelationField> {

    private static final List<String> ANNOTATIONS = Arrays.asList("Entity", "Document", "MappedSuperclass");

    @Autowired
    private ParseService parseService;
    @Autowired
    private TitleFieldService titleFieldService;

    @Override
    public boolean supports(JavaField javaField) {
        return Stream.of(javaField.getType().getAnnotations())
                .anyMatch(a -> ANNOTATIONS.contains(a.annotationType().getSimpleName()));
    }

    @Override
    public RelationField parse(JavaField javaField) {
        RelationField relationField = new RelationField();
        relationField.setRelationType(parseService.parse(javaField.getType()));
        relationField.setTitleField(titleFieldService.getTitleField(javaField));
        relationField.setValidation(getValidation(javaField));
        return relationField;
    }

    @SuppressWarnings("PMD")
    private RelationValidation getValidation(JavaField javaField) {
        RelationValidation validation = new RelationValidation();
        // TODO
        return validation;
    }
}
