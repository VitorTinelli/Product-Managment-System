package vitor.tinelli.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class UnitPutRequestBody {

  @NotNull(message = "The id can not be null")
  @Schema(description = "This is the unit's id", example = "2")
  private long id;

  @NotBlank(message = "The name can not be empty")
  @Schema(description = "This is the unit's name", example = "Metros")
  private String name;

}
