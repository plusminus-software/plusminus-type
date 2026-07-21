package software.plusminus.type.parsers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.VideoField;
import software.plusminus.type.model.validation.VideoValidation;

import java.util.stream.Stream;

/* Ordered before the type-driven parsers: a @Video field is usually a String,
   which TextFieldParser would otherwise claim first */
@Order(0)
@Component
public class VideoFieldParser implements FieldParser<VideoField> {

    @Override
    public boolean supports(JavaField javaField) {
        return Stream.of(javaField.getAnnotations())
                .anyMatch(a -> a.annotationType().getSimpleName().equals("Video"));
    }

    @Override
    public VideoField parse(JavaField javaField) {
        VideoField videoField = new VideoField();
        videoField.setValidation(getValidation(javaField));
        return videoField;
    }

    @SuppressWarnings("PMD")
    private VideoValidation getValidation(JavaField javaField) {
        VideoValidation validation = new VideoValidation();
        // TODO
        return validation;
    }
}
