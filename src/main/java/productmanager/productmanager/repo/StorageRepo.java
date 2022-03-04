package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.model.Storage;

import javax.transaction.Transactional;
import java.util.List;

public interface StorageRepo extends JpaRepository<Storage,Integer> {
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Transactional
    @Query("UPDATE Storage s set s.productName= :productName, s.productQuantityI= :productQuantityI, s.productQuantity= :productQuantity, s.productPrice= :productPrice, " +
            "s.promotionPrice= :promotionPrice, s.productImage= :productImage, s.description= :description, s.category= :category, s.state= :state WHERE " +
            "s.id= :id")
    public void updateProductById(@Param("productName") String productName, @Param("productQuantityI") int productQuantityI,@Param("productQuantity") int productQuantity ,@Param("productPrice") float productPrice,
                                       @Param("promotionPrice") float promotionPrice,@Param("productImage") String productImage,@Param("description") String description,@Param("category") String category,
                                           @Param("state") String state
                                      ,@Param("id") int id);

    Storage findStorageByProductName(String name);

    void deleteStorageById(int id);

    List<Storage> findStorageByCategory(String category);

    List<Storage> findStorageByState(String state);
}
