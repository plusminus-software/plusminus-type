package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.ArrayValidation;

@Data
public class ArrayField extends Field {

    private Field arrayType;
    private ArrayValidation validation;

}
