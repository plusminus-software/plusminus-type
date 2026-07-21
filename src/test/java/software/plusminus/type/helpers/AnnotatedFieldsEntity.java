package software.plusminus.type.helpers;

import software.plusminus.type.model.TitleField;

@SuppressWarnings("unused")
public class AnnotatedFieldsEntity {

    @Any
    private Object any;
    @Embedded
    private EmbeddableEntity embedded;
    @Url
    private String url;
    @Video
    private String video;
    @Lob
    private String lob;
    @TitleField(name = "custom")
    private String titled;

}
