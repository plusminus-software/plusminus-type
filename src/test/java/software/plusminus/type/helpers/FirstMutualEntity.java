package software.plusminus.type.helpers;

import lombok.Data;

@Data
@Entity
public class FirstMutualEntity {

    private String name;
    private SecondMutualEntity second;

}
