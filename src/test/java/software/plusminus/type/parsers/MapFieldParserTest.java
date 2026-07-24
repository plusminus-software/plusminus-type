package software.plusminus.type.parsers;

import org.junit.Test;
import software.plusminus.type.ParseService;
import software.plusminus.type.helpers.MapFieldEntity;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.Type;
import software.plusminus.type.model.field.ArrayField;
import software.plusminus.type.model.field.TextField;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MapFieldParserTest {

    @Test
    public void parsesMapFieldAsArrayOfValueType() {
        List<FieldParser<? extends Field>> parsers = new ArrayList<>();
        ParseService parseService = new ParseService(parsers);
        parsers.add(new ArrayFieldParser(parseService));
        parsers.add(new TextFieldParser());

        Type type = parseService.parse(MapFieldEntity.class);

        Field attributes = type.getFields().stream()
                .filter(f -> "attributes".equals(f.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Field not found: attributes"));
        assertThat(attributes).isInstanceOf(ArrayField.class);
        assertThat(((ArrayField) attributes).getArrayType()).isInstanceOf(TextField.class);
    }
}
