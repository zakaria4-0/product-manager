package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.model.Reclamation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReclamationRepo extends JpaRepository<Reclamation,Integer> {
    List<Reclamation> findReclamationByDate(LocalDate date);

    List<Reclamation> findReclamationByClientEmail(String email);


}
