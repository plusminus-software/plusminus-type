package software.plusminus.type.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class Type {

    private String namespace;
    private Type parent;
    private String name;
    private List<Annotation> annotations = new ArrayList<>();
    private List<Field> fields;

    public List<Field> getAllFields() {
        List<Type> hierarchy = new ArrayList<>();
        Type current = this;
        while (current != null) {
            hierarchy.add(current);
            current = current.getParent();
        }
        return hierarchy.stream()
                .flatMap(t -> t.getFields().stream())
                .collect(Collectors.toList());
    }

    public Map<String, Annotation> getAnnotationsMap() {
        return annotations.stream()
                .collect(Collectors.toMap(Annotation::getName, Function.identity()));
    }
}
