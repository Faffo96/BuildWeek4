package Entities;

import Entities.Services.Ticket;
import enums.VehicleType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue
    private int vehicleId;

    private int capacity;

    @Enumerated (EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @OneToMany
    private List<Ticket> ticket;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToMany(mappedBy = "vehicle")
    private List<Trip> trips;

    @OneToMany
    private List<VehicleState> vehicleState;

    public Vehicle(int capacity, VehicleType vehicleType, Route route) {
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.route = route;
    }

    public Vehicle(){

    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<VehicleState> getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(List<VehicleState> vehicleState) {
        this.vehicleState = vehicleState;
    }
}
