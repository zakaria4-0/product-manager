package productmanager.productmanager.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reclamation {
    @Id
    @SequenceGenerator(name = "reclamation_sequence", sequenceName = "reclamation_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reclamation_sequence")
    private int id;
    private String clientName;
    private String clientEmail;
    private int codeCommand;
    private String productName;
    private int codeArticle;
    private String motif;
    private LocalDate date;
    private LocalTime time;


    public Reclamation(int id, String clientName, String clientEmail, int codeCommand, String productName, int codeArticle, String motif, LocalDate date, LocalTime time) {
        this.id = id;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.codeCommand = codeCommand;
        this.productName = productName;
        this.codeArticle = codeArticle;
        this.motif = motif;
        this.date = date;
        this.time = time;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(int codeArticle) {
        this.codeArticle = codeArticle;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", codeArticle=" + codeArticle +
                ", motif='" + motif + '\'' +
                '}';
    }
}
