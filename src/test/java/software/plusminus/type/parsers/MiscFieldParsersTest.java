package software.plusminus.type.parsers;

import org.junit.Test;
import software.plusminus.type.helpers.AnnotatedFieldsEntity;
import software.plusminus.type.helpers.Range;
import software.plusminus.type.model.JavaField;
import software.plusminus.type.model.field.ColorField;
import software.plusminus.type.model.field.FileField;
import software.plusminus.type.model.field.ImageField;
import software.plusminus.type.model.field.RangeField;
import software.plusminus.type.model.field.UrlField;
import software.plusminus.type.model.field.VideoField;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class MiscFieldParsersTest {

    @Test
    public void colorParserSupportsColorOnly() {
        ColorFieldParser parser = new ColorFieldParser();

        assertThat(parser.supports(javaField(Color.class))).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void colorParserParsesColorField() {
        ColorField field = new ColorFieldParser().parse(javaField(Color.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void fileParserSupportsFileAndInputStream() {
        FileFieldParser parser = new FileFieldParser();

        assertThat(parser.supports(javaField(File.class))).isTrue();
        assertThat(parser.supports(javaField(InputStream.class))).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void fileParserParsesFileField() {
        FileField field = new FileFieldParser().parse(javaField(File.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void imageParserSupportsBufferedImageOnly() {
        ImageFieldParser parser = new ImageFieldParser();

        assertThat(parser.supports(javaField(BufferedImage.class))).isTrue();
        assertThat(parser.supports(javaField(File.class))).isFalse();
    }

    @Test
    public void imageParserParsesImageField() {
        ImageField field = new ImageFieldParser().parse(javaField(BufferedImage.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void rangeParserSupportsTypesNamedRange() {
        RangeFieldParser parser = new RangeFieldParser();

        assertThat(parser.supports(javaField(Range.class))).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void rangeParserParsesRangeField() {
        RangeField field = new RangeFieldParser().parse(javaField(Range.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void urlParserSupportsUrlTypeAndUrlAnnotation() throws NoSuchFieldException {
        UrlFieldParser parser = new UrlFieldParser();
        JavaField annotated = javaField(String.class);
        annotated.setAnnotations(
                AnnotatedFieldsEntity.class.getDeclaredField("url").getAnnotations());

        assertThat(parser.supports(javaField(URL.class))).isTrue();
        assertThat(parser.supports(annotated)).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
    }

    @Test
    public void urlParserParsesUrlField() {
        UrlField field = new UrlFieldParser().parse(javaField(URL.class));

        assertThat(field.getValidation()).isNotNull();
    }

    @Test
    public void videoParserSupportsVideoAnnotationOnly() throws NoSuchFieldException {
        VideoFieldParser parser = new VideoFieldParser();
        JavaField annotated = javaField(String.class);
        annotated.setAnnotations(
                AnnotatedFieldsEntity.class.getDeclaredField("video").getAnnotations());

        assertThat(parser.supports(annotated)).isTrue();
        assertThat(parser.supports(javaField(String.class))).isFalse();
        assertThat(parser.supports(javaField(File.class))).isFalse();
    }

    @Test
    public void videoParserParsesVideoField() {
        VideoField field = new VideoFieldParser().parse(javaField(File.class));

        assertThat(field.getValidation()).isNotNull();
    }

    private JavaField javaField(Class<?> type) {
        JavaField javaField = new JavaField();
        javaField.setType(type);
        javaField.setAnnotations(new Annotation[0]);
        return javaField;
    }
}
