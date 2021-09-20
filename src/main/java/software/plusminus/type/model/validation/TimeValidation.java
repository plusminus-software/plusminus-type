package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimeValidation extends Validation {

    private LocalTime min;
    private LocalTime max;

}
