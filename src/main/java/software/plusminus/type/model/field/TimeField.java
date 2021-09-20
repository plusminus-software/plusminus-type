package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.validation.TimeValidation;

@Data
public class TimeField extends TemporalField {

    private TimeValidation validation;

}
