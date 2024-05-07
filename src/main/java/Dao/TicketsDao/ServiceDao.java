package Dao.TicketsDao;

import Entities.Tickets.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ServiceDao {
    private EntityManager em;

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
}
