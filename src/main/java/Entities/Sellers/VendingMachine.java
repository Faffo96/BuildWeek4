package Entities.Sellers;

public class VendingMachine extends Seller {
    private boolean state;

    public VendingMachine(int sellerId, boolean state) {
        super(sellerId);
        this.state = state;
    }

    public VendingMachine(boolean state) {
        this.state = state;
    }

    public VendingMachine(){

    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
