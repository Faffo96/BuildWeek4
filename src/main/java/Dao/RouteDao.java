package Dao;

import Entities.Route;
import Entities.Services.Ticket;
import Entities.Vehicle;
import Entities.VehicleState;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class RouteDao {
    private EntityManager em;

    public RouteDao(EntityManager em) {
        this.em = em;
    }

    public void save(Route route){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(route);
        et.commit();
    }

    public void update(Route element) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.merge(element);
        et.commit();
    }

    public Route getById(int id){
        return em.find(Route.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Route route = getById(id);

        if (route != null){
            em.remove(route);
        } else {
            System.out.println("Rotta non trovata");
        }
        et.commit();
    }
}
