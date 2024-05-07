package Entities.Tickets;

import Entities.Sellers.Seller;
import Entities.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tickets")
public class Ticket extends Service {

    private LocalDate stampDate;

    @ManyToOne
    private User user;

    public Ticket() {
    }

    public Ticket(LocalDate expirationDate, boolean validity, Seller seller, LocalDate stampDate, User user) {
        super(expirationDate, validity, seller);
        this.stampDate = stampDate;
        this.user = user;
    }

    public LocalDate getStampDate() {
        return stampDate;
    }

    public void setStampDate(LocalDate stampDate) {
        this.stampDate = stampDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
