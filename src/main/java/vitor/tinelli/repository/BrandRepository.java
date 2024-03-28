package vitor.tinelli.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vitor.tinelli.domain.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

  List<Brand> findByName(String name);
}
