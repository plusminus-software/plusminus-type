package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class DatetimeLocalValidation extends Validation {

    private LocalDateTime min;
    private LocalDateTime max;

}
