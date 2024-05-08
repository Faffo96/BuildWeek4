package Dao;

import Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDao {
    private EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public void save(User user){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(user);
        et.commit();
    }

    public User getById(int id){
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
}
