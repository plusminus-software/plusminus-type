package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;

@Data
public abstract class TemporalField extends Field {

    private String format;

}
