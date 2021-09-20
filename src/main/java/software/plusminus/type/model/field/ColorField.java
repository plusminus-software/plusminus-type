package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.ColorValidation;

@Data
public class ColorField extends Field {

    private ColorValidation validation;

}
