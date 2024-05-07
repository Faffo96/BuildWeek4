package Dao;

import Entities.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class VehiclesDao {
    private EntityManager em;

    public VehiclesDao(EntityManager em) {
        this.em = em;
    }

    public void save(Vehicle vehicle){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(vehicle);
        et.commit();
    }

    public Vehicle getById(int id){
        return em.find(Vehicle.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Vehicle vehicle = getById(id);

        if (vehicle != null){
            em.remove(vehicle);
        } else {
            System.out.println("Veicolo non disponibile");
        }
        et.commit();
    }
}
