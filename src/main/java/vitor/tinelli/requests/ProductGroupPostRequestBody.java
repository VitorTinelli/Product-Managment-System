package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductGroupPostRequestBody {
  @NotBlank(message = "The product group name can't be empty")
  @Schema(description = "This is the product group's name", example = "Refrigerantes")
  private String name;

}
