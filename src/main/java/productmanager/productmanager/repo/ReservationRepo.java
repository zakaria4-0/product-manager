package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    @Query("SELECT r FROM Reservation r WHERE r.date= :date AND r.time<= :now")
    List<Reservation> findReservationByDateAndTime(@Param("date") LocalDate date,@Param("now") LocalTime now);


    Reservation findReservationByNameAndEmailAndId(String clientName, String clientEmail, int codeCommand);

    List<Reservation> findReservationByEmail(String email);
}
