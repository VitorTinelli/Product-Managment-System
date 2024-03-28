package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BrandPutRequestBody {

  //@Not Null is used to validate if the id is not null (a number cannot be empty)
  @NotNull(message = "The brand id can't be empty")
  @Schema(description = "This is the brand's id", example = "1")
  private Long id;

  // @NotBlank is used to validate if the name is not null or empty
  @NotBlank(message = "The brand name can't be empty")
  @Schema(description = "This is the brand's name", example = "Coca-Cola")
  private String name;
}
