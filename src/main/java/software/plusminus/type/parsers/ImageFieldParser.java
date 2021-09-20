package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.ImageField;
import software.plusminus.type.model.validation.ImageValidation;

import java.awt.image.BufferedImage;

@Component
public class ImageFieldParser implements FieldParser<ImageField> {

    @Override
    public boolean supports(JavaField javaField) {
        return javaField.getType() == BufferedImage.class;
    }

    @Override
    public ImageField parse(JavaField javaField) {
        ImageField imageField = new ImageField();
        imageField.setValidation(getValidation(javaField));
        return imageField;
    }

    @SuppressWarnings("PMD")
    private ImageValidation getValidation(JavaField javaField) {
        ImageValidation validation = new ImageValidation();
        // TODO
        return validation;
    }
}
