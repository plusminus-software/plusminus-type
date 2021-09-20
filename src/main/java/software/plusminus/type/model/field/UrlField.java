package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.UrlValidation;

@Data
public class UrlField extends Field {

    private UrlValidation validation;

}
