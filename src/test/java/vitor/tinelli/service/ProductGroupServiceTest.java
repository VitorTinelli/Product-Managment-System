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
import vitor.tinelli.domain.ProductGroup;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.repository.ProductGroupRepository;
import vitor.tinelli.requests.ProductGroupPostRequestBody;
import vitor.tinelli.requests.ProductGroupPutRequestBody;

@ExtendWith(MockitoExtension.class)
class ProductGroupServiceTest {

  ProductGroup productGroup;
  ProductGroupPostRequestBody productGroupPostRequestBody;
  ProductGroupPutRequestBody productGroupPutRequestBody;

  @InjectMocks
  private ProductGroupService productGroupService;
  @Mock
  private ProductGroupRepository productGroupRepository;

  @BeforeEach
  void setUp() {

    productGroupPutRequestBody = new ProductGroupPutRequestBody();
    productGroupPutRequestBody.setId(100L);
    productGroupPutRequestBody.setName("productGroup PUT Test");

    productGroupPostRequestBody = new ProductGroupPostRequestBody();
    productGroupPostRequestBody.setName("productGroup POST Test");

    productGroup = new ProductGroup(100L, "productGroup Test");
  }

  @Test
  @DisplayName("listAll returns list of productGroups when successful")
  void listAll_ReturnAllProductGroups_WhenSuccessful() {
    when(productGroupRepository.findAll()).thenReturn(List.of(productGroup));
    List<ProductGroup> productGroups = productGroupService.listAll();

    verify(productGroupRepository).findAll();
    verifyNoMoreInteractions(productGroupRepository);
    Assertions.assertEquals(List.of(productGroup), productGroups);
  }

  @Test
  @DisplayName("listByName Return All productGroups With The Same Name wen successful")
  void listByName_ReturnAllProductGroupsWithTheSameName_WhenSuccessful() {
    when(productGroupRepository.findByName("productGroup Test")).thenReturn(List.of(productGroup));
    List<ProductGroup> productGroups = productGroupService.findByName("productGroup Test");

    verify(productGroupRepository).findByName("productGroup Test");
    verifyNoMoreInteractions(productGroupRepository);
    Assertions.assertEquals(List.of(productGroup), productGroups);
  }

  @Test
  @DisplayName("listById return productGroup when successful")
  void listById_ReturnProductGroup_WhenSuccessful() {
    when(productGroupRepository.findById(1L)).thenReturn(Optional.ofNullable(productGroup));
    ProductGroup productGroupFound = productGroupService.findByIdOrThrowBadRequestException(1L);

    verify(productGroupRepository).findById(1L);
    verifyNoMoreInteractions(productGroupRepository);
    Assertions.assertEquals(productGroup, productGroupFound);
  }

  @Test
  @DisplayName("listByName return empty list when productGroup not exist")
  void listByName_ReturnEmptyList_WhenProductGroupNotExist() {
    when(productGroupRepository.findByName("Unknown productGroup")).thenReturn(
        Collections.emptyList());

    List<ProductGroup> productGroups = productGroupService.findByName("Unknown productGroup");

    verify(productGroupRepository).findByName("Unknown productGroup");
    verifyNoMoreInteractions(productGroupRepository);
    Assertions.assertTrue(productGroups.isEmpty());

  }

  @Test
  @DisplayName("listById throws BadRequestException when productGroup not exist")
  void listById_TrowBadRequestException_WhenProductGroupNotExist() {
    when(productGroupRepository.findById(1L)).thenThrow(
        new RuntimeException("productGroup not found"));

    Assertions.assertThrows(RuntimeException.class,
        () -> productGroupService.findByIdOrThrowBadRequestException(1L));

    verify(productGroupRepository).findById(1L);
    verifyNoMoreInteractions(productGroupRepository);
  }

  @Test
  @DisplayName("post saves productGroup when successful")
  void post_SavesProductGroup_WhenSuccessful() {
    when(productGroupRepository.save(any(ProductGroup.class))).thenReturn(productGroup);
    ProductGroup savedproductGroup = productGroupService.save(productGroupPostRequestBody);

    verify(productGroupRepository).save(any(ProductGroup.class));
    verifyNoMoreInteractions(productGroupRepository);
    Assertions.assertEquals(productGroup, savedproductGroup);
  }

  @Test
  @DisplayName("post throws BadRequestException when productGroup name is blank")
  void post_ThrowException_WhenProductGroupNameIsBlank() {
    productGroupPostRequestBody.setName(" ");
    when(productGroupRepository.save(any(ProductGroup.class))).thenThrow(
        new BadRequestException("productGroup name cannot be blank"));

    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupService.save(productGroupPostRequestBody));
    verify(productGroupRepository).save(any(ProductGroup.class));
    verifyNoMoreInteractions(productGroupRepository);
  }

  @Test
  @DisplayName("delete deletes productGroup when successful")
  void delete_DeletesProductGroup_WhenSuccessful() {
    when(productGroupRepository.findById(2L)).thenReturn(Optional.of(productGroup));

    Assertions.assertDoesNotThrow(() -> productGroupService.delete(2L));
    Assertions.assertEquals(Collections.emptyList(), productGroupService.listAll());

    verify(productGroupRepository).findById(2L);
    verify(productGroupRepository).delete(productGroup);
  }

  @Test
  @DisplayName("delete throws BadRequestException when productGroup not exist")
  void delete_ThrowException_WhenProductGroupNotExist() {
    when(productGroupRepository.findById(any())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class, () -> productGroupService.delete(1L));
    verify(productGroupRepository).findById(1L);
    verifyNoMoreInteractions(productGroupRepository);
  }

  @Test
  @DisplayName("put replace productGroup when successful")
  void put_ReplaceProductGroup_WhenSuccessful() {
    when(productGroupRepository.findById(100L)).thenReturn(Optional.of(productGroup));
    when(productGroupRepository.save(any(ProductGroup.class))).thenReturn(productGroup);

    Assertions.assertDoesNotThrow(() -> productGroupService.replace(productGroupPutRequestBody));

    Assertions.assertEquals(productGroup,
        productGroupService.findByIdOrThrowBadRequestException(100L));

    verify(productGroupRepository).save(any(ProductGroup.class));
  }

  @Test
  @DisplayName("put throws BadRequestException when productGroup not exist")
  void put_ThrowException_WhenProductGroupNotExist() {
    when(productGroupRepository.findById(100L)).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupService.replace(productGroupPutRequestBody));
    Assertions.assertEquals(Collections.emptyList(), productGroupService.listAll());

    verify(productGroupRepository).findById(100L);
    verify(productGroupRepository, never()).save(any(ProductGroup.class));
    verify(productGroupRepository, never()).delete(any(ProductGroup.class));
    verify(productGroupRepository).findAll();
  }

  @Test
  @DisplayName("put throws BadRequestException when productGroup name is blank")
  void put_ThrowException_WhenProductGroupNameIsBlank() {
    productGroupPutRequestBody.setName(" ");
    when(productGroupRepository.findById(100L)).thenReturn(Optional.of(productGroup));
    doThrow(new BadRequestException("productGroup name cannot be blank"))
        .when(productGroupRepository).save(any(ProductGroup.class));

    Assertions.assertThrows(BadRequestException.class,
        () -> productGroupService.replace(productGroupPutRequestBody));
    Assertions.assertEquals(productGroup,
        productGroupService.findByIdOrThrowBadRequestException(100L));
    Assertions.assertNotEquals(" ",
        productGroupService.findByIdOrThrowBadRequestException(100L).getName());
    verify(productGroupRepository).save(any(ProductGroup.class));
    verify(productGroupRepository, never()).delete(any(ProductGroup.class));
  }

  @Test
  @DisplayName("put throws BadRequestException when id is null")
  void put_ThrowException_WhenIdIsNull() {
    productGroupPutRequestBody.setId(null);

    Assertions.assertThrows(NullPointerException.class,
        () -> productGroupService.replace(productGroupPutRequestBody));
    verify(productGroupRepository, never()).save(any(ProductGroup.class));
    verify(productGroupRepository, never()).delete(any(ProductGroup.class));
  }

}
