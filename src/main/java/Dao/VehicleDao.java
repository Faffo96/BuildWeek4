package Dao;

import Entities.Route;
import Entities.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class VehicleDao {
    private static EntityManager em;

    public VehicleDao(EntityManager em) {
        this.em = em;
    }

    public void save(Vehicle vehicle){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(vehicle);
        et.commit();
    }

    public static Vehicle getById(int id){
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

    public static List<Vehicle> getAvailableVehicles() {
        String jpql = "SELECT v FROM Vehicle v WHERE v.route IS NULL";
        TypedQuery<Vehicle> query = em.createQuery(jpql, Vehicle.class);
        return query.getResultList();
    }

    public static Vehicle checkVehicleAvailabilityByRoute(Route route) {
        String jpql = "SELECT v FROM Vehicle v WHERE v.route = :route";
        TypedQuery<Vehicle> query = em.createQuery(jpql, Vehicle.class);
        query.setParameter("route", route);
        query.setMaxResults(1); // Limita la query a restituire solo un risultato
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Vehicle> getAllVehicles() {
        String jpql = "SELECT v FROM Vehicle";
        TypedQuery<Vehicle> query = em.createQuery(jpql, Vehicle.class);
        return query.getResultList();
    }


}
