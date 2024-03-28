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
import vitor.tinelli.domain.Brand;
import vitor.tinelli.domain.Product;
import vitor.tinelli.domain.ProductGroup;
import vitor.tinelli.domain.Unit;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.requests.ProductPostRequestBody;
import vitor.tinelli.requests.ProductPutRequestBody;
import vitor.tinelli.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  Product product;
  ProductPostRequestBody productPostRequestBody;

  ProductPutRequestBody productPutRequestBody;

  Brand brand;
  Unit unit;
  ProductGroup productGroup;


  @Mock
  private ProductService productService;

  @InjectMocks
  private ProductController productController;

  @BeforeEach
  void setUp() {
    productPostRequestBody = new ProductPostRequestBody();
    productPostRequestBody.setName("product POST Test");

    productPutRequestBody = new ProductPutRequestBody();
    productPutRequestBody.setId(100L);
    productPutRequestBody.setName("product PUT Test");

    unit = new Unit(1L, "unit Test");
    productGroup = new ProductGroup(1L, "productGroup Test");
    brand = new Brand(1L, "brand Test");

    product = new Product(100L, "product Test", unit, productGroup, brand);
  }

  @Test
  @DisplayName("listAll returns list of products when successful")
  void listAll_ReturnAllProducts_WhenSuccessful() {
    when(productService.listAll()).thenReturn(List.of(product));
    ResponseEntity<List<Product>> products = productController.listAll();

    verify(productService).listAll();
    verifyNoMoreInteractions(productService);
    Assertions.assertEquals(ResponseEntity.ok(List.of(product)), products);
  }

  @Test
  @DisplayName("listAll returns empty list when any product exists")
  void listAll_ReturnEmptyList_WhenProductNotExist() {
    when(productService.listAll()).thenReturn(Collections.emptyList());
    ResponseEntity<List<Product>> products = productController.listAll();

    verify(productService).listAll();
    verifyNoMoreInteractions(productService);
    Assertions.assertEquals(ResponseEntity.ok(Collections.emptyList()), products);
    Assertions.assertNotEquals(ResponseEntity.ok(List.of(product)), products);
  }

  @Test
  @DisplayName("listByID returns a product when successful")
  void listByID_ReturnProduct_WhenSuccessful() {
    when(productService.findByIdOrThrowBadRequestException(100L)).thenReturn(product);
    ResponseEntity<Product> products = productController.findById(100L);

    verify(productService).findByIdOrThrowBadRequestException(100L);
    verifyNoMoreInteractions(productService);
    Assertions.assertEquals(ResponseEntity.ok(product), products);
  }

  @Test
  @DisplayName("listByID throws BadRequestException when product not exist")
  void listByID_ThrowBadRequestException_WhenProductNotExist() {
    when(productService.findByIdOrThrowBadRequestException(100L))
        .thenThrow(new BadRequestException("product not found"));

    verifyNoMoreInteractions(productService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productController.findById(100L));
  }

  @Test
  @DisplayName("listByName returns list of products when successful")
  void listByName_ReturnAllProductsWithTheSameName_WhenSuccessful() {
    when(productService.findByName("product Test")).thenReturn(List.of(product));
    ResponseEntity<List<Product>> products = productController.findByName(
        "product Test");

    verify(productService).findByName("product Test");
    verifyNoMoreInteractions(productService);
    Assertions.assertEquals(ResponseEntity.ok(List.of(product)), products);
  }

  @Test
  @DisplayName("listByName returns empty list when any product exists")
  void listByName_ReturnEmptyList_WhenProductNotExist() {
    when(productService.findByName("Unknown product")).thenReturn(
        Collections.emptyList());
    ResponseEntity<List<Product>> products = productController.findByName(
        "Unknown product");

    verify(productService).findByName("Unknown product");
    verifyNoMoreInteractions(productService);
    Assertions.assertEquals(ResponseEntity.ok(Collections.emptyList()), products);
    Assertions.assertNotEquals(ResponseEntity.ok(List.of(product)), products);
  }

  @Test
  @DisplayName("post saves product when successful")
  void post_SavesProduct_WhenSuccessful() {
    product.setName("product POST Test");
    when(productService.save(any(ProductPostRequestBody.class))).thenReturn(product);

    ResponseEntity<Product> productSaved = productController.save(
        productPostRequestBody);

    verify(productService).save(any(ProductPostRequestBody.class));
    verifyNoMoreInteractions(productService);
    Assertions.assertEquals(
        new ResponseEntity<>(productService.save(productPostRequestBody),
            HttpStatus.CREATED),
        productSaved);
  }

  @Test
  @DisplayName("post throws BadRequestException when product name is null, empty or blank")
  void post_ThrowBadRequestException_WhenProductNameIsBlank() {
    productPostRequestBody.setName(" ");
    when(productService.save(any(ProductPostRequestBody.class)))
        .thenThrow(new BadRequestException("product name cannot be null"));

    verifyNoMoreInteractions(productService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productController.save(productPostRequestBody));
  }

  @Test
  @DisplayName("delete removes product when successful")
  void delete_RemoveProduct_WhenSuccessful() {
    doNothing().when(productService).delete(100L);
    ResponseEntity<Void> responseEntity = productController.delete(100L);

    verify(productService).delete(100L);
    verifyNoMoreInteractions(productService);
    Assertions.assertEquals(ResponseEntity.noContent().build(), responseEntity);
  }

  @Test
  @DisplayName("delete throws BadRequestException when product not exist")
  void delete_TrowBadRequestException_WhenProductNotExist() {
    doThrow(new BadRequestException("product not found")).when(productService)
        .delete(100L);
    verifyNoMoreInteractions(productService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productService.delete(100L));
  }

  @Test
  @DisplayName("replace updates product when successful")
  void replace_ReplaceProduct_WhenSuccessful() {
    doNothing().when(productService).replace(any(ProductPutRequestBody.class));
    ResponseEntity<Void> responseEntity = productController.replace(
        productPutRequestBody);

    verify(productService).replace(any(ProductPutRequestBody.class));
    verifyNoMoreInteractions(productService);
    Assertions.assertEquals(ResponseEntity.noContent().build(), responseEntity);
  }

  @Test
  @DisplayName("replace throws BadRequestException when product name is null, empty or blank")
  void replace_ThrowBadRequestException_WhenProductNameIsBlank() {
    productPutRequestBody.setName(" ");
    doThrow(new BadRequestException("product name cannot be null")).when(productService)
        .replace(any(ProductPutRequestBody.class));

    verifyNoMoreInteractions(productService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productService.replace(productPutRequestBody));
  }

  @Test
  @DisplayName("replace throws BadRequestException when product id is null")
  void replace_ThrowBadRequestException_WhenProductIdIsNull() {
    productPutRequestBody.setId(null);
    doThrow(new BadRequestException("product id cannot be null")).when(productService)
        .replace(any(ProductPutRequestBody.class));

    verifyNoMoreInteractions(productService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productService.replace(productPutRequestBody));
  }

  @Test
  @DisplayName("replace throws BadRequestException when product id not exist")
  void replace_ThrowBadRequestException_WhenProductIdNotExist() {
    productPutRequestBody.setId(200L);
    doThrow(new BadRequestException("product not found")).when(productService)
        .replace(any(ProductPutRequestBody.class));

    verifyNoMoreInteractions(productService);
    Assertions.assertThrows(BadRequestException.class,
        () -> productService.replace(productPutRequestBody));
  }
}
