package Entities.Tickets;

import Entities.Sellers.Seller;
import Entities.User;
import enums.SubscriptionDuration;

import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.time.LocalDate;

public class Subscription extends Service{

    @Enumerated
    private SubscriptionDuration subscriptionDuration;

    @OneToOne
    private User user;

    public Subscription(LocalDate expirationDate, boolean validity, Seller seller, SubscriptionDuration subscriptionDuration, User user) {
        super(expirationDate, validity, seller);
        this.subscriptionDuration = subscriptionDuration;
        this.user = user;
    }

    public Subscription(User user) {
        this.user = user;
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
