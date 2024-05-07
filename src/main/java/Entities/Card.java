package Entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue
    private int cardId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate subscriptionDate ;
    private LocalDate expireDate ;

    public Card(User user) {
        this.user = user;
        this.subscriptionDate = LocalDate.now();
        this.expireDate = subscriptionDate.plusDays(365);
    }

    public Card() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "Card{" +
                "user=" + user +
                ", subscriptionDate=" + subscriptionDate +
                ", expireDate=" + expireDate +
                '}';
    }
}