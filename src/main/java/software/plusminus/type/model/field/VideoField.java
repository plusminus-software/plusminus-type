package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.validation.VideoValidation;

@Data
public class VideoField extends Field {

    private VideoValidation validation;

}
