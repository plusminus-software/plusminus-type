package software.plusminus.type.helpers;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class ValidatedFieldsEntity {

    @NotBlank
    @Size(min = 2, max = 10)
    @Pattern(regexp = "[a-z]+")
    private String name;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer count;

    @DecimalMax("50.5")
    private Long amount;

    private String plain;

}
