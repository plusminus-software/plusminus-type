package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnyRelationValidation extends Validation {

    private List<String> types;

}
