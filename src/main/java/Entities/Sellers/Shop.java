package Entities.Sellers;

public class Shop extends Seller {
    private String address;

    public Shop(int sellerId, String address) {
        super(sellerId);
        this.address = address;
    }

    public Shop(String address) {
        this.address = address;
    }

    public Shop(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
