package productmanager.productmanager.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Reclamation {
    @Id
    @SequenceGenerator(name = "reclamation_sequence", sequenceName = "reclamation_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reclamation_sequence")
    private int id;
    private String clientName;
    private String clientEmail;
    private int codeCommand;
    private LocalDate date;
    private LocalTime time;
    private String month;
    private String year;
    private String etat;
    private LocalDate dateCloture;
    @OneToMany(targetEntity = ProductClaimed.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "re_fk")
    private List<ProductClaimed> productClaimeds;
    private String categorie;


    public Reclamation(int id, String clientName, String clientEmail, int codeCommand, LocalDate date, LocalTime time, String month, String year, String etat, LocalDate dateCloture, List<ProductClaimed> productClaimeds, String categorie) {
        this.id = id;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.codeCommand = codeCommand;
        this.date = date;
        this.time = time;
        this.month = month;
        this.year = year;
        this.etat = etat;
        this.dateCloture = dateCloture;
        this.productClaimeds = productClaimeds;
        this.categorie = categorie;
    }

    public Reclamation() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public int getCodeCommand() {
        return codeCommand;
    }

    public void setCodeCommand(int codeCommand) {
        this.codeCommand = codeCommand;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                '}';
    }

    public List<ProductClaimed> getProductClaimeds() {
        return productClaimeds;
    }

    public void setProductClaimeds(List<ProductClaimed> productClaimeds) {
        this.productClaimeds = productClaimeds;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDate getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDate dateCloture) {
        this.dateCloture = dateCloture;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
