package vitor.tinelli.controller;

import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vitor.tinelli.domain.ProductGroup;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.requests.ProductGroupPostRequestBody;
import vitor.tinelli.requests.ProductGroupPutRequestBody;
import vitor.tinelli.service.ProductGroupService;

@ExtendWith(MockitoExtension.class)
class ProductGroupControllerTest {

  ProductGroup productGroup;
  ProductGroupPostRequestBody productGroupPostRequestBody;

  ProductGroupPutRequestBody productGroupPutRequestBody;
  @Mock
  private ProductGroupService productGroupService;

  @InjectMocks
  private ProductGroupController productGroupController;

  @BeforeEach
  void setUp() {
    productGroupPostRequestBody = new ProductGroupPostRequestBody();
    productGroupPostRequestBody.setName("productGroup POST Test");

    productGroupPutRequestBody = new ProductGroupPutRequestBody();
    productGroupPutRequestBody.setId(100L);
    productGroupPutRequestBody.setName("productGroup PUT Test");

    productGroup = new ProductGroup(100L, "productGroup Test");
  }

  @Test
  @DisplayName("listAll returns list of productGroups when successful")
  void listAll_ReturnAllProductGroups_WhenSuccessful() {
    when(productGroupService.listAll()).thenReturn(List.of(productGroup));
    ResponseEntity<List<ProductGroup>> productGroups = productGroupController.listAll();

    verify(productGroupService).listAll();
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertEquals(ResponseEntity.ok(List.of(productGroup)), productGroups);
  }

  @Test
  @DisplayName("listAll returns empty list when any productGroup exists")
  void listAll_ReturnEmptyList_WhenProductGroupNotExist() {
    when(productGroupService.listAll()).thenReturn(Collections.emptyList());
    ResponseEntity<List<ProductGroup>> productGroups = productGroupController.listAll();

    verify(productGroupService).listAll();
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertEquals(ResponseEntity.ok(Collections.emptyList()), productGroups);
    Assertions.assertNotEquals(ResponseEntity.ok(List.of(productGroup)), productGroups);
  }

  @Test
  @DisplayName("listByID returns a productGroup when successful")
  void listByID_ReturnProductGroup_WhenSuccessful() {
    when(productGroupService.findByIdOrThrowBadRequestException(100L)).thenReturn(productGroup);
    ResponseEntity<ProductGroup> productGroups = productGroupController.findById(100L);

    verify(productGroupService).findByIdOrThrowBadRequestException(100L);
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertEquals(ResponseEntity.ok(productGroup), productGroups);
  }

  @Test
  @DisplayName("listByID throws BadRequestException when productGroup not exist")
  void listByID_ThrowBadRequestException_WhenProductGroupNotExist() {
    when(productGroupService.findByIdOrThrowBadRequestException(100L))
        .thenThrow(new BadRequestException("productGroup not found"));

    verifyNoMoreInteractions(productGroupService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupController.findById(100L));
  }

  @Test
  @DisplayName("listByName returns list of productGroups when successful")
  void listByName_ReturnAllProductGroupsWithTheSameName_WhenSuccessful() {
    when(productGroupService.findByName("productGroup Test")).thenReturn(List.of(productGroup));
    ResponseEntity<List<ProductGroup>> productGroups = productGroupController.findByName(
        "productGroup Test");

    verify(productGroupService).findByName("productGroup Test");
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertEquals(ResponseEntity.ok(List.of(productGroup)), productGroups);
  }

  @Test
  @DisplayName("listByName returns empty list when any productGroup exists")
  void listByName_ReturnEmptyList_WhenProductGroupNotExist() {
    when(productGroupService.findByName("Unknown productGroup")).thenReturn(
        Collections.emptyList());
    ResponseEntity<List<ProductGroup>> productGroups = productGroupController.findByName(
        "Unknown productGroup");

    verify(productGroupService).findByName("Unknown productGroup");
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertEquals(ResponseEntity.ok(Collections.emptyList()), productGroups);
    Assertions.assertNotEquals(ResponseEntity.ok(List.of(productGroup)), productGroups);
  }

  @Test
  @DisplayName("post saves productGroup when successful")
  void post_SavesProductGroup_WhenSuccessful() {
    productGroup.setName("productGroup POST Test");
    when(productGroupService.save(any(ProductGroupPostRequestBody.class))).thenReturn(productGroup);

    ResponseEntity<ProductGroup> productGroupSaved = productGroupController.save(
        productGroupPostRequestBody);

    verify(productGroupService).save(any(ProductGroupPostRequestBody.class));
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertEquals(
        new ResponseEntity<>(productGroupService.save(productGroupPostRequestBody),
            HttpStatus.CREATED),
        productGroupSaved);
  }

  @Test
  @DisplayName("post throws BadRequestException when productGroup name is null, empty or blank")
  void post_ThrowBadRequestException_WhenProductGroupNameIsBlank() {
    productGroupPostRequestBody.setName(" ");
    when(productGroupService.save(any(ProductGroupPostRequestBody.class)))
        .thenThrow(new BadRequestException("productGroup name cannot be null"));

    verifyNoMoreInteractions(productGroupService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupController.save(productGroupPostRequestBody));
  }

  @Test
  @DisplayName("delete removes productGroup when successful")
  void delete_RemoveProductGroup_WhenSuccessful() {
    doNothing().when(productGroupService).delete(100L);
    ResponseEntity<Void> responseEntity = productGroupController.delete(100L);

    verify(productGroupService).delete(100L);
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertEquals(ResponseEntity.noContent().build(), responseEntity);
  }

  @Test
  @DisplayName("delete throws BadRequestException when productGroup not exist")
  void delete_TrowBadRequestException_WhenProductGroupNotExist() {
    doThrow(new BadRequestException("productGroup not found")).when(productGroupService)
        .delete(100L);
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupService.delete(100L));
  }

  @Test
  @DisplayName("replace updates productGroup when successful")
  void replace_ReplaceProductGroup_WhenSuccessful() {
    doNothing().when(productGroupService).replace(any(ProductGroupPutRequestBody.class));
    ResponseEntity<Void> responseEntity = productGroupController.replace(
        productGroupPutRequestBody);

    verify(productGroupService).replace(any(ProductGroupPutRequestBody.class));
    verifyNoMoreInteractions(productGroupService);
    Assertions.assertEquals(ResponseEntity.noContent().build(), responseEntity);
  }

  @Test
  @DisplayName("replace throws BadRequestException when productGroup name is null, empty or blank")
  void replace_ThrowBadRequestException_WhenProductGroupNameIsBlank() {
    productGroupPutRequestBody.setName(" ");
    doThrow(new BadRequestException("productGroup name cannot be null")).when(productGroupService)
        .replace(any(ProductGroupPutRequestBody.class));

    verifyNoMoreInteractions(productGroupService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupService.replace(productGroupPutRequestBody));
  }

  @Test
  @DisplayName("replace throws BadRequestException when productGroup id is null")
  void replace_ThrowBadRequestException_WhenProductGroupIdIsNull() {
    productGroupPutRequestBody.setId(null);
    doThrow(new BadRequestException("productGroup id cannot be null")).when(productGroupService)
        .replace(any(ProductGroupPutRequestBody.class));

    verifyNoMoreInteractions(productGroupService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupService.replace(productGroupPutRequestBody));
  }

  @Test
  @DisplayName("replace throws BadRequestException when productGroup id not exist")
  void replace_ThrowBadRequestException_WhenProductGroupIdNotExist() {
    productGroupPutRequestBody.setId(200L);
    doThrow(new BadRequestException("productGroup not found")).when(productGroupService)
        .replace(any(ProductGroupPutRequestBody.class));

    verifyNoMoreInteractions(productGroupService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupService.replace(productGroupPutRequestBody));
  }
}
