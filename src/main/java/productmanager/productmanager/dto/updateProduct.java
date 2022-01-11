package productmanager.productmanager.dto;

public class updateProduct {
    private int productQuantity;
    private float productPrice;

    public updateProduct(int productQuantity, float productPrice) {
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public updateProduct() {
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "updateProduct{" +
                "productQuantity=" + productQuantity +
                ", productPrice=" + productPrice +
                '}';
    }
}
