package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BrandPostRequestBody {

  // @NotBlank is used to validate if the name is not null or empty
  // (I am not using @NotEmpty because using @NotEmpty we can pass a string with spaces)
  @NotBlank(message = "The brand name can't be empty")
  @Schema(description = "This is the brand's name", example = "Pepsi")
  private String name;

}
