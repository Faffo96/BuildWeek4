package Entities.Services;

import Entities.Card;
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

    @OneToOne (mappedBy = "subscription")
    private Card card;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    public Subscription(Seller seller, SubscriptionDuration subscriptionDuration, Card card) {
        super(seller);
        if (subscriptionDuration == subscriptionDuration.WEEKLY) {
            this.expirationDate = purchaseDate.plusDays(7);
        } else if (subscriptionDuration == subscriptionDuration.MONTHLY) {
            this.expirationDate = purchaseDate.plusDays(30);
        }
        this.subscriptionDuration = subscriptionDuration;
        this.card = card;
    }

    public Subscription() {

    }

    public SubscriptionDuration getSubscriptionDuration() {
        return subscriptionDuration;
    }

    public void setSubscriptionDuration(SubscriptionDuration subscriptionDuration) {
        this.subscriptionDuration = subscriptionDuration;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


}
