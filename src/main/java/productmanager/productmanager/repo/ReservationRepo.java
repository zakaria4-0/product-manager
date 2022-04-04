package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {

    List<Reservation> findReservationByDate(LocalDate date);


    Reservation findReservationByNameAndEmailAndId(String clientName, String clientEmail, int codeCommand);

    List<Reservation> findReservationByEmail(String email);


}
