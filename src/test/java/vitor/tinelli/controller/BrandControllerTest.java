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
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.requests.BrandPostRequestBody;
import vitor.tinelli.requests.BrandPutRequestBody;
import vitor.tinelli.service.BrandService;

@ExtendWith(MockitoExtension.class)
class BrandControllerTest {

  Brand brand;
  BrandPostRequestBody brandPostRequestBody;

  BrandPutRequestBody brandPutRequestBody;
  @Mock
  private BrandService brandService;

  @InjectMocks
  private BrandController brandController;

  @BeforeEach
  void setUp() {
    brandPostRequestBody = new BrandPostRequestBody();
    brandPostRequestBody.setName("Brand POST Test");

    brandPutRequestBody = new BrandPutRequestBody();
    brandPutRequestBody.setId(100L);
    brandPutRequestBody.setName("Brand PUT Test");

    brand = new Brand(100L, "Brand Test");
  }

  @Test
  @DisplayName("listAll returns list of brands when successful")
  void listAll_ReturnAllBrands_WhenSuccessful() {
    when(brandService.listAll()).thenReturn(List.of(brand));
    ResponseEntity<List<Brand>> brands = brandController.listAll();

    verify(brandService).listAll();
    verifyNoMoreInteractions(brandService);
    Assertions.assertEquals(ResponseEntity.ok(List.of(brand)), brands);
  }

  @Test
  @DisplayName("listAll returns empty list when any brand exists")
  void listAll_ReturnEmptyList_WhenBrandNotExist() {
    when(brandService.listAll()).thenReturn(Collections.emptyList());
    ResponseEntity<List<Brand>> brands = brandController.listAll();

    verify(brandService).listAll();
    verifyNoMoreInteractions(brandService);
    Assertions.assertEquals(ResponseEntity.ok(Collections.emptyList()), brands);
    Assertions.assertNotEquals(ResponseEntity.ok(List.of(brand)), brands);
  }

  @Test
  @DisplayName("listByID returns a brand when successful")
  void listByID_ReturnBrand_WhenSuccessful() {
    when(brandService.findByIdOrThrowBadRequestException(100L)).thenReturn(brand);
    ResponseEntity<Brand> brands = brandController.findById(100L);

    verify(brandService).findByIdOrThrowBadRequestException(100L);
    verifyNoMoreInteractions(brandService);
    Assertions.assertEquals(ResponseEntity.ok(brand), brands);
  }

  @Test
  @DisplayName("listByID throws BadRequestException when brand not exist")
  void listByID_ThrowBadRequestException_WhenBrandNotExist() {
    when(brandService.findByIdOrThrowBadRequestException(100L))
        .thenThrow(new BadRequestException("Brand not found"));

    verifyNoMoreInteractions(brandService);
    Assertions.assertThrows(BadRequestException.class,
        () -> brandController.findById(100L));
  }

  @Test
  @DisplayName("listByName returns list of brands when successful")
  void listByName_ReturnAllBrandsWithTheSameName_WhenSuccessful() {
    when(brandService.findByName("Brand Test")).thenReturn(List.of(brand));
    ResponseEntity<List<Brand>> brands = brandController.findByName("Brand Test");

    verify(brandService).findByName("Brand Test");
    verifyNoMoreInteractions(brandService);
    Assertions.assertEquals(ResponseEntity.ok(List.of(brand)), brands);
  }

  @Test
  @DisplayName("listByName returns empty list when any brand exists")
  void listByName_ReturnEmptyList_WhenBrandNotExist() {
    when(brandService.findByName("Unknown Brand")).thenReturn(Collections.emptyList());
    ResponseEntity<List<Brand>> brands = brandController.findByName("Unknown Brand");

    verify(brandService).findByName("Unknown Brand");
    verifyNoMoreInteractions(brandService);
    Assertions.assertEquals(ResponseEntity.ok(Collections.emptyList()), brands);
    Assertions.assertNotEquals(ResponseEntity.ok(List.of(brand)), brands);
  }

  @Test
  @DisplayName("post saves brand when successful")
  void post_SavesBrand_WhenSuccessful() {
    brand.setName("Brand POST Test");
    when(brandService.save(any(BrandPostRequestBody.class))).thenReturn(brand);

    ResponseEntity<Brand> brandSaved = brandController.save(brandPostRequestBody);

    verify(brandService).save(any(BrandPostRequestBody.class));
    verifyNoMoreInteractions(brandService);
    Assertions.assertEquals(
        new ResponseEntity<>(brandService.save(brandPostRequestBody), HttpStatus.CREATED),
        brandSaved);
  }

  @Test
  @DisplayName("post throws BadRequestException when brand name is null, empty or blank")
  void post_ThrowBadRequestException_WhenBrandNameIsBlank() {
    brandPostRequestBody.setName(" ");
    when(brandService.save(any(BrandPostRequestBody.class)))
        .thenThrow(new BadRequestException("Brand name cannot be null"));

    verifyNoMoreInteractions(brandService);
    Assertions.assertThrows(BadRequestException.class,
        () -> brandController.save(brandPostRequestBody));
  }

  @Test
  @DisplayName("delete removes brand when successful")
  void delete_RemoveBrand_WhenSuccessful() {
    doNothing().when(brandService).delete(100L);
    ResponseEntity<Void> responseEntity = brandController.delete(100L);

    verify(brandService).delete(100L);
    verifyNoMoreInteractions(brandService);
    Assertions.assertEquals(ResponseEntity.noContent().build(), responseEntity);
  }

  @Test
  @DisplayName("delete throws BadRequestException when brand not exist")
  void delete_TrowBadRequestException_WhenBrandNotExist() {
    doThrow(new BadRequestException("Brand not found")).when(brandService).delete(100L);
    verifyNoMoreInteractions(brandService);
    Assertions.assertThrows(BadRequestException.class,
        () -> brandService.delete(100L));
  }

  @Test
  @DisplayName("replace updates brand when successful")
  void replace_ReplaceBrand_WhenSuccessful() {
    doNothing().when(brandService).replace(any(BrandPutRequestBody.class));
    ResponseEntity<Void> responseEntity = brandController.replace(brandPutRequestBody);

    verify(brandService).replace(any(BrandPutRequestBody.class));
    verifyNoMoreInteractions(brandService);
    Assertions.assertEquals(ResponseEntity.noContent().build(), responseEntity);
  }

  @Test
  @DisplayName("replace throws BadRequestException when brand name is null, empty or blank")
  void replace_ThrowBadRequestException_WhenBrandNameIsBlank() {
    brandPutRequestBody.setName(" ");
    doThrow(new BadRequestException("Brand name cannot be null")).when(brandService)
        .replace(any(BrandPutRequestBody.class));

    verifyNoMoreInteractions(brandService);
    Assertions.assertThrows(BadRequestException.class,
        () -> brandService.replace(brandPutRequestBody));
  }

  @Test
  @DisplayName("replace throws BadRequestException when brand id is null")
  void replace_ThrowBadRequestException_WhenBrandIdIsNull() {
    brandPutRequestBody.setId(null);
    doThrow(new BadRequestException("Brand id cannot be null")).when(brandService)
        .replace(any(BrandPutRequestBody.class));

    verifyNoMoreInteractions(brandService);
    Assertions.assertThrows(BadRequestException.class,
        () -> brandService.replace(brandPutRequestBody));
  }

  @Test
  @DisplayName("replace throws BadRequestException when brand id not exist")
  void replace_ThrowBadRequestException_WhenBrandIdNotExist() {
    brandPutRequestBody.setId(200L);
    doThrow(new BadRequestException("Brand not found")).when(brandService)
        .replace(any(BrandPutRequestBody.class));

    verifyNoMoreInteractions(brandService);
    Assertions.assertThrows(BadRequestException.class,
        () -> brandService.replace(brandPutRequestBody));
  }
}
