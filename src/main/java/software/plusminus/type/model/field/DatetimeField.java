package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.validation.DatetimeValidation;

@Data
public class DatetimeField extends TemporalField {

    private DatetimeValidation validation;

}
