package Entities.Tickets;

import Entities.Sellers.Seller;
import Entities.User;
import enums.SubscriptionDuration;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
public class Subscription extends Service{

    @Enumerated
    private SubscriptionDuration subscriptionDuration;

    @OneToOne
    private User user;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    public Subscription(Seller seller, SubscriptionDuration subscriptionDuration, User user) {
        super(seller);
        if (subscriptionDuration == subscriptionDuration.WEEKLY) {
            this.expirationDate = purchaseDate.plusDays(7);
        } else if (subscriptionDuration == subscriptionDuration.MONTHLY) {
            this.expirationDate = purchaseDate.plusDays(30);
        }
        this.subscriptionDuration = subscriptionDuration;
        this.user = user;
    }

    public Subscription() {

    }

    public SubscriptionDuration getSubscriptionDuration() {
        return subscriptionDuration;
    }

    public void setSubscriptionDuration(SubscriptionDuration subscriptionDuration) {
        this.subscriptionDuration = subscriptionDuration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
