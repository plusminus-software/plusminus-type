package software.plusminus.type.helpers;

import lombok.Data;

@Data
@Entity
public class SecondMutualEntity {

    private String name;
    private FirstMutualEntity first;

}
