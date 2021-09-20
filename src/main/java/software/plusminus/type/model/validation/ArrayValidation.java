package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArrayValidation extends Validation {

    private Boolean nullable;
    private Integer min;
    private Integer max;
    private Integer size;
    private List<String> contains;

}
