package Dao.SellerDao;

import Entities.Sellers.Seller;
import Entities.Services.Subscription;
import Entities.Services.Ticket;
import enums.VehicleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDao {
    private EntityManager em;

    public SellerDao(EntityManager em) {
        this.em = em;
    }

    public void save(Seller seller){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(seller);
        et.commit();
    }

    public Seller getById(int id){
        return em.find(Seller.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Seller seller = getById(id);

        if (seller != null){
            em.remove(seller);
        } else {
            System.out.println("Rivenditore non trovato");
        }
        et.commit();
    }

    public Map<Seller, Integer> soldServices(LocalDate startDate, LocalDate endDate) {
        Map<Seller, Integer> soldServicesMap = new HashMap<>();

        //ticket venduti nell'intervallo di tempo
        TypedQuery<Ticket> ticketQuery = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.stampDate BETWEEN :startDate AND :endDate", Ticket.class);
        ticketQuery.setParameter("startDate", startDate);
        ticketQuery.setParameter("endDate", endDate);

        //abbonamenti venduti nell'intervallo di tempo
        TypedQuery<Subscription> subscriptionQuery = em.createQuery(
                "SELECT s FROM Subscription s WHERE s.purchaseDate BETWEEN :startDate AND :endDate", Subscription.class);
        subscriptionQuery.setParameter("startDate", startDate);
        subscriptionQuery.setParameter("endDate", endDate);

        List<Ticket> tickets = ticketQuery.getResultList();
        System.out.println("Ticket venduti:");
        for (Ticket ticket : tickets) {
            Seller seller = ticket.getSeller();
            soldServicesMap.put(seller, soldServicesMap.getOrDefault(seller, 0) + 1);
        }

        List<Subscription> subscriptions = subscriptionQuery.getResultList();
        System.out.println("Sottoscrizioni vendute:");
        for (Subscription subscription : subscriptions) {
            Seller seller = subscription.getSeller();
            soldServicesMap.put(seller, soldServicesMap.getOrDefault(seller, 0) + 1);
        }

        // stampa
        System.out.println("Servizi venduti:");
        for (Map.Entry<Seller, Integer> entry : soldServicesMap.entrySet()) {
            System.out.println("Venditore: " + entry.getKey().getSellerId() + ", Servizi venduti: " + entry.getValue());
        }

        return soldServicesMap;
    }
}
