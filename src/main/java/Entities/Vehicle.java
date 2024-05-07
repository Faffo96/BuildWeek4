package Entities;

import Entities.Tickets.Ticket;
import enums.VehicleType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue
    @JoinColumn(name = "vehicle_id")
    private int vehicleId;
    private int capacity;

    @Enumerated
    @JoinColumn(name = "vehicle_type")
    private VehicleType vehicleType;

    @OneToMany
    private List<Ticket> ticket;

    @ManyToOne
    private Route route;

    @OneToMany
    private List<Trip> trip;

    @OneToMany
    private List<VehicleState> vehicleState;

    public Vehicle(int vehicleId, int capacity, VehicleType vehicleType, List<Ticket> ticket, Route route, List<Trip> trip, List<VehicleState> vehicleState) {
        this.vehicleId = vehicleId;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.ticket = ticket;
        this.route = route;
        this.trip = trip;
        this.vehicleState = vehicleState;
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

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

    public List<VehicleState> getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(List<VehicleState> vehicleState) {
        this.vehicleState = vehicleState;
    }
}
