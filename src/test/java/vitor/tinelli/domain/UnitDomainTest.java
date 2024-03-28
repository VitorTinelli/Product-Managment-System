package vitor.tinelli.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UnitDomainTest {

  @InjectMocks
  private Unit unitDomain;

  @BeforeEach
  void setUp() {
    unitDomain = new Unit(1L, "unit Test");
  }

  @Test
  @DisplayName("Test if getId returns the correct id")
  void testGetId() {
    unitDomain.setId(1L);
    Assertions.assertEquals(1L, unitDomain.getId());
  }

  @Test
  @DisplayName("Test if getName returns the correct name")
  void testGetName() {
    unitDomain.setName("unit Test");
    Assertions.assertEquals("unit Test", unitDomain.getName());
  }

  @Test
  @DisplayName("Test the HashCode of the unitDomain class")
  void testHashCode() {
    Unit unit = new Unit(1L, "unit Test");
    Assertions.assertEquals(unit.hashCode(), unitDomain.hashCode());
  }

  @Test
  @DisplayName("Test toString of the unitDomain class")
  void testToString() {
    Unit unit = new Unit(1L, "unit Test");
    Assertions.assertEquals(unit.toString(), unitDomain.toString());
  }

  @Test
  @DisplayName("Test Constructor with id and name")
  void testConstructorWithIdAndName() {
    Long expectedId = 1L;
    String expetedName = "unit Test";
    Unit unit = new Unit(expectedId, expetedName);
    Assertions.assertEquals(expectedId, unit.getId());
    Assertions.assertEquals(expetedName, unit.getName());
  }

  @Test
  @DisplayName("Test Builder of the unitDomain class")
  void testBuilder() {
    Unit unit = Unit.builder()
        .id(1L)
        .name("unit Test")
        .build();
    Assertions.assertEquals(unit, unitDomain);

  }
}

