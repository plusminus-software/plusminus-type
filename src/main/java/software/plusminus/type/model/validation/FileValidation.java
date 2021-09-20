package software.plusminus.type.model.validation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.plusminus.type.model.Validation;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileValidation extends Validation {

    private Integer minSize;
    private Integer maxSize;
    private List<String> extension;

}
