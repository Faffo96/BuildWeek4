package Entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle_states")
public class VehicleState {
    @Id
    @GeneratedValue
    @JoinColumn(name = "vehicle_state_id")
    private int vehicleStateId;

    @JoinColumn(name = "under_maintenance")
    private boolean underMaintenance;

    @JoinColumn(name = "start_state")
    private LocalDate startState;
    @JoinColumn(name = "end_state")
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

    public int getVehicleStateId() {
        return vehicleStateId;
    }

    public void setVehicleStateId(int vehicleStateId) {
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
}
