package vitor.tinelli.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vitor.tinelli.domain.ProductGroup;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {

  List<ProductGroup> findByName(String name);

}
