package vitor.tinelli.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductGroup {

  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
  @Column(name = "productGroup_id")
  private Long id;

  @Column(nullable = false, name = "productGroup_name")
  private String name;


  @JsonCreator
  public ProductGroup(@JsonProperty("id") Long id) {
    this.id = id;
  }

}
