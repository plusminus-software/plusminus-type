package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.Validation;

import java.util.List;

@Data
public class EnumField extends Field {

    private List<String> enumValues;
    private Validation validation;

}
