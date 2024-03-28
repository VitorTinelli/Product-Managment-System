package vitor.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.Product;
import vitor.tinelli.requests.ProductPostRequestBody;
import vitor.tinelli.requests.ProductPutRequestBody;
import vitor.tinelli.service.ProductService;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<Product>> listAll(){
    return ResponseEntity.ok(productService.listAll());
  }

  @GetMapping(path = "/find")
  public ResponseEntity<List<Product>> findByName(String name){
    return ResponseEntity.ok(productService.findByName(name));
  }

  @GetMapping(path = "{id}")
  public ResponseEntity<Product> findById(@PathVariable Long id){
    return ResponseEntity.ok(productService.findByIdOrThrowBadRequestException(id));
  }

  @PostMapping
  public ResponseEntity<Product> save(@RequestBody @Valid ProductPostRequestBody productPostRequestBody){
    return new ResponseEntity<>(productService.save(productPostRequestBody), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "admin/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    productService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping
  public ResponseEntity<Void> replace (@RequestBody ProductPutRequestBody productPutRequestBody){
    productService.replace(productPutRequestBody);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }



}
