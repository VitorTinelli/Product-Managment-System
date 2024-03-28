package vitor.tinelli.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private long id;

  @Column(name = "product_Name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "unit_id", referencedColumnName = "unit_id")
  private Unit unit;

  @ManyToOne
  @JoinColumn(name = "productGroup_id", referencedColumnName = "productGroup_id")
  private ProductGroup productGroup;

  @ManyToOne
  @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
  private Brand brand;

}
