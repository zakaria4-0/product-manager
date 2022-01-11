package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import productmanager.productmanager.model.CustomerLogin;

public interface CustomerLoginRepo extends JpaRepository<CustomerLogin,Integer> {

    CustomerLogin findCustomerByNameAndPassword(String custName, String custPassword);

    CustomerLogin findCustomerLoginByNameAndEmail(String name,String email);

    CustomerLogin findCustomerLoginByEmail(String email);

    CustomerLogin findCustomerLoginByName(String name);
}
