package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.model.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT r.products FROM Reservation r JOIN r.products p WHERE r.id= :id AND p.name= :name ")
    List<Product> findProductByCp_fkAndName(@Param("id") int codeCommand,@Param("name") String productName);


}
