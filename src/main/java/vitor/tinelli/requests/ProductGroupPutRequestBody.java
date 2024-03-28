package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductGroupPutRequestBody {

  @NotNull(message = "The product group id can't be empty")
  @Schema(description = "This is the product group's id", example = "1")
  private Long id;

  @NotBlank(message = "The product group name can't be empty")
  @Schema(description = "This is the product group's name", example = "Sucos")
  private String name;

}
