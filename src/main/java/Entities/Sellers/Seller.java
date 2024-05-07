package Entities.Sellers;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "sellers")
public abstract class Seller {
    @Id
    @GeneratedValue
    @JoinColumn(name = "seller_id")
    protected int sellerId;


    public Seller(){

    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
