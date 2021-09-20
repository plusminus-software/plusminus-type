package software.plusminus.type.model.field;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.TitleField;
import software.plusminus.type.model.Type;
import software.plusminus.type.model.validation.RelationValidation;

@Data
@EqualsAndHashCode(exclude = "relationType", callSuper = true)
@ToString(exclude = "relationType", callSuper = true)
public class RelationField extends Field {

    @TitleField
    private String titleField;
    private Type relationType;
    private RelationValidation validation;

}
