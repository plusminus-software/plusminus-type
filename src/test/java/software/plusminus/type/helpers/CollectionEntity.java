package software.plusminus.type.helpers;

import lombok.Data;

import java.util.List;

@Data
public class CollectionEntity {

    private List<@TestValueAnnotation("generic-value") String> texts;

}
