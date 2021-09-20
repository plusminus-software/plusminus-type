package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.NumberField;
import software.plusminus.type.model.validation.NumberValidation;
import software.plusminus.util.NumberUtils;

@Component
public class NumberFieldParser implements FieldParser<NumberField> {

    @Override
    public boolean supports(JavaField javaField) {
        return NumberUtils.isNumberClass(javaField.getType());
    }

    @Override
    public NumberField parse(JavaField javaField) {
        NumberField numberField = new NumberField();
        numberField.setValidation(getValidation(javaField));
        return numberField;
    }

    @SuppressWarnings("PMD")
    private NumberValidation getValidation(JavaField javaField) {
        NumberValidation validation = new NumberValidation();
        // TODO
        return validation;
    }
}
