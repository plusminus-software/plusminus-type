package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.BooleanValidation;

@Data
public class BooleanField extends Field {

    private BooleanValidation validation;

}
