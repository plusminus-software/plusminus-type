package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.RangeValidation;

@Data
public class RangeField extends Field {

    private RangeValidation validation;

}
