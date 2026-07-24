package software.plusminus.type.parsers;

import software.plusminus.type.model.JavaField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Shared extraction of bean-validation (javax.validation) metadata from a field's annotations.
 * Annotations are matched by simple name, consistent with the rest of the parsers, so the code
 * works regardless of whether the constraints come from the javax or jakarta namespace.
 */
final class Validations {

    private static final List<String> REQUIRED_ANNOTATIONS =
            Arrays.asList("NotNull", "NotEmpty", "NotBlank");
    private static final int SIZE_MAX_DEFAULT = Integer.MAX_VALUE;

    private Validations() {
    }

    static boolean isRequired(JavaField javaField) {
        return annotations(javaField)
                .map(a -> a.annotationType().getSimpleName())
                .anyMatch(REQUIRED_ANNOTATIONS::contains);
    }

    static Integer sizeMin(JavaField javaField) {
        Integer min = intAttribute(javaField, "Size", "min");
        return min == null || min == 0 ? null : min;
    }

    static Integer sizeMax(JavaField javaField) {
        Integer max = intAttribute(javaField, "Size", "max");
        return max == null || max == SIZE_MAX_DEFAULT ? null : max;
    }

    static String pattern(JavaField javaField) {
        return stringAttribute(javaField, "Pattern", "regexp");
    }

    static Integer numberMin(JavaField javaField) {
        Integer min = numberAttribute(javaField, "Min", "value");
        return min != null ? min : decimalAttribute(javaField, "DecimalMin");
    }

    static Integer numberMax(JavaField javaField) {
        Integer max = numberAttribute(javaField, "Max", "value");
        return max != null ? max : decimalAttribute(javaField, "DecimalMax");
    }

    private static Stream<Annotation> annotations(JavaField javaField) {
        Annotation[] annotations = javaField.getAnnotations();
        return annotations == null ? Stream.empty() : Stream.of(annotations);
    }

    private static Integer intAttribute(JavaField javaField, String annotation, String method) {
        Object value = attribute(javaField, annotation, method);
        return value instanceof Number ? ((Number) value).intValue() : null;
    }

    private static Integer numberAttribute(JavaField javaField, String annotation, String method) {
        Object value = attribute(javaField, annotation, method);
        return value instanceof Number ? (int) ((Number) value).longValue() : null;
    }

    private static Integer decimalAttribute(JavaField javaField, String annotation) {
        Object value = attribute(javaField, annotation, "value");
        if (value == null) {
            return null;
        }
        try {
            return new BigDecimal(value.toString()).intValue();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String stringAttribute(JavaField javaField, String annotation, String method) {
        Object value = attribute(javaField, annotation, method);
        return value == null || value.toString().isEmpty() ? null : value.toString();
    }

    private static Object attribute(JavaField javaField, String annotationSimpleName, String method) {
        return annotations(javaField)
                .filter(a -> a.annotationType().getSimpleName().equals(annotationSimpleName))
                .findFirst()
                .map(a -> invoke(a, method))
                .orElse(null);
    }

    private static Object invoke(Annotation annotation, String method) {
        try {
            Method attributeMethod = annotation.annotationType().getMethod(method);
            return attributeMethod.invoke(annotation);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }
}
