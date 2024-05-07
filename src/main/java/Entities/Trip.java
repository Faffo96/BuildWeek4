package Entities;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue
    private Long tripId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "start_trip")
    private LocalDateTime startTrip;

    @Column(name = "end_trip")
    private LocalDateTime endTrip;

    private Duration duration;

    @OneToOne
    @JoinColumn (name = "route_id")
    private Route route;

    public Trip(Vehicle vehicle, Route route) {
        this.vehicle = vehicle;
        this.startTrip = LocalDateTime.now();
        this.route = route;
    }

    public Trip(){

    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getStartTrip() {
        return startTrip;
    }

    public void setStartTrip(LocalDateTime startTrip) {
        this.startTrip = startTrip;
    }

    public LocalDateTime getEndTrip() {
        return endTrip;
    }

    public void setEndTrip(LocalDateTime endTrip) {
        this.endTrip = endTrip;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
