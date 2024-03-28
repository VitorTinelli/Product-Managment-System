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
import vitor.tinelli.domain.Brand;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.repository.BrandRepository;
import vitor.tinelli.requests.BrandPostRequestBody;
import vitor.tinelli.requests.BrandPutRequestBody;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

  Brand brand;
  BrandPostRequestBody brandPostRequestBody;
  BrandPutRequestBody brandPutRequestBody;

  @InjectMocks
  private BrandService brandService;
  @Mock
  private BrandRepository brandRepository;

  @BeforeEach
  void setUp() {

    brandPutRequestBody = new BrandPutRequestBody();
    brandPutRequestBody.setId(100L);
    brandPutRequestBody.setName("Brand PUT Test");

    brandPostRequestBody = new BrandPostRequestBody();
    brandPostRequestBody.setName("Brand POST Test");

    brand = new Brand(100L, "Brand Test");
  }

  @Test
  @DisplayName("listAll returns list of brands when successful")
  void listAll_ReturnAllBrands_WhenSuccessful() {
    when(brandRepository.findAll()).thenReturn(List.of(brand));
    List<Brand> brands = brandService.listAll();

    verify(brandRepository).findAll();
    verifyNoMoreInteractions(brandRepository);
    Assertions.assertEquals(List.of(brand), brands);
  }

  @Test
  @DisplayName("listByName Return All Brands With The Same Name wen successful")
  void listByName_ReturnAllBrandsWithTheSameName_WhenSuccessful() {
    when(brandRepository.findByName("Brand Test")).thenReturn(List.of(brand));
    List<Brand> brands = brandService.findByName("Brand Test");

    verify(brandRepository).findByName("Brand Test");
    verifyNoMoreInteractions(brandRepository);
    Assertions.assertEquals(List.of(brand), brands);
  }

  @Test
  @DisplayName("listById return brand when successful")
  void listById_ReturnBrand_WhenSuccessful() {
    when(brandRepository.findById(1L)).thenReturn(Optional.ofNullable(brand));
    Brand brandFound = brandService.findByIdOrThrowBadRequestException(1L);

    verify(brandRepository).findById(1L);
    verifyNoMoreInteractions(brandRepository);
    Assertions.assertEquals(brand, brandFound);
  }

  @Test
  @DisplayName("listByName return empty list when brand not exist")
  void listByName_ReturnEmptyList_WhenBrandNotExist() {
    when(brandRepository.findByName("Unknown Brand")).thenReturn(Collections.emptyList());

    List<Brand> brands = brandService.findByName("Unknown Brand");

    verify(brandRepository).findByName("Unknown Brand");
    verifyNoMoreInteractions(brandRepository);
    Assertions.assertTrue(brands.isEmpty());

  }

  @Test
  @DisplayName("listById throws BadRequestException when brand not exist")
  void listById_TrowBadRequestException_WhenBrandNotExist() {
    when(brandRepository.findById(1L)).thenThrow(new RuntimeException("Brand not found"));

    Assertions.assertThrows(RuntimeException.class,
        () -> brandService.findByIdOrThrowBadRequestException(1L));

    verify(brandRepository).findById(1L);
    verifyNoMoreInteractions(brandRepository);
  }

  @Test
  @DisplayName("post saves brand when successful")
  void post_SavesBrand_WhenSuccessful() {
    when(brandRepository.save(any(Brand.class))).thenReturn(brand);
    Brand savedBrand = brandService.save(brandPostRequestBody);

    verify(brandRepository).save(any(Brand.class));
    verifyNoMoreInteractions(brandRepository);
    Assertions.assertEquals(brand, savedBrand);
  }

  @Test
  @DisplayName("post throws BadRequestException when brand name is blank")
  void post_ThrowException_WhenBrandNameIsBlank() {
    brandPostRequestBody.setName(" ");
    when(brandRepository.save(any(Brand.class))).thenThrow(
        new BadRequestException("Brand name cannot be blank"));

    Assertions.assertThrows(BadRequestException.class,
        () -> brandService.save(brandPostRequestBody));
    verify(brandRepository).save(any(Brand.class));
    verifyNoMoreInteractions(brandRepository);
  }

  @Test
  @DisplayName("delete deletes brand when successful")
  void delete_DeletesBrand_WhenSuccessful() {
    when(brandRepository.findById(2L)).thenReturn(Optional.of(brand));

    Assertions.assertDoesNotThrow(() -> brandService.delete(2L));
    Assertions.assertEquals(Collections.emptyList(), brandService.listAll());

    verify(brandRepository).findById(2L);
    verify(brandRepository).delete(brand);
  }

  @Test
  @DisplayName("delete throws BadRequestException when brand not exist")
  void delete_ThrowException_WhenBrandNotExist() {
    when(brandRepository.findById(any())).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class, () -> brandService.delete(1L));
    verify(brandRepository).findById(1L);
    verifyNoMoreInteractions(brandRepository);
  }

  @Test
  @DisplayName("put replace brand when successful")
  void put_ReplaceBrand_WhenSuccessful() {
    when(brandRepository.findById(100L)).thenReturn(Optional.of(brand));
    when(brandRepository.save(any(Brand.class))).thenReturn(brand);

    Assertions.assertDoesNotThrow(() -> brandService.replace(brandPutRequestBody));

    Assertions.assertEquals(brand, brandService.findByIdOrThrowBadRequestException(100L));

    verify(brandRepository).save(any(Brand.class));
  }

  @Test
  @DisplayName("put throws BadRequestException when brand not exist")
  void put_ThrowException_WhenBrandNotExist() {
    when(brandRepository.findById(100L)).thenReturn(Optional.empty());

    Assertions.assertThrows(BadRequestException.class,
        () -> brandService.replace(brandPutRequestBody));
    Assertions.assertEquals(Collections.emptyList(), brandService.listAll());

    verify(brandRepository).findById(100L);
    verify(brandRepository, never()).save(any(Brand.class));
    verify(brandRepository, never()).delete(any(Brand.class));
    verify(brandRepository).findAll();
  }

  @Test
  @DisplayName("put throws BadRequestException when brand name is blank")
  void put_ThrowException_WhenBrandNameIsBlank() {
    brandPutRequestBody.setName(" ");
    when(brandRepository.findById(100L)).thenReturn(Optional.of(brand));
    doThrow(new BadRequestException("Brand name cannot be blank"))
        .when(brandRepository).save(any(Brand.class));

    Assertions.assertThrows(BadRequestException.class,
        () -> brandService.replace(brandPutRequestBody));
    Assertions.assertEquals(brand, brandService.findByIdOrThrowBadRequestException(100L));
    Assertions.assertNotEquals(" ",
        brandService.findByIdOrThrowBadRequestException(100L).getName());
    verify(brandRepository).save(any(Brand.class));
    verify(brandRepository, never()).delete(any(Brand.class));
  }

  @Test
  @DisplayName("put throws BadRequestException when id is null")
  void put_ThrowException_WhenIdIsNull() {
    brandPutRequestBody.setId(null);

    Assertions.assertThrows(NullPointerException.class,
        () -> brandService.replace(brandPutRequestBody));
    verify(brandRepository, never()).save(any(Brand.class));
    verify(brandRepository, never()).delete(any(Brand.class));
  }

}
