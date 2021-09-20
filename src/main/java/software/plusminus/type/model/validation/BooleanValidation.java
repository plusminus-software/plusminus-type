package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

@Data
@EqualsAndHashCode(callSuper = true)
public class BooleanValidation extends Validation {

    private Boolean requiredValue;

}
