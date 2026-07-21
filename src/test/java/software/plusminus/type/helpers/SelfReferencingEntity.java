package software.plusminus.type.helpers;

import lombok.Data;

@Data
@Entity
public class SelfReferencingEntity {

    private String name;
    private SelfReferencingEntity parent;

}
