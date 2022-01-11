package productmanager.productmanager.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import productmanager.productmanager.model.Admin;


public interface AdminRepo extends JpaRepository<Admin,Integer> {

    Admin findAdminByNameAndPassword(String adminName, String adminPassword);
}
