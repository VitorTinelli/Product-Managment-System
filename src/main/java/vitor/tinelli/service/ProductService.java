package vitor.tinelli.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.tinelli.domain.Brand;
import vitor.tinelli.domain.Product;
import vitor.tinelli.domain.ProductGroup;
import vitor.tinelli.domain.Unit;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.repository.BrandRepository;
import vitor.tinelli.repository.ProductGroupRepository;
import vitor.tinelli.repository.ProductRepository;
import vitor.tinelli.repository.UnitRepository;
import vitor.tinelli.requests.ProductPostRequestBody;
import vitor.tinelli.requests.ProductPutRequestBody;


@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductGroupRepository productGroupRepository;
  private final BrandRepository brandRepository;
  private final UnitRepository unitRepository;

  public List<Product> listAll() {
    return productRepository.findAll();
  }


  public List<Product> findByName(String name) {
    return productRepository.findByName(name);
  }

  public Product findByIdOrThrowBadRequestException(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Product not found, please verify the provided ID"));
  }


  @Transactional
  public Product save(ProductPostRequestBody productPostRequestBody) {
    ProductGroup productGroup = productGroupRepository.findById(
            productPostRequestBody.getProductGroup_id())
        .orElseThrow(() -> new BadRequestException(
            "ProductGroup not found, check the provided ID"));
    Brand brand = brandRepository.findById(productPostRequestBody.getBrand_id())
        .orElseThrow(() -> new BadRequestException(
            "Brand not found, check the provided ID"));
    Unit unit = unitRepository.findById(productPostRequestBody.getUnit_id())
        .orElseThrow(() -> new BadRequestException(
            "Unit not found, check the provided ID"));

    return productRepository.save(Product.builder()
        .name(productPostRequestBody.getName())
        .brand(brand)
        .unit(unit)
        .productGroup(productGroup)
        .build());
  }

  public void delete(Long id) {
    productRepository.delete(findByIdOrThrowBadRequestException(id));
  }

  public void replace(ProductPutRequestBody productPutRequestBody) {

    Product savedProduct = productRepository.findById(productPutRequestBody.getId())
        .orElseThrow(() -> new BadRequestException("Product not found"));

    if (productPutRequestBody.getBrand_id() != null) {
      Brand brand = brandRepository.findById(productPutRequestBody.getBrand_id())
          .orElseThrow(() -> new BadRequestException("Brand not found"));
      savedProduct.setBrand(brand);
    }

    if (productPutRequestBody.getProductGroup_id() != null) {
      ProductGroup productGroup = productGroupRepository.findById(productPutRequestBody.getProductGroup_id())
          .orElseThrow(() -> new BadRequestException("ProductGroup not found"));
      savedProduct.setProductGroup(productGroup);
    }

    if (productPutRequestBody.getUnit_id() != null) {
      Unit unit = unitRepository.findById(productPutRequestBody.getUnit_id())
          .orElseThrow(() -> new RuntimeException("Unit not found"));
      savedProduct.setUnit(unit);
    }

    productRepository.save(Product.builder()
        .id(savedProduct.getId())
        .name(productPutRequestBody.getName())
        .brand(savedProduct.getBrand())
        .unit(savedProduct.getUnit())
        .productGroup(savedProduct.getProductGroup())
        .build());
  }

}
