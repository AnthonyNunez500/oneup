package upc.edu.oneup.repository;

import upc.edu.oneup.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName(String productName);
    boolean existsByUserId(Long userId);
}
