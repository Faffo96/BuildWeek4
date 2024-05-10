package Dao;

import Entities.Route;
import Entities.Trip;
import Entities.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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

        if (trip!= null){
            em.remove(trip);
        } else {
            System.out.println("Viaggio non trovato");
        }
        et.commit();
    }

    public void createTrip(Vehicle vehicle, Route route, Duration duration){
        Trip trip = new Trip(vehicle, route, duration);
        em.persist(trip);
    }

    public int countTripsByVehicle(int vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
        String query = "SELECT COUNT(t) FROM Trip t WHERE t.vehicle.vehicleId = :vehicleId " +
                "AND t.startTime BETWEEN :startDate AND :endDate";

        Query countQuery = em.createQuery(query);
        countQuery.setParameter("vehicleId", vehicleId);
        countQuery.setParameter("startDate", startDate);
        countQuery.setParameter("endDate", endDate);
        int tripCount = (int) countQuery.getSingleResult();

        return tripCount;
    }


    public double averageTripDuration(int vehicleId) {
        int totalDurationInMinutes = 0;
        int tripCount = 0;

        String query = "SELECT t FROM Trip t WHERE t.vehicle.vehicleId = :vehicleId";
        List<Trip> trips = em.createQuery(query, Trip.class)
                .setParameter("vehicleId", vehicleId)
                .getResultList();

        for (Trip trip : trips) {
            totalDurationInMinutes += trip.getDuration().toMinutes(); // Converte Duration in minuti
            tripCount++;
        }

        if (tripCount > 0) {
            double averageDurationInMinutes = (double) totalDurationInMinutes / tripCount;
            return averageDurationInMinutes;
        } else {
            return 0;
        }
    }



}