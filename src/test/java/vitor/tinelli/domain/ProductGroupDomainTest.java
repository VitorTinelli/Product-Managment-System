package vitor.tinelli.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductGroupDomainTest {

  @InjectMocks
  private ProductGroup productGroupDomain;

  @BeforeEach
  void setUp() {
    productGroupDomain = new ProductGroup(1L, "productGroup Test");
  }

  @Test
  @DisplayName("Test if getId returns the correct id")
  void testGetId() {
    productGroupDomain.setId(1L);
    Assertions.assertEquals(1L, productGroupDomain.getId());
  }

  @Test
  @DisplayName("Test if getName returns the correct name")
  void testGetName() {
    productGroupDomain.setName("productGroup Test");
    Assertions.assertEquals("productGroup Test", productGroupDomain.getName());
  }

  @Test
  @DisplayName("Test the HashCode of the productGroupDomain class")
  void testHashCode() {
    ProductGroup productGroup = new ProductGroup(1L, "productGroup Test");
    Assertions.assertEquals(productGroup.hashCode(), productGroupDomain.hashCode());
  }

  @Test
  @DisplayName("Test toString of the productGroupDomain class")
  void testToString() {
    ProductGroup productGroup = new ProductGroup(1L, "productGroup Test");
    Assertions.assertEquals(productGroup.toString(), productGroupDomain.toString());
  }

  @Test
  @DisplayName("Test Constructor with id and name")
  void testConstructorWithIdAndName() {
    Long expectedId = 1L;
    String expetedName = "productGroup Test";
    ProductGroup productGroup = new ProductGroup(expectedId, expetedName);
    Assertions.assertEquals(expectedId, productGroup.getId());
    Assertions.assertEquals(expetedName, productGroup.getName());
  }

  @Test
  @DisplayName("Test Builder of the productGroupDomain class")
  void testBuilder() {
    ProductGroup productGroup = ProductGroup.builder()
        .id(1L)
        .name("productGroup Test")
        .build();
    Assertions.assertEquals(productGroup, productGroupDomain);
  }
}

