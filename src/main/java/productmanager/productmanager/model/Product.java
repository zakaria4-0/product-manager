package productmanager.productmanager.model;

import javax.persistence.*;

@Entity
public class Product {

   @Id
   @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence",allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
   private int pid;
   private  String name;
   private int qte;
   private float price;


   public Product(int pid, String name, int qte, float price) {
      this.pid = pid;
      this.name = name;
      this.qte = qte;
      this.price = price;

   }

   public Product() {
   }

   public int getPid() {
      return pid;
   }

   public void setPid(int pid) {
      this.pid = pid;
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
      return "Product{" +
              "pid=" + pid +
              ", name='" + name + '\'' +
              ", qte=" + qte +
              ", price=" + price +
              '}';
   }


}
