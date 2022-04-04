package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import productmanager.productmanager.model.ReclamSupport;

import java.util.List;

public interface ReclamSupportRepo extends JpaRepository<ReclamSupport,Integer> {

    void deleteReclamSupportById(int id);

    List<ReclamSupport> findReclamSupportBycName(String cName);
}
