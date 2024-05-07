package Entities.Sellers;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "shops")
public class Shop extends Seller {
    private String address;

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
