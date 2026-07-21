# plusminus-type

Reflection-based type and field model for Java classes, used by Plusminus libraries (such as plusminus-admin) to describe domain entities.

The library parses a Java class into a `Type` — a tree of typed `Field` objects with their annotations and validation metadata. Consumers (for example, an admin UI generator) can then render forms or process entities without touching reflection themselves.

## Model and parsing

Core model classes live in `software.plusminus.type.model`:

* `Type` — name, namespace, parent `Type`, annotations, and a list of fields (`getAllFields()` walks the class hierarchy).
* `Field` — abstract base with a name, annotations, and a `Validation`; concrete subtypes live in `model.field`: `BooleanField`, `NumberField`, `TextField`, `EnumField`, `DateField`, `TimeField`, `DatetimeField`, `DatetimeLocalField`, `ColorField`, `RangeField`, `UrlField`, `UuidField`, `FileField`, `ImageField`, `VideoField`, `ArrayField`, `EmbeddedField`, `RelationField`, `AnyRelationField`.
* `Validation` and per-field-type subclasses in `model.validation` (e.g. `NumberValidation`, `TextValidation`).
* `Annotation` — a simplified name/value view of a Java annotation.

`ParseService` (a Spring `@Service`) builds the model via reflection, caching parsed types and handling cyclic entity graphs. Each field kind is recognised by a matching `FieldParser` implementation in `software.plusminus.type.parsers`:

```java
Type type = parseService.parse(User.class);
List<Field> fields = type.getAllFields(); // includes fields of parent classes
```

`TitleFieldService` resolves the "title" field of a related class — either via the `@TitleField` annotation or by falling back to fields named `name` or `title`.

The library ships Spring Boot auto-configuration (`TypeAutoconfig`, registered in `META-INF/spring.factories`), so its beans are picked up automatically.

## Getting started

Add the dependency:

```xml
<dependency>
    <groupId>software.plusminus</groupId>
    <artifactId>plusminus-type</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Building

Requires JDK 8. Build with the Maven wrapper:

```bash
./mvnw clean install
```

The build enforces Checkstyle, PMD, SpotBugs, and JaCoCo coverage checks (inherited from `plusminus-parent`).

## License

[Apache License 2.0](LICENSE)
