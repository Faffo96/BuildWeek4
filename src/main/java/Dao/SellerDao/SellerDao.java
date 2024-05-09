package Dao.SellerDao;

import Entities.Sellers.Seller;
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

    public void update(Seller element) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(element);
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

        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.stampDate BETWEEN :startDate AND :endDate", Ticket.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<Ticket> tickets = query.getResultList();

        for (Ticket ticket : tickets) {
            Seller seller = ticket.getSeller();
            soldServicesMap.put(seller, soldServicesMap.getOrDefault(seller, 0) + 1);
        }

        return soldServicesMap;
    }
}
