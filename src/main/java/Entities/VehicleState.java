package Entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle_states")
public class VehicleState {
    @Id
    @GeneratedValue
    private Long vehicleStateId;

    @Column(name = "under_maintenance")
    private boolean underMaintenance;

    @Column(name = "start_state")
    private LocalDate startState;
    @Column(name = "end_state")
    private LocalDate endState;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public VehicleState(boolean underMaintenance, Vehicle vehicle) {
        this.underMaintenance = underMaintenance;
        this.startState = LocalDate.now();
        this.vehicle = vehicle;
    }

    public VehicleState(){

    }

    public Long getVehicleStateId() {
        return vehicleStateId;
    }

    public void setVehicleStateId(Long vehicleStateId) {
        this.vehicleStateId = vehicleStateId;
    }

    public boolean isUnderMaintenance() {
        return underMaintenance;
    }

    public void setUnderMaintenance(boolean underMaintenance) {
        this.underMaintenance = underMaintenance;
    }

    public LocalDate getStartState() {
        return startState;
    }

    public void setStartState(LocalDate startState) {
        this.startState = startState;
    }

    public LocalDate getEndState() {
        return endState;
    }

    public void setEndState(LocalDate endState) {
        this.endState = endState;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "VehicleState{" +
                "vehicleStateId=" + vehicleStateId +
                ", underMaintenance=" + underMaintenance +
                ", startState=" + startState +
                ", endState=" + endState +
                ", vehicle=" + vehicle +
                '}';
    }
}
