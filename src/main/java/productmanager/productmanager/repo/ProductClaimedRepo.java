package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.model.ProductClaimed;

import java.time.LocalDate;
import java.util.List;

public interface ProductClaimedRepo extends JpaRepository<ProductClaimed,Integer> {
    @Query("SELECT p FROM Reclamation r JOIN r.productClaimeds p WHERE r.month= :month")
    List<ProductClaimed> findProductClaimedByMonth(@Param(("month")) String month);
}
