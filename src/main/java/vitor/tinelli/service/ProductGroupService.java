package vitor.tinelli.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.tinelli.domain.ProductGroup;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.repository.ProductGroupRepository;
import vitor.tinelli.requests.ProductGroupPostRequestBody;
import vitor.tinelli.requests.ProductGroupPutRequestBody;

@Service
@RequiredArgsConstructor
public class ProductGroupService {

  private final ProductGroupRepository productGroupRepository;

  public List<ProductGroup> listAll() {
    return productGroupRepository.findAll();
  }

  public List<ProductGroup> findByName(String name) {
    return productGroupRepository.findByName(name);
  }


  public ProductGroup findByIdOrThrowBadRequestException(long id) {
    return productGroupRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "Product group not Found, Please verify the provided ID"));
  }

  @Transactional
  public ProductGroup save(ProductGroupPostRequestBody productGroupPostRequestBody) {
    return productGroupRepository.save(ProductGroup.builder()
        .name(productGroupPostRequestBody.getName())
        .build());
  }

  public void delete(long id) {
    productGroupRepository.delete(findByIdOrThrowBadRequestException(id));
  }

  @Transactional
  public void replace(ProductGroupPutRequestBody productGroupPutRequestBody) {
    ProductGroup savedProductGroup = findByIdOrThrowBadRequestException(productGroupPutRequestBody.getId());
    ProductGroup productGroup = ProductGroup.builder()
        .id(savedProductGroup.getId())
        .name(productGroupPutRequestBody.getName())
        .build();

    productGroupRepository.save(productGroup);
  }
}