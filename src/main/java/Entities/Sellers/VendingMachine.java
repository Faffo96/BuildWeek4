package Entities.Sellers;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vending_machines")
public class VendingMachine extends Seller {
    private boolean operative;

    public VendingMachine(boolean operative) {
        super();
        this.operative = operative;
    }

    public VendingMachine(){

    }

    public boolean isState() {
        return operative;
    }

    public void setState(boolean operative) {
        this.operative = operative;
    }
}
