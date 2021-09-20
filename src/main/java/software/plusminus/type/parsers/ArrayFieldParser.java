package software.plusminus.type.parsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.plusminus.type.ParseService;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.ArrayField;
import software.plusminus.type.model.validation.ArrayValidation;

@Component
public class ArrayFieldParser implements FieldParser<ArrayField> {

    @Autowired
    private ParseService parseService;

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType().isArray()
                || Iterable.class.isAssignableFrom(javaField.getType());
    }

    @Override
    public ArrayField parse(JavaField javaField) {
        ArrayField arrayField = new ArrayField();
        arrayField.setArrayType(getType(javaField));
        arrayField.setValidation(getValidation(javaField));
        return arrayField;
    }

    private Field getType(JavaField javaField) {
        if (javaField.getType().isArray()) {
            JavaField arrayType = new JavaField();
            arrayType.setAnnotations(javaField.getAnnotations());
            arrayType.setType(javaField.getType().getComponentType());
            return parseService.parseField(arrayType);
        }
        return parseService.parseField(javaField.getGeneric());
    }

    @SuppressWarnings("PMD")
    private ArrayValidation getValidation(JavaField javaField) {
        ArrayValidation validation = new ArrayValidation();
        // TODO
        return validation;
    }
}
