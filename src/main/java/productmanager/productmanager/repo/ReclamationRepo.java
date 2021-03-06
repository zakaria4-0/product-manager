package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.model.Reclamation;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReclamationRepo extends JpaRepository<Reclamation,Integer> {
    List<Reclamation> findReclamationByYear(String year);

    List<Reclamation> findReclamationByClientEmail(String email);


    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Transactional
    @Query("UPDATE Reclamation r set r.etat= :etat WHERE r.id= :id")
    void editReclamById(@Param("etat") String etat,@Param("id") int id);

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Transactional
    @Query("UPDATE Reclamation r set r.dateCloture= :dateCloture WHERE r.id= :id")
    void editReclamDateById(@Param("dateCloture") LocalDate now,@Param("id") int id);

    List<Reclamation> findReclamationByMonth(String month);
}
