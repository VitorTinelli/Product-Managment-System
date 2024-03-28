package vitor.tinelli.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vitor.tinelli.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
  List<Product> findByName(String name);
}
