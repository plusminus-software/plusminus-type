package software.plusminus.type.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;

import java.lang.annotation.Annotation;

@Data
public class JavaField {

    private String name;
    private Class<?> type;
    @SuppressFBWarnings("EI_EXPOSE_REP")
    private Annotation[] annotations;
    private JavaField generic;

}
