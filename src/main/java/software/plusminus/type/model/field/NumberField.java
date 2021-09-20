package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.NumberValidation;

@Data
public class NumberField extends Field {

    private NumberValidation validation;

}
