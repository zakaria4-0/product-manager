package productmanager.productmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import productmanager.productmanager.dto.OrderResponse;
import productmanager.productmanager.model.Reservation;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    @Query("SELECT new productmanager.productmanager.dto.OrderResponse(r.id,r.name,r.email,r.date,r.address,r.region,r.ville , p.name , p.price, p.qte) FROM Reservation r JOIN r.products p")
    public List<OrderResponse> getInformation();
}
