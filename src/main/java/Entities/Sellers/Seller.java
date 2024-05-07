package Entities.Sellers;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Seller {
    @Id
    @GeneratedValue
    @JoinColumn(name = "seller_id")
    protected int sellerId;

    public Seller(int sellerId) {
        this.sellerId = sellerId;
    }

    public Seller(){

    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
