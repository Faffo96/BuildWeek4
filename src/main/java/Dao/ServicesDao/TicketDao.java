package Dao.ServicesDao;

import Entities.Sellers.Seller;
import Entities.Services.Ticket;
import Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class TicketDao {
    private static EntityManager em;

    public TicketDao(EntityManager em) {
        this.em = em;
    }

    public void save(Ticket ticket){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(ticket);
        et.commit();
    }

    public void update(Ticket element) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(element);
        et.commit();
    }

    public Ticket getById(int id){
        return em.find(Ticket.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Ticket ticket = getById(id);

        if (ticket != null){
            em.remove(ticket);
        } else {
            System.out.println("Biglietto non trovato");
        }
        et.commit();
    }

    public static void purchaseTicket(Long sellerId, Long userId) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Ticket ticket = new Ticket();
            ticket.setSeller(em.find(Seller.class, sellerId));
            ticket.setUser(em.find(User.class, userId));

            em.persist(ticket);
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


    public static int checkStampedTickets(LocalDate startDate, LocalDate endDate, Integer vehicleId) {
        if (vehicleId != null && vehicleId != 0) {
            Query query = em.createQuery("SELECT COUNT(t) FROM Ticket t JOIN t.vehicle v WHERE v.vehicleId = :vehicleId AND t.stampDate BETWEEN :startDate AND :endDate");
            query.setParameter("vehicleId", vehicleId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return ((Number) query.getSingleResult()).intValue();
        } else {

            Query query = em.createQuery("SELECT COUNT(t) FROM Ticket t WHERE t.stampDate BETWEEN :startDate AND :endDate");
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return ((Number) query.getSingleResult()).intValue();
        }
    }


    public void checkTicket(Ticket ticket) {
        if (ticket.getUser() != null) {
            ticket.setStampDate(LocalDate.now());
            ticket.setValidity(false);
            update(ticket);
        }
    }


}
