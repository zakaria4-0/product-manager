package productmanager.productmanager.model;

import javax.persistence.*;

@Entity
public class Command {
    @Id
    @SequenceGenerator(name = "command_sequence", sequenceName = "command_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "command_sequence")
    private int id;
    private String cname;

    private String name;
    private int qte;
    private float price;

    public Command() {
    }

    public Command(int id, String cname, String name, int qte, float price) {
        this.id = id;
        this.cname = cname;

        this.name = name;
        this.qte = qte;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Command{" +
                ", name='" + name + '\'' +
                ", qte=" + qte +
                ", price=" + price +
                '}';
    }
}
