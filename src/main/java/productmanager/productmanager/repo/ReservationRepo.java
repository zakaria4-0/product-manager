package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import productmanager.productmanager.dto.OrderResponse;
import productmanager.productmanager.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    @Query("SELECT new productmanager.productmanager.dto.OrderResponse(r.id,r.name,r.email,r.date,r.time,r.address,r.region,r.ville,r.total , p.name , p.price, p.qte) FROM Reservation r JOIN r.products p")
    List<OrderResponse> getInformation();

    @Query("SELECT r FROM Reservation r WHERE r.date= :date AND r.time<= :now")
    List<Reservation> findReservationByDateAndTime(@Param("date") LocalDate date,@Param("now") LocalTime now);


    Reservation findReservationByNameAndEmailAndId(String clientName, String clientEmail, int codeCommand);
}
