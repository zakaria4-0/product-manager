package productmanager.productmanager.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Reservation {

    @Id
    @SequenceGenerator(name = "reservation_sequence", sequenceName = "reservation_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_sequence")
    private int id;
    private String name;
    private String email;
    private String ville;
    private String address;
    private String region;
    private float total;

    @OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cp_fk",referencedColumnName = "id")
    private List<Product> products;

    private LocalDate date;

    private LocalTime time;

    public Reservation(String name, String email, String ville, String address, float total, LocalDate date, String region, List<Product> products, LocalTime time) {
        this.name = name;
        this.email = email;
        this.ville = ville;
        this.address = address;
        this.total = total;
        this.date = date;
        this.region = region;
        this.products = products;
        this.time = time;
    }

    public Reservation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", ville='" + ville + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", Region='" + region + '\'' +
                '}';
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
