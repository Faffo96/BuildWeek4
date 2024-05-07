package Dao.ServicesDao;

import Entities.Services.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TicketDao {
    private EntityManager em;

    public TicketDao(EntityManager em) {
        this.em = em;
    }

    public void save(Ticket ticket){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(ticket);
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
}
