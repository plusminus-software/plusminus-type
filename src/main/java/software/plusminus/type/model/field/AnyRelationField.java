package software.plusminus.type.model.field;

import lombok.Data;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.TitleField;
import software.plusminus.type.model.validation.AnyRelationValidation;

@Data
public class AnyRelationField extends Field {

    @TitleField
    private String titleField;
    private AnyRelationValidation validation;

}
