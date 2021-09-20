package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.TextValidation;

@Data
public class TextField extends Field {

    private TextValidation validation;
    private boolean clob;

}
