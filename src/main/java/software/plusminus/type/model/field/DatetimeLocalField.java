package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.validation.DatetimeLocalValidation;

@Data
public class DatetimeLocalField extends TemporalField {

    private DatetimeLocalValidation validation;

}
