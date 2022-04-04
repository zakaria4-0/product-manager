package productmanager.productmanager.model;

import javax.persistence.*;

@Entity
public class ProductClaimed {
    @Id
    @SequenceGenerator(name = "productC_sequence", sequenceName = "productC_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productC_sequence")
    private int id;
    private String name;
    private int codeArticle;
    private int qte;
    private String motif;

    public ProductClaimed(int id, String name, int codeArticle, int qte, String motif) {
        this.id = id;
        this.name = name;
        this.codeArticle = codeArticle;
        this.qte = qte;
        this.motif = motif;
    }

    public ProductClaimed() {
    }

    public int getPid() {
        return id;
    }

    public void setPid(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ProductClaimed{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qte=" + qte +
                '}';
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
}
