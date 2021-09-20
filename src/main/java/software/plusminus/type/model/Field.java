package software.plusminus.type.model;

import lombok.Data;

import java.util.List;

@Data
public abstract class Field {

    private String name;
    private List<Annotation> annotations;

    public abstract Validation getValidation();

}
