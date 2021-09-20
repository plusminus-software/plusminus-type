package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.validation.DateValidation;

@Data
public class DateField extends TemporalField {

    private DateValidation validation;

}
