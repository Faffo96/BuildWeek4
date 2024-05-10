package Dao;

import Entities.Vehicle;
import Entities.VehicleState;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class VehicleStateDao {
    private static EntityManager em;

    public VehicleStateDao(EntityManager em) {
        this.em = em;
    }

    public void save(VehicleState vehicleState) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(vehicleState);
        et.commit();
    }

    public VehicleState getById(int id) {
        return em.find(VehicleState.class, id);
    }

    public void delete(int id) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        VehicleState vehicleState = getById(id);

        if (vehicleState != null) {
            em.remove(vehicleState);
        } else {
            System.out.println("Nessun veicolo trovato");
        }
        et.commit();
    }


    public List<VehicleState> getOperationalPeriodsByVehicleId(int vehicleId) {
        String jpql = "SELECT vs FROM VehicleState vs " +
                "WHERE vs.vehicle.vehicleId = :vehicleId " +
                "AND vs.underMaintenance = false";

        TypedQuery<VehicleState> query = em.createQuery(jpql, VehicleState.class);
        query.setParameter("vehicleId", vehicleId);

        return query.getResultList();
    }

    public List<VehicleState> getMaintenancePeriodsByVehicleId(int vehicleId) {
        String jpql = "SELECT vs FROM VehicleState vs " +
                "WHERE vs.vehicle.vehicleId = :vehicleId " +
                "AND vs.underMaintenance = true";

        TypedQuery<VehicleState> query = em.createQuery(jpql, VehicleState.class);
        query.setParameter("vehicleId", vehicleId);

        return query.getResultList();
    }

    public void updateVehicleMaintenanceStatus(int vehicleId, boolean underMaintenance) {
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();

            // Query per selezionare l'ultimo stato del veicolo
            TypedQuery<VehicleState> query = em.createQuery("SELECT vs FROM VehicleState vs WHERE vs.vehicle = :vehicleId", VehicleState.class);
            query.setParameter("vehicleId", vehicleId);
            query.setMaxResults(1); // Limita la query a un solo risultato (il più recente)
            List<VehicleState> results = query.getResultList();
            Vehicle vehicle = VehicleDao.getById(vehicleId);
            if (!results.isEmpty()) {
                VehicleState vehicleState = results.get(0);
                vehicleState.setEndState(LocalDate.now());
                em.merge(vehicleState);
                save(new VehicleState(underMaintenance, vehicle));
                et.commit();
            } else {
                System.out.println("Id del veicolo non trovato o nessuno stato trovato per il veicolo: " + vehicleId);
            }
        } catch (Exception e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            e.printStackTrace(); // Stampa l'eccezione per diagnosticare il problema
        }
    }


    public void updateEndDate(int vehicleId, LocalDate endDate) {
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();

            // Query per selezionare l'ultimo stato del veicolo
            TypedQuery<VehicleState> query = em.createQuery("SELECT vs FROM VehicleState vs WHERE vs.vehicleId = :vehicleId ORDER BY vs.insertionDate DESC", VehicleState.class);
            query.setParameter("vehicleId", vehicleId);
            query.setMaxResults(1); // Limita la query a un solo risultato (il più recente)
            List<VehicleState> results = query.getResultList();

            if (!results.isEmpty()) {
                VehicleState vehicleState = results.get(0);
                vehicleState.setEndState(endDate);
                em.merge(vehicleState);
                et.commit();
            } else {
                System.out.println("Id del veicolo non trovato o nessuno stato trovato per il veicolo: " + vehicleId);
            }
        } catch (Exception e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
        }
    }


    public static boolean getVehicleState(Vehicle vehicle) {
        String jpql = "SELECT vs.underMaintenance FROM VehicleState vs " +
                "WHERE vs.vehicle = :vehicle " +
                "ORDER BY vs.startState DESC";

        TypedQuery<Boolean> query = em.createQuery(jpql, Boolean.class);
        query.setParameter("vehicle", vehicle);
        query.setMaxResults(1);

        List<Boolean> results = query.getResultList();
        return !results.isEmpty() ? results.get(0) : false;
    }

}
