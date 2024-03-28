package vitor.tinelli.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductDomainTest {

  Brand brand;
  ProductGroup productGroup;
  Unit unit;
  @InjectMocks
  private Product productDomain;

  @BeforeEach
  void setUp() {
    unit = new Unit(1L, "unit Test");
    productGroup = new ProductGroup(1L, "productGroup Test");
    brand = new Brand(1L, "brand Test");

    productDomain = new Product(1L, "product Test", unit, productGroup, brand);
  }

  @Test
  @DisplayName("Test if getId returns the correct id")
  void testGetId() {
    productDomain.setId(1L);
    Assertions.assertEquals(1L, productDomain.getId());
  }

  @Test
  @DisplayName("Test if getName returns the correct name")
  void testGetName() {
    productDomain.setName("product Test");
    Assertions.assertEquals("product Test", productDomain.getName());
  }

  @Test
  @DisplayName("Test the HashCode of the productDomain class")
  void testHashCode() {
    Product product = new Product(1L, "product Test", unit, productGroup, brand);
    Assertions.assertEquals(product.hashCode(), productDomain.hashCode());
  }

  @Test
  @DisplayName("Test toString of the productDomain class")
  void testToString() {
    Product product = new Product(1L, "product Test", unit, productGroup, brand);
    Assertions.assertEquals(productDomain.toString(), product.toString());
  }

  @Test
  @DisplayName("Test Constructor with id and name")
  void testConstructorWithIdAndName() {
    Long expectedId = 1L;
    String expectedName = "product Test";
    Product product = new Product(expectedId, expectedName, unit, productGroup, brand);
    Assertions.assertEquals(expectedId, product.getId());
    Assertions.assertEquals(expectedName, product.getName());
  }

  @Test
  @DisplayName("Test Builder of the productDomain class")
  void testBuilder() {
    Product product = Product.builder()
        .id(1L)
        .name("product Test")
        .unit(unit)
        .productGroup(productGroup)
        .brand(brand)
        .build();
    Assertions.assertEquals(productDomain, product);
  }
}

