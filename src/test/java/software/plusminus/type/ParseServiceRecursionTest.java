package software.plusminus.type;

import org.junit.Before;
import org.junit.Test;
import software.plusminus.type.helpers.FirstMutualEntity;
import software.plusminus.type.helpers.SecondMutualEntity;
import software.plusminus.type.helpers.SelfReferencingEntity;
import software.plusminus.type.model.Field;
import software.plusminus.type.model.Type;
import software.plusminus.type.model.field.RelationField;
import software.plusminus.type.parsers.FieldParser;
import software.plusminus.type.parsers.NumberFieldParser;
import software.plusminus.type.parsers.RelationFieldParser;
import software.plusminus.type.parsers.TextFieldParser;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ParseServiceRecursionTest {

    private ParseService parseService;

    @Before
    public void setUp() {
        List<FieldParser<? extends Field>> parsers = new ArrayList<>();
        parseService = new ParseService(parsers);
        RelationFieldParser relationParser = new RelationFieldParser(parseService, new TitleFieldService());
        parsers.add(relationParser);
        parsers.add(new TextFieldParser());
        parsers.add(new NumberFieldParser());
    }

    @Test
    public void parsesSelfReferencingEntity() {
        Type type = parseService.parse(SelfReferencingEntity.class);

        Field parent = findField(type, "parent");
        assertThat(parent).isInstanceOf(RelationField.class);
        assertThat(((RelationField) parent).getRelationType()).isSameAs(type);
    }

    @Test
    public void parsesMutuallyReferencingEntities() {
        Type first = parseService.parse(FirstMutualEntity.class);
        Type second = parseService.parse(SecondMutualEntity.class);

        Field toSecond = findField(first, "second");
        assertThat(((RelationField) toSecond).getRelationType()).isSameAs(second);
        Field toFirst = findField(second, "first");
        assertThat(((RelationField) toFirst).getRelationType()).isSameAs(first);
    }

    @Test
    public void failedParseIsNotCached() {
        Throwable first = catchThrowable(() -> parseService.parse(UnparsableEntity.class));
        Throwable second = catchThrowable(() -> parseService.parse(UnparsableEntity.class));

        assertThat(first).isInstanceOf(ParseException.class);
        assertThat(second).isInstanceOf(ParseException.class);
    }

    private Field findField(Type type, String name) {
        return type.getFields().stream()
                .filter(field -> name.equals(field.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Field not found: " + name));
    }

    private static class UnparsableEntity {

        @SuppressWarnings("java:S1068")
        private Object unsupported; //NOPMD - present only to trigger a parse failure

    }
}
