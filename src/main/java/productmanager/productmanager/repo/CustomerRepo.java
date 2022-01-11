package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.dto.OrderRequest;
import productmanager.productmanager.dto.OrderResponse;
import productmanager.productmanager.model.Customer;
import productmanager.productmanager.model.Product;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {

    Customer findCustomerByNameAndEmail(String name,String email);

    Customer findCustomerByEmail(String email);

    Customer findCustomerByName(String name);
}
