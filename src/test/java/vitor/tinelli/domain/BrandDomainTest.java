package vitor.tinelli.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BrandDomainTest {

  @InjectMocks
  private Brand brandDomain;

  @BeforeEach
  void setUp() {
    brandDomain = new Brand(1L, "Brand Test");
  }

  @Test
  @DisplayName("Test if getId returns the correct id")
  void testGetId() {
    brandDomain.setId(1L);
    Assertions.assertEquals(1L, brandDomain.getId());
  }

  @Test
  @DisplayName("Test if getName returns the correct name")
  void testGetName() {
    brandDomain.setName("Brand Test");
    Assertions.assertEquals("Brand Test", brandDomain.getName());
  }

  @Test
  @DisplayName("Test the HashCode of the BrandDomain class")
  void testHashCode() {
    Brand brand = new Brand(1L, "Brand Test");
    Assertions.assertEquals(brand.hashCode(), brandDomain.hashCode());
  }

  @Test
  @DisplayName("Test toString of the BrandDomain class")
  void testToString() {
    Brand brand = new Brand(1L, "Brand Test");
    Assertions.assertEquals(brand.toString(), brandDomain.toString());
  }

  @Test
  @DisplayName("Test Constructor with id and name")
  void testConstructorWithIdAndName() {
    Long expectedId = 1L;
    String expetedName = "Brand Test";
    Brand brand = new Brand(expectedId, expetedName);
    Assertions.assertEquals(expectedId, brand.getId());
    Assertions.assertEquals(expetedName, brand.getName());
  }

  @Test
  @DisplayName("Test Builder of the BrandDomain class")
  void testBuilder() {
    Brand brand = Brand.builder()
        .id(1L)
        .name("Brand Test")
        .build();
    Assertions.assertEquals(brand, brandDomain);

  }
}

