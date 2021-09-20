package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

@Data
@EqualsAndHashCode(callSuper = true)
public class RangeValidation extends Validation {

    private Integer min;
    private Integer max;
    private Integer length;
    private Integer scale;

}
