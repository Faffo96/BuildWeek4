package Dao.ServicesDao;

import Entities.Card;
import Entities.Sellers.Seller;
import Entities.Services.Subscription;
import Entities.Services.Ticket;
import Entities.User;
import enums.SubscriptionDuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SubscriptionDao {
    private static EntityManager em;

    public SubscriptionDao(EntityManager em) {
        this.em = em;
    }

    public static void save(Subscription subscription){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(subscription);
        et.commit();
    }

    public Subscription getById(int id){
        return em.find(Subscription.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Subscription subscription = getById(id);

        if (subscription != null){
            em.remove(subscription);
        } else {
            System.out.println("Abbonamento non disponibile");
        }
        et.commit();
    }

    public static void purchaseSubscription(Long sellerId, Long cardId) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Subscription subscription = new Subscription();
            subscription.setSeller(em.find(Seller.class, sellerId));
            subscription.setCard(em.find(Card.class, cardId));


            em.persist(subscription);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
