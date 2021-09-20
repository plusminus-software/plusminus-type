package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class DatetimeValidation extends Validation {

    private Instant min;
    private Instant max;

}
