package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import productmanager.productmanager.model.Customer;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

    List<Customer> findCustomerByNameAndEmail(String name, String email);

    Customer findCustomerByEmail(String email);

    List<Customer> findCustomerByName(String name);
}
