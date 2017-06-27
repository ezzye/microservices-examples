package microservices;

public class Stock {

    private String productCode;
    private int qty;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "productCode='" + productCode + '\'' +
                ", qty=" + qty +
                '}';
    }
}
