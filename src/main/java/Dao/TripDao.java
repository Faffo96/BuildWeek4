package Dao;

import Entities.Route;
import Entities.Trip;
import Entities.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TripDao {
    private EntityManager em;

    public TripDao(EntityManager em) {
        this.em = em;
    }

    public void save(Trip trip){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(trip);
        et.commit();
    }

    public Trip getById(int id){
        return em.find(Trip.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Trip trip = getById(id);

        if (trip != null){
            em.remove(trip);
        } else {
            System.out.println("Viaggio non trovato");
        }
        et.commit();
    }

    public void createTrip(Vehicle vehicle, Route route){
        Trip trip = new Trip(vehicle,route);
        save(trip);
    }
}
