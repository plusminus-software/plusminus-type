package software.plusminus.type.model.field;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.TitleField;
import software.plusminus.type.model.Type;
import software.plusminus.type.model.Validation;

@Data
@EqualsAndHashCode(exclude = "type", callSuper = true)
@ToString(exclude = "type", callSuper = true)
public class EmbeddedField extends Field {

    @TitleField
    private String titleField;
    private Type type;
    private Validation validation;

}
