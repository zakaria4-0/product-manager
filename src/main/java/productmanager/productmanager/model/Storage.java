package productmanager.productmanager.model;

import javax.persistence.*;

@Entity
public class Storage {
    @Id
    @SequenceGenerator(name = "storage_sequence", sequenceName = "storage_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_sequence")
    private int id;
    private String productName;
    private int productQuantity;
    private int productQuantityI;
    private float productPrice;
    private float promotionPrice;
    private String productImage;
    private String description;
    private String  category;
    private String state;

    public Storage() {
    }

    public Storage(int id, String productName, int productQuantity, int productQuantityI, float productPrice, float promotionPrice, String productImage, String description, String category, String state) {
        this.id = id;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productQuantityI = productQuantityI;
        this.productPrice = productPrice;
        this.promotionPrice = promotionPrice;
        this.productImage = productImage;
        this.description = description;
        this.category = category;
        this.state = state;
    }

    public float getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(float promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public int getProductQuantityI() {
        return productQuantityI;
    }

    public void setProductQuantityI(int productQuantityI) {
        this.productQuantityI = productQuantityI;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productQuantity=" + productQuantity +
                ", productPrice=" + productPrice +
                '}';
    }
}
