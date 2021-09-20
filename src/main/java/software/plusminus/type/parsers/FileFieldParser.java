package software.plusminus.type.parsers;

import org.springframework.stereotype.Component;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.FileField;
import software.plusminus.type.model.validation.FileValidation;

import java.io.File;
import java.io.InputStream;
import java.util.stream.Stream;

@Component
public class FileFieldParser implements FieldParser<FileField> {

    @Override
    public boolean supports(JavaField javaField) {
        return Stream.of(File.class, InputStream.class)
                .anyMatch(c -> c == javaField.getType());
    }

    @Override
    public FileField parse(JavaField javaField) {
        FileField fileField = new FileField();
        fileField.setValidation(getValidation(javaField));
        return fileField;
    }

    @SuppressWarnings("PMD")
    private FileValidation getValidation(JavaField javaField) {
        FileValidation validation = new FileValidation();
        // TODO
        return validation;
    }
}
