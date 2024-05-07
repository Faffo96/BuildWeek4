package Entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue
    @JoinColumn(name = "trip_id")
    private int tripId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @JoinColumn(name = "start_trip")
    private LocalDate startTrip;

    @JoinColumn(name = "end_trip")
    private LocalDate endTrip;

    private LocalDate duration;

    private Route route;

    public Trip(int tripId, Vehicle vehicle, LocalDate startTrip, LocalDate endTrip, LocalDate duration, Route route) {
        this.tripId = tripId;
        this.vehicle = vehicle;
        this.startTrip = startTrip;
        this.endTrip = endTrip;
        this.duration = duration;
        this.route = route;
    }

    public Trip(){

    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getStartTrip() {
        return startTrip;
    }

    public void setStartTrip(LocalDate startTrip) {
        this.startTrip = startTrip;
    }

    public LocalDate getEndTrip() {
        return endTrip;
    }

    public void setEndTrip(LocalDate endTrip) {
        this.endTrip = endTrip;
    }

    public LocalDate getDuration() {
        return duration;
    }

    public void setDuration(LocalDate duration) {
        this.duration = duration;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
