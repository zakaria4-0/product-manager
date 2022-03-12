package productmanager.productmanager.dto;


import productmanager.productmanager.model.Reservation;

public class OrderRequest {
   private Reservation reservation;

    public OrderRequest(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "reservation=" + reservation +
                '}';
    }
}
