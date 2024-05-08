package Dao.ServicesDao;

import Entities.Services.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ServiceDao {
    private static EntityManager em;

    public ServiceDao(EntityManager em) {
        this.em = em;
    }

    public void save(Service service){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(service);
        et.commit();
    }

    public Service getById(int id){
        return em.find(Service.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Service service = getById(id);

        if (service != null){
            em.remove(service);
        } else {
            System.out.println("Servizio non disponibile");
        }
        et.commit();
    }

    public static void purchaseService (Long sellerId, Long cardId, Long userId) {
        if (cardId != null) {
            SubscriptionDao.purchaseSubscription(sellerId, cardId);
        } else {
            TicketDao.purchaseTicket(sellerId, userId);
        }
    }


}
