package Dao.SellerDao;

import Entities.Sellers.Seller;
import Entities.Services.Service;
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

        TypedQuery<Service> serviceQuery = em.createQuery(
                "SELECT s FROM Service s WHERE s.purchaseDate BETWEEN :startDate AND :endDate", Service.class);
        serviceQuery.setParameter("startDate", startDate);
        serviceQuery.setParameter("endDate", endDate);

        List<Service> services = serviceQuery.getResultList();
        System.out.println("Servizi venduti:");
        for (Service service : services) {
            Seller seller = service.getSeller();
            soldServicesMap.put(seller, soldServicesMap.getOrDefault(seller, 0) + 1);
        }
        // stampa
        for (Map.Entry<Seller, Integer> entry : soldServicesMap.entrySet()) {
            Seller seller = entry.getKey();

            int ticketsSold = 0;
            int subscriptionsSold = 0;

            TypedQuery<Service> ciao = em.createQuery(
                    "SELECT s FROM Service s WHERE s.seller = :seller AND s.purchaseDate BETWEEN :startDate AND :endDate", Service.class);
            ciao.setParameter("seller", seller);
            ciao.setParameter("startDate", startDate);
            ciao.setParameter("endDate", endDate);
            List<Service> servicesSoldBySeller = ciao.getResultList();
            for (Service service : servicesSoldBySeller) {
                if (service instanceof Ticket) {
                    ticketsSold++;
                } else if (service instanceof Subscription) {
                    subscriptionsSold++;
                }
            }

            System.out.println("Venditore: " + seller.getSellerId());
            System.out.println("  Biglietti venduti: " + ticketsSold);
            System.out.println("  Abbonamenti venduti: " + subscriptionsSold);
        }
        return soldServicesMap;
    }
}
