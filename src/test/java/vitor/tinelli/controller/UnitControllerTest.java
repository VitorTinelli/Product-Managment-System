package vitor.tinelli.controller;

import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vitor.tinelli.domain.Unit;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.requests.UnitPostRequestBody;
import vitor.tinelli.requests.UnitPutRequestBody;
import vitor.tinelli.service.UnitService;

@ExtendWith(MockitoExtension.class)
class UnitControllerTest {

  Unit unit;
  UnitPostRequestBody unitPostRequestBody;

  UnitPutRequestBody unitPutRequestBody;
  @Mock
  private UnitService unitService;

  @InjectMocks
  private UnitController unitController;

  @BeforeEach
  void setUp() {
    unitPostRequestBody = new UnitPostRequestBody();
    unitPostRequestBody.setName("Unit POST Test");

    unitPutRequestBody = new UnitPutRequestBody();
    unitPutRequestBody.setId(100L);
    unitPutRequestBody.setName("Unit PUT Test");

    unit = new Unit(100L, "Unit Test");
  }

  @Test
  @DisplayName("listAll returns list of units when successful")
  void listAll_ReturnAllUnits_WhenSuccessful() {
    when(unitService.listAll()).thenReturn(List.of(unit));
    List<Unit> units = unitController.listAll();

    verify(unitService).listAll();
    verifyNoMoreInteractions(unitService);
    Assertions.assertEquals(List.of(unit), units);
  }

  @Test
  @DisplayName("listAll returns empty list when any unit exists")
  void listAll_ReturnEmptyList_WhenUnitNotExist() {
    when(unitService.listAll()).thenReturn(Collections.emptyList());
    List<Unit> units = unitController.listAll();

    verify(unitService).listAll();
    verifyNoMoreInteractions(unitService);
    Assertions.assertEquals(Collections.emptyList(), units);
    Assertions.assertNotEquals(List.of(unit), units);
  }

  @Test
  @DisplayName("listByID returns a unit when successful")
  void listByID_ReturnUnit_WhenSuccessful() {
    when(unitService.findByIdOrThrowBadRequestException(100L)).thenReturn(unit);
    Unit units = unitController.findById(100L);

    verify(unitService).findByIdOrThrowBadRequestException(100L);
    verifyNoMoreInteractions(unitService);
    Assertions.assertEquals(unit, units);
  }

  @Test
  @DisplayName("listByID throws BadRequestException when unit not exist")
  void listByID_ThrowBadRequestException_WhenUnitNotExist() {
    when(unitService.findByIdOrThrowBadRequestException(100L))
        .thenThrow(new BadRequestException("unit not found"));

    verifyNoMoreInteractions(unitService);
    Assertions.assertThrows(BadRequestException.class,
        () -> unitService.findByIdOrThrowBadRequestException(100L));
  }

  @Test
  @DisplayName("listByName returns list of units when successful")
  void listByName_ReturnAllUnitsWithTheSameName_WhenSuccessful() {
    when(unitService.findByName("unit Test")).thenReturn(List.of(unit));
    List<Unit> units = unitController.findByName("unit Test");

    verify(unitService).findByName("unit Test");
    verifyNoMoreInteractions(unitService);
    Assertions.assertEquals(List.of(unit), units);
  }

  @Test
  @DisplayName("listByName returns empty list when any unit exists")
  void listByName_ReturnEmptyList_WhenUnitNotExist() {
    when(unitService.findByName("Unknown unit")).thenReturn(Collections.emptyList());
    List<Unit> units = unitController.findByName("Unknown unit");

    verify(unitService).findByName("Unknown unit");
    verifyNoMoreInteractions(unitService);
    Assertions.assertEquals(Collections.emptyList(), units);
    Assertions.assertNotEquals(List.of(unit), units);
  }

  @Test
  @DisplayName("post saves unit when successful")
  void post_SavesUnit_WhenSuccessful() {
    unit.setName("unit POST Test");
    when(unitService.save(any(UnitPostRequestBody.class))).thenReturn(unit);

    Unit unitSaved = unitService.save(unitPostRequestBody);

    verify(unitService).save(any(UnitPostRequestBody.class));
    verifyNoMoreInteractions(unitService);
    Assertions.assertEquals(unitService.save(unitPostRequestBody), unitSaved);
  }

  @Test
  @DisplayName("post throws BadRequestException when unit name is null, empty or blank")
  void post_ThrowBadRequestException_WhenUnitNameIsBlank() {
    unitPostRequestBody.setName(" ");
    when(unitService.save(any(UnitPostRequestBody.class)))
        .thenThrow(new BadRequestException("unit name cannot be null"));

    verifyNoMoreInteractions(unitService);
    Assertions.assertThrows(BadRequestException.class,
        () -> unitController.save(unitPostRequestBody));
  }

  @Test
  @DisplayName("delete removes unit when successful")
  void delete_RemoveUnit_WhenSuccessful() {
    doNothing().when(unitService).delete(100L);

    Assertions.assertDoesNotThrow(() -> unitService.delete(100L));
    verify(unitService).delete(100L);
    verifyNoMoreInteractions(unitService);
  }

  @Test
  @DisplayName("delete throws BadRequestException when unit not exist")
  void delete_TrowBadRequestException_WhenunitNotExist() {
    doThrow(new BadRequestException("unit not found")).when(unitService).delete(100L);
    verifyNoMoreInteractions(unitService);
    Assertions.assertThrows(BadRequestException.class,
        () -> unitService.delete(100L));
  }

  @Test
  @DisplayName("replace updates unit when successful")
  void replace_ReplaceUnit_WhenSuccessful() {
    doNothing().when(unitService).replace(any(UnitPutRequestBody.class));

    unitController.replace(unitPutRequestBody);

    ArgumentCaptor<UnitPutRequestBody> argumentCaptor = ArgumentCaptor.forClass(
        UnitPutRequestBody.class);
    verify(unitService).replace(argumentCaptor.capture());
    verifyNoMoreInteractions(unitService);

    UnitPutRequestBody capturedArgument = argumentCaptor.getValue();
    Assertions.assertEquals(unitPutRequestBody, capturedArgument);
  }

  @Test
  @DisplayName("replace throws BadRequestException when unit name is null, empty or blank")
  void replace_ThrowBadRequestException_WhenUnitNameIsBlank() {
    unitPutRequestBody.setName(" ");
    doThrow(new BadRequestException("Unit name cannot be null")).when(unitService)
        .replace(any(UnitPutRequestBody.class));

    verifyNoMoreInteractions(unitService);
    Assertions.assertThrows(BadRequestException.class,
        () -> unitService.replace(unitPutRequestBody));
  }

//  @Test
//  @DisplayName("replace throws BadRequestException when unit id is null")
//  void replace_ThrowBadRequestException_WhenUnitIdIsNull() {
//    unitPutRequestBody.setId(null);
//    doThrow(new BadRequestException("unit id cannot be null")).when(unitService)
//        .replace(any(UnitPutRequestBody.class));
//
//    verifyNoMoreInteractions(unitService);
//    Assertions.assertThrows(BadRequestException.class,
//        () -> unitService.replace(unitPutRequestBody));
//  }

  @Test
  @DisplayName("replace throws BadRequestException when unit id not exist")
  void replace_ThrowBadRequestException_WhenunitIdNotExist() {
    unitPutRequestBody.setId(200L);
    doThrow(new BadRequestException("unit not found")).when(unitService)
        .replace(any(UnitPutRequestBody.class));

    verifyNoMoreInteractions(unitService);
    Assertions.assertThrows(BadRequestException.class,
        () -> unitService.replace(unitPutRequestBody));
  }
}
