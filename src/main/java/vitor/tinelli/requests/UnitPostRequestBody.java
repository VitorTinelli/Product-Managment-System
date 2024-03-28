package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UnitPostRequestBody {

  @NotBlank(message = "The unit name can't be empty")
  @Schema(description = "This is the unit's name", example = "Metros")
  private String name;

}
