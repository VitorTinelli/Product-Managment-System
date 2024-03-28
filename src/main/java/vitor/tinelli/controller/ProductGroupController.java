package vitor.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.ProductGroup;
import vitor.tinelli.requests.ProductGroupPostRequestBody;
import vitor.tinelli.requests.ProductGroupPutRequestBody;
import vitor.tinelli.service.ProductGroupService;

@RestController
@RequestMapping("product/groups")
@RequiredArgsConstructor
public class ProductGroupController {

  private final ProductGroupService productGroupService;

  @GetMapping
  public ResponseEntity<List<ProductGroup>> listAll() {
    return ResponseEntity.ok(productGroupService.listAll());
  }

  @GetMapping("/find")
  public ResponseEntity<List<ProductGroup>> findByName(@RequestParam @Valid String name) {
    return ResponseEntity.ok(productGroupService.findByName(name));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<ProductGroup> findById(@PathVariable long id) {
    return ResponseEntity.ok(productGroupService.findByIdOrThrowBadRequestException(id));
  }

  @PostMapping
  public ResponseEntity<ProductGroup> save(
      @RequestBody @Valid ProductGroupPostRequestBody productGroupPostRequestBody) {
    return new ResponseEntity<>(productGroupService.save(productGroupPostRequestBody),
        HttpStatus.CREATED);
  }

  @DeleteMapping(path = "admin/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    productGroupService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping
  public ResponseEntity<Void> replace(@RequestBody @Valid ProductGroupPutRequestBody productGroupPutRequestBody) {
    productGroupService.replace(productGroupPutRequestBody);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
