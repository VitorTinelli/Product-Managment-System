package vitor.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.Brand;
import vitor.tinelli.requests.BrandPostRequestBody;
import vitor.tinelli.requests.BrandPutRequestBody;
import vitor.tinelli.service.BrandService;

@RestController
@RequestMapping("brands")
@RequiredArgsConstructor
public class BrandController {

  private final BrandService brandService;

  @GetMapping
  public ResponseEntity<List<Brand>> listAll() {
    return ResponseEntity
        .ok(brandService.listAll());
  }

  @GetMapping("/find")
  public ResponseEntity<List<Brand>> findByName(@RequestParam String name) {
    return ResponseEntity.ok(brandService.findByName(name));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Brand> findById(@PathVariable long id) {
    return ResponseEntity.ok(brandService.findByIdOrThrowBadRequestException(id));
  }

  @PostMapping
  public ResponseEntity<Brand> save(@RequestBody @Valid BrandPostRequestBody brandPostRequestBody) {
    return new ResponseEntity<>(brandService.save(brandPostRequestBody), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "admin/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    brandService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping
  public ResponseEntity<Void> replace(@RequestBody @Valid BrandPutRequestBody brandPutRequestBody) {
    brandService.replace(brandPutRequestBody);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
