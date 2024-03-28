package vitor.tinelli.service;

import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vitor.tinelli.domain.Unit;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.repository.UnitRepository;
import vitor.tinelli.requests.UnitPostRequestBody;
import vitor.tinelli.requests.UnitPutRequestBody;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

  Unit unit;
  UnitPostRequestBody unitPostRequestBody;
  UnitPutRequestBody unitPutRequestBody;

  @InjectMocks
  private UnitService unitService;
  @Mock
  private UnitRepository unitRepository;

  @BeforeEach
  void setUp() {

    unitPutRequestBody = new UnitPutRequestBody();
    unitPutRequestBody.setId(100L);
    unitPutRequestBody.setName("unit PUT Test");

    unitPostRequestBody = new UnitPostRequestBody();
    unitPostRequestBody.setName("unit POST Test");

    unit = new Unit(100L, "unit Test");
  }

  @Test
  @DisplayName("listAll returns list of units when successful")
  void listAll_ReturnAllUnits_WhenSuccessful() {
    when(unitRepository.findAll()).thenReturn(List.of(unit));
    List<Unit> units = unitService.listAll();

    verify(unitRepository).findAll();
    verifyNoMoreInteractions(unitRepository);
    Assertions.assertEquals(List.of(unit), units);
  }

  @Test
  @DisplayName("listByName Return All units With The Same Name wen successful")
  void listByName_ReturnAllUnitsWithTheSameName_WhenSuccessful() {
    when(unitRepository.findByName("unit Test")).thenReturn(List.of(unit));
    List<Unit> units = unitService.findByName("unit Test");

    verify(unitRepository).findByName("unit Test");
    verifyNoMoreInteractions(unitRepository);
    Assertions.assertEquals(List.of(unit), units);
  }

  @Test
  @DisplayName("listById return unit when successful")
  void listById_ReturnUnit_WhenSuccessful() {
    when(unitRepository.findById(1L)).thenReturn(Optional.ofNullable(unit));
    Unit unitFound = unitService.findByIdOrThrowBadRequestException(1L);

    verify(unitRepository).findById(1L);
    verifyNoMoreInteractions(unitRepository);
    Assertions.assertEquals(unit, unitFound);
  }

  @Test
  @DisplayName("listByName return empty list when unit not exist")
  void listByName_ReturnEmptyList_WhenUnitNotExist() {
    when(unitRepository.findByName("Unknown unit")).thenReturn(Collections.emptyList());

    List<Unit> units = unitService.findByName("Unknown unit");

    verify(unitRepository).findByName("Unknown unit");
    verifyNoMoreInteractions(unitRepository);
    Assertions.assertTrue(units.isEmpty());

  }

  @Test
  @DisplayName("listById throws BadRequestException when unit not exist")
  void listById_TrowBadRequestException_WhenUnitNotExist() {
    when(unitRepository.findById(1L)).thenThrow(new RuntimeException("unit not found"));

    Assertions.assertThrows(RuntimeException.class,
        () -> unitService.findByIdOrThrowBadRequestException(1L));

    verify(unitRepository).findById(1L);
    verifyNoMoreInteractions(unitRepository);
  }

  @Test
  @DisplayName("post saves unit when successful")
  void post_SavesUnit_WhenSuccessful() {
    when(unitRepository.save(any(Unit.class))).thenReturn(unit);
    Unit savedunit = unitService.save(unitPostRequestBody);

    verify(unitRepository).save(any(Unit.class));
    verifyNoMoreInteractions(unitRepository);
    Assertions.assertEquals(unit, savedunit);
  }

  @Test
  @DisplayName("post throws BadRequestException when unit name is blank")
  void post_ThrowException_WhenUnitNameIsBlank() {
    unitPostRequestBody.setName(" ");
    when(unitRepository.save(any(Unit.class))).thenThrow(
        new BadRequestException("unit name cannot be blank"));

    Assertions.assertThrows(BadRequestException.class,
        () -> unitService.save(unitPostRequestBody));
    verify(unitRepository).save(any(Unit.class));
    verifyNoMoreInteractions(unitRepository);
  }

  @Test
  @DisplayName("delete deletes unit when successful")
  void delete_DeletesUnit_WhenSuccessful() {
    when(unitRepository.findById(2L)).thenReturn(Optional.of(unit));

    Assertions.assertDoesNotThrow(() -> unitService.delete(2L));
    Assertions.assertEquals(Collections.emptyList(), unitService.listAll());

    verify(unitRepository).findById(2L);
    verify(unitRepository).delete(unit);
  }

  @Test
  @DisplayName("delete throws BadRequestException when unit not exist")
  void delete_ThrowException_WhenUnitNotExist() {
    when(unitRepository.findById(any())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class, () -> unitService.delete(1L));
    verify(unitRepository).findById(1L);
    verifyNoMoreInteractions(unitRepository);
  }

  @Test
  @DisplayName("put replace unit when successful")
  void put_ReplaceUnit_WhenSuccessful() {
    when(unitRepository.findById(100L)).thenReturn(Optional.of(unit));
    when(unitRepository.save(any(Unit.class))).thenReturn(unit);

    Assertions.assertDoesNotThrow(() -> unitService.replace(unitPutRequestBody));

    Assertions.assertEquals(unit, unitService.findByIdOrThrowBadRequestException(100L));

    verify(unitRepository).save(any(Unit.class));
  }

  @Test
  @DisplayName("put throws BadRequestException when unit not exist")
  void put_ThrowException_WhenUnitNotExist() {
    when(unitRepository.findById(100L)).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> unitService.replace(unitPutRequestBody));
    Assertions.assertEquals(Collections.emptyList(), unitService.listAll());

    verify(unitRepository).findById(100L);
    verify(unitRepository, never()).save(any(Unit.class));
    verify(unitRepository, never()).delete(any(Unit.class));
    verify(unitRepository).findAll();
  }

  @Test
  @DisplayName("put throws BadRequestException when unit name is blank")
  void put_ThrowException_WhenUnitNameIsBlank() {
    unitPutRequestBody.setName(" ");
    when(unitRepository.findById(100L)).thenReturn(Optional.of(unit));
    doThrow(new BadRequestException("unit name cannot be blank"))
        .when(unitRepository).save(any(Unit.class));

    Assertions.assertThrows(BadRequestException.class,
        () -> unitService.replace(unitPutRequestBody));
    Assertions.assertEquals(unit, unitService.findByIdOrThrowBadRequestException(100L));
    Assertions.assertNotEquals(" ",
        unitService.findByIdOrThrowBadRequestException(100L).getName());
    verify(unitRepository).save(any(Unit.class));
    verify(unitRepository, never()).delete(any(Unit.class));
  }

//  @Test
//  @DisplayName("put throws BadRequestException when id is null")
//  void put_ThrowException_WhenIdIsNull() {
//    unitPutRequestBody.setId(null);
//
//    Assertions.assertThrows(NullPointerException.class,
//        () -> unitService.replace(unitPutRequestBody));
//    verify(unitRepository, never()).save(any(Unit.class));
//    verify(unitRepository, never()).delete(any(Unit.class));
//  }

}
