package vitor.tinelli.controller;

import javax.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vitor.tinelli.domain.Unit;
import vitor.tinelli.requests.UnitPostRequestBody;
import vitor.tinelli.requests.UnitPutRequestBody;
import vitor.tinelli.service.UnitService;

@RestController
@RequestMapping("units")
@RequiredArgsConstructor
public class UnitController {

  private final UnitService unitService;

  @GetMapping
  public List<Unit> listAll() {

    return unitService.listAll();
  }

  @GetMapping("/find")
  public List<Unit> findByName(@RequestParam String name) {
    return unitService.findByName(name);
  }

  @GetMapping(path = "/{id}")
  public Unit findById(@PathVariable long id) {
    return unitService.findByIdOrThrowBadRequestException(id);
  }

  @PostMapping
  public Unit save(@RequestBody @Valid UnitPostRequestBody unitPostRequestBody) {
    return unitService.save(unitPostRequestBody);
  }

  @DeleteMapping(path = "/admin/{id}")
  public void delete(@PathVariable long id) {
    unitService.delete(id);
  }

  @PutMapping
  public void replace(@RequestBody @Valid UnitPutRequestBody unitPutRequestBody) {
    unitService.replace(unitPutRequestBody);
  }

}
