package Dao;

import Entities.Vehicle;
import Entities.VehicleState;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class VehicleStateDao {
    private static EntityManager em;

    public VehicleStateDao(EntityManager em) {
        this.em = em;
    }

    public void save(VehicleState vehicleState){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(vehicleState);
        et.commit();
    }

    public VehicleState getById(int id){
        return em.find(VehicleState.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        VehicleState vehicleState = getById(id);

        if (vehicleState != null){
            em.remove(vehicleState);
        } else {
            System.out.println("Nessun veicolo trovato");
        }
        et.commit();
    }

    public List<VehicleState> getOperationalPeriodsByVehicleId(Long vehicleId) {
        String jpql = "SELECT vs FROM VehicleState vs " +
                "WHERE vs.vehicle.vehicleId = :vehicleId " +
                "AND vs.underMaintenance = false";

        TypedQuery<VehicleState> query = em.createQuery(jpql, VehicleState.class);
        query.setParameter("vehicleId", vehicleId);

        return query.getResultList();
    }

    public List<VehicleState> getMaintenancePeriodsByVehicleId(Long vehicleId) {
        String jpql = "SELECT vs FROM VehicleState vs " +
                "WHERE vs.vehicle.vehicleId = :vehicleId " +
                "AND vs.underMaintenance = true";

        TypedQuery<VehicleState> query = em.createQuery(jpql, VehicleState.class);
        query.setParameter("vehicleId", vehicleId);

        return query.getResultList();
    }

    public static void updateVehicleMaintenanceStatus(int vehicleId, boolean underMaintenance) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            VehicleState vehicleState = em.find(VehicleState.class, vehicleId);
            if (vehicleState != null) {
                vehicleState.setUnderMaintenance(underMaintenance);
                em.merge(vehicleState);
                et.commit();
            } else {
                System.out.println("Id del veicolo non trovato: " + vehicleId);
            }
        } catch (Exception e) {
            if (et != null) {
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
