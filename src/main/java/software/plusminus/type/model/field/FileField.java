package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.FileValidation;

@Data
public class FileField extends Field {

    private FileValidation validation;

}
