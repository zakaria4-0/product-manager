package productmanager.productmanager.dto;

import java.util.Date;

public class OrderResponse {
    private int id;
    private String name;
    private String email;
    private Date date;
    private String address;
    private String region;
    private String ville;
    private String productName;
    private float productPrice;
    private int productQte;

    public OrderResponse(int id, String name, String email, Date date, String address, String region, String ville, String productName, float productPrice, int productQte) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
        this.address = address;
        this.region = region;
        this.ville = ville;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQte = productQte;
    }

    public OrderResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQte() {
        return productQte;
    }

    public void setProductQte(int productQte) {
        this.productQte = productQte;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "name='" + name + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productQte=" + productQte +
                '}';
    }
}
