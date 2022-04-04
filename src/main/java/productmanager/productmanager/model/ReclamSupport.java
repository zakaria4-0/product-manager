package productmanager.productmanager.model;

import javax.persistence.*;

@Entity
public class ReclamSupport {
    @Id
    @SequenceGenerator(name = "reclamSupport_sequence", sequenceName = "reclamSupport_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reclamSupport_sequence")
    private int id;
    private String cName;
    private String name;
    private int codeArticle;
    private int qte;
    private String motif;

    public ReclamSupport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(int codeArticle) {
        this.codeArticle = codeArticle;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    @Override
    public String toString() {
        return "ReclamSupport{" +
                "id=" + id +
                ", cName='" + cName + '\'' +
                ", name='" + name + '\'' +
                ", codeArticle=" + codeArticle +
                ", qte=" + qte +
                ", motif='" + motif + '\'' +
                '}';
    }
}
