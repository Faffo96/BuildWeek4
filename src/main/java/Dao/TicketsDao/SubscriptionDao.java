package Dao.TicketsDao;

import Entities.Tickets.Subscription;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SubscriptionDao {
    private EntityManager em;

    public SubscriptionDao(EntityManager em) {
        this.em = em;
    }

    public void save(Subscription subscription){
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
}
