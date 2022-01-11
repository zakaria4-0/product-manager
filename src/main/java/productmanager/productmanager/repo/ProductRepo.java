package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import productmanager.productmanager.model.Product;

public interface ProductRepo extends JpaRepository<Product,Integer> {
}
