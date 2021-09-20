package software.plusminus.type;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.TitleField;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
public class TitleFieldService {

    private static final List<String> DEFAULT_TITLE_FIELD_NAMES = Arrays.asList("name", "title");
    private static final String NOT_TITLE_FIELD = "NotTitleField";

    @Nullable
    public String getTitleField(JavaField javaField) {
        return Stream.<Supplier<String>>of(
                () -> findOnCurrentField(javaField),
                () -> findOnType(javaField.getType()),
                () -> findOnTypeFields(javaField.getType()))
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private String findOnCurrentField(JavaField javaField) {
        return Stream.of(javaField.getAnnotations())
                .filter(a -> TitleField.class.isAssignableFrom(a.getClass()))
                .findFirst()
                .map(a -> (TitleField) a)
                .map(TitleField::name)
                .orElse(null);
    }

    @Nullable
    private String findOnType(Class<?> type) {
        TitleField titleField = type.getAnnotation(TitleField.class);
        if (titleField == null) {
            return null;
        }
        return titleField.name();
    }

    private String findOnTypeFields(Class<?> type) {
        Optional<String> byAnnotation = Stream.of(type.getDeclaredFields())
                .filter(f -> {
                    TitleField annotation = f.getAnnotation(TitleField.class);
                    return annotation != null && "".equals(annotation.name());
                })
                .findFirst()
                .map(Field::getName);
        if (byAnnotation.isPresent()) {
            return byAnnotation.get();
        }

        Optional<String> byName = Stream.of(type.getDeclaredFields())
                .filter(f -> DEFAULT_TITLE_FIELD_NAMES.contains(f.getName()))
                .filter(f -> Stream.of(f.getAnnotations())
                        .noneMatch(a -> a.annotationType().getSimpleName().equals(NOT_TITLE_FIELD)))
                .findFirst()
                .map(Field::getName);
        return byName.orElse(null);

    }

}
