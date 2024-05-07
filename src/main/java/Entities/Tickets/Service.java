package Entities.Tickets;

import Entities.Sellers.Seller;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Service {

    @Id
    @GeneratedValue
    protected Long id;

    protected LocalDate purchaseDate;
    protected LocalDate expirationDate;
    protected boolean validity = true;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    protected Seller seller;


    public Service(LocalDate expirationDate, boolean validity, Seller seller) {
        this.purchaseDate = LocalDate.now();
        this.expirationDate = expirationDate;
        this.validity = validity;
        this.seller = seller;
    }

    public Service() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isValidity() {
        return validity;
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }


}
