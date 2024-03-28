package vitor.tinelli.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vitor.tinelli.domain.Unit;

public interface UnitRepository extends JpaRepository <Unit, Long> {
  List<Unit> findByName(String name);
}
