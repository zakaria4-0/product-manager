package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import productmanager.productmanager.model.Command;

import java.util.List;

public interface CommandRepo extends JpaRepository<Command,Integer> {




    List<Command> findCommandByCname(String cname);
}
