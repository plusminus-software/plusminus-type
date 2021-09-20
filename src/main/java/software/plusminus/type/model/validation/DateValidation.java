package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class DateValidation extends Validation {

    private LocalDate min;
    private LocalDate max;

}
