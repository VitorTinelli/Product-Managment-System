package vitor.tinelli.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vitor.tinelli.domain.Unit;
import vitor.tinelli.exception.BadRequestException;
import vitor.tinelli.repository.UnitRepository;
import vitor.tinelli.requests.UnitPostRequestBody;
import vitor.tinelli.requests.UnitPutRequestBody;

@Service
@RequiredArgsConstructor
public class UnitService {
  private final UnitRepository unitRepository;

  public List<Unit> listAll(){
    return unitRepository.findAll();
  }

  public List<Unit> findByName(String name){
    return unitRepository.findByName(name);
  }

  public Unit findByIdOrThrowBadRequestException(long id){
    return unitRepository.findById(id)
        .orElseThrow(() -> new BadRequestException(
            "Unit not found, please verify the provided ID"));
  }

  public Unit save(UnitPostRequestBody unitPostRequestBody) {
    return unitRepository.save(Unit.builder()
        .name(unitPostRequestBody.getName())
        .build());
  }

  public void delete(long id) {
    unitRepository.delete(findByIdOrThrowBadRequestException(id));
  }

  public void replace(UnitPutRequestBody unitPutRequestBody) {
    Unit savedUnit = findByIdOrThrowBadRequestException(unitPutRequestBody.getId());
    unitRepository.save(Unit.builder()
            .id(savedUnit.getId())
            .name(unitPutRequestBody.getName())
        .build());
  }
}
