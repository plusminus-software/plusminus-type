package software.plusminus.type;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ParseService {

    private static final ConcurrentMap<Class<?>, Type> CACHE
            = new ConcurrentHashMap<>();

    @Autowired
    private List<FieldParser<? extends Field>> fieldParsers;

    public <T> Type parse(Class<T> classValue) {
        Type type = CACHE.computeIfAbsent(classValue, c -> new Type());
        if (type.getName() == null) {
            populateType(classValue, type);
        }
        return type;
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

        List<Field> fields = Stream.of(classValue.getDeclaredFields())
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
        return Stream.of(javaField.getAnnotations())
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
        Optional<java.lang.reflect.Field> valueField = FieldUtils.findFirst(
                javaAnnotation.getClass(), field -> field.getName().equals("value"));
        valueField.ifPresent(f -> {
            Object value = FieldUtils.read(javaAnnotation, f);
            if (value != null) {
                annotation.setValue(value.toString());
            }
        });
    }
}
