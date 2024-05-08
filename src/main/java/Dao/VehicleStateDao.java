package Dao;

import Entities.VehicleState;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class VehicleStateDao {
    private EntityManager em;

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
}
