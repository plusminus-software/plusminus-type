package software.plusminus.type;

import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import software.plusminus.type.model.Annotation;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.Type;
import software.plusminus.type.parsers.FieldParser;
import software.plusminus.util.FieldUtils;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ParseService {

    private final ConcurrentMap<Class<?>, Type> cache = new ConcurrentHashMap<>();

    private final List<FieldParser<? extends Field>> fieldParsers;

    /* Parsers may recurse back into ParseService (e.g. RelationFieldParser),
       so the list is injected lazily to avoid a circular bean dependency */
    public ParseService(@Lazy List<FieldParser<? extends Field>> fieldParsers) {
        this.fieldParsers = fieldParsers;
    }

    public <T> Type parse(Class<T> classValue) {
        Type cached = cache.get(classValue);
        if (cached != null) {
            return cached;
        }
        Type type = new Type();
        populateType(classValue, type);
        Type existing = cache.putIfAbsent(classValue, type);
        return existing != null ? existing : type;
    }

    public Field parseField(JavaField javaField) {
        return fieldParsers.stream()
                .filter(p -> p.supports(javaField))
                .findFirst()
                .map(p -> p.parse(javaField))
                .orElseThrow(() -> new ParseException("Can't find parser for the following field: " + javaField));
    }

    public <T> void populateType(Class<T> classValue, Type type) {
        type.setName(classValue.getSimpleName());
        type.setAnnotations(toAnnotations(classValue.getAnnotations()));

        List<Field> fields = Stream.of(classValue.getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .map(this::toJavaField)
                .map(javaField -> {
                    Field field = this.parseField(javaField);
                    if (field.getName() == null) {
                        field.setName(javaField.getName());
                    }
                    if (field.getAnnotations() == null) {
                        field.setAnnotations(getAnnotations(javaField));
                    }
                    return field;
                })
                .collect(Collectors.toList());
        type.setFields(fields);

        if (classValue.getSuperclass() != null
                && classValue.getSuperclass() != Object.class) {
            type.setParent(parse(classValue.getSuperclass()));
        }
        type.setNamespace(classValue.getPackage().getName());
    }

    private JavaField toJavaField(java.lang.reflect.Field field) {
        JavaField javaField = new JavaField();
        javaField.setName(field.getName());
        javaField.setType(field.getType());
        javaField.setAnnotations(field.getAnnotations());
        javaField.setGeneric(toGeneric(field));
        return javaField;
    }

    private List<Annotation> getAnnotations(JavaField javaField) {
        return toAnnotations(javaField.getAnnotations());
    }

    private List<Annotation> toAnnotations(java.lang.annotation.Annotation[] javaAnnotations) {
        return Stream.of(javaAnnotations)
            .map(a -> {
                Annotation annotation = new Annotation();
                annotation.setName(a.annotationType().getSimpleName());
                populateAnnotationValue(a, annotation);
                return annotation;
            })
            .collect(Collectors.toList());
    }

    @Nullable
    private JavaField toGeneric(java.lang.reflect.Field field) {
        AnnotatedType annotatedType = field.getAnnotatedType();
        boolean parametrized = annotatedType instanceof AnnotatedParameterizedType;
        if (!parametrized) {
            return null;
        }

        JavaField genericField = new JavaField();

        Class<?> genericType = FieldUtils.getGenericType(field);
        genericField.setType(genericType);

        AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType) annotatedType;
        genericField.setAnnotations(
                annotatedParameterizedType.getAnnotatedActualTypeArguments()[0].getAnnotations());

        return genericField;
    }

    private void populateAnnotationValue(java.lang.annotation.Annotation javaAnnotation,
                                         Annotation annotation) {
        java.lang.reflect.Method valueMethod;
        try {
            valueMethod = javaAnnotation.annotationType().getMethod("value");
        } catch (NoSuchMethodException e) {
            // Annotation has no value() element - nothing to populate.
            return;
        }
        try {
            Object value = valueMethod.invoke(javaAnnotation);
            if (value != null) {
                annotation.setValue(value.toString());
            }
        } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            throw new ParseException("Can't read value() of annotation " + javaAnnotation, e);
        }
    }
}
