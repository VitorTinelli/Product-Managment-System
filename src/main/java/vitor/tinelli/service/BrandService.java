package vitor.tinelli.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.tinelli.domain.Brand;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.repository.BrandRepository;
import vitor.tinelli.requests.BrandPostRequestBody;
import vitor.tinelli.requests.BrandPutRequestBody;

@Service
@RequiredArgsConstructor
public class BrandService {

  private final BrandRepository brandRepository;

  public List<Brand> listAll() {
    return brandRepository.findAll();
  }

  public List<Brand> findByName(String name) {
    return brandRepository.findByName(name);
  }


  public Brand findByIdOrThrowBadRequestException(long id) {
    return brandRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "Brand not Found, Please verify the provided ID"));
  }

  @Transactional
  public Brand save(BrandPostRequestBody brandPostRequestBody) {
    return brandRepository.save(Brand.builder()
        .name(brandPostRequestBody.getName())
        .build());
  }

  public void delete(long id) {
    brandRepository.delete(findByIdOrThrowBadRequestException(id));
  }

  @Transactional
  public void replace(BrandPutRequestBody brandPutRequestBody) {
    Brand savedBrand = findByIdOrThrowBadRequestException(brandPutRequestBody.getId());
    Brand brand = Brand.builder()
        .id(savedBrand.getId())
        .name(brandPutRequestBody.getName())
        .build();

    brandRepository.save(brand);
  }
}