package Dao;

import Entities.Services.Ticket;
import Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDao {
    private static EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public void save(User user){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(user);
        et.commit();
    }

    public static User getById(int id){
        return em.find(User.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        User user = getById(id);

        if (user != null){
            em.remove(user);
        } else {
            System.out.println("User non trovato");
        }
        et.commit();
    }

    public void createUser(String name,String surname){
        EntityTransaction et = em.getTransaction();
        User user = new User(name,surname);
        save(user);
    }

    public void hopOnTheBus() {

    }

    public static List<Ticket> getTicketsByUser(User user) {
        TypedQuery<Ticket> query = em.createQuery("SELECT t FROM Ticket t WHERE t.user = :user", Ticket.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
