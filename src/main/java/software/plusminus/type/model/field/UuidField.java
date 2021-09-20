package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.UuidValidation;

@Data
public class UuidField extends Field {

    private UuidValidation validation;

}
