package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.VideoField;
import software.plusminus.type.model.validation.VideoValidation;

@Component
public class VideoFieldParser implements FieldParser<VideoField> {

    @Override
    public boolean supports(JavaField javaField) {
        //TODO
        return false;
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
