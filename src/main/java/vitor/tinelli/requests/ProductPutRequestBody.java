package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ProductPutRequestBody {

  @NotNull(message = "The product id can't be empty")
  @Schema(description = "This is the product's id", example = "1")
  private Long id;

  @NotBlank(message = "The product name can't be empty")
  @Schema(description = "This is the product's name", example = "Pepsi Black")
  private String name;

  @Schema(description = "This is the product's brand id", example = "1")
  private Long brand_id;

  @Schema(description = "This is the product's group id", example = "1")
  private Long productGroup_id;

  @Schema(description = "This is the product's unit id", example = "2")
  private Long unit_id;


}
