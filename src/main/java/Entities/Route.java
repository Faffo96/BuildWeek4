package Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue
    @JoinColumn(name = "route_id")
    private int routeId;

    @JoinColumn(name = "start_location")
    private String startLocation;
    @JoinColumn(name = "end_location")
    private String endLocation;

    @OneToMany
    private List<Vehicle> vehicles;

    private LocalDate duration;

    public Route(String startLocation, String endLocation, LocalDate duration) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.duration = duration;
    }

    public Route(){

    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public LocalDate getDuration() {
        return duration;
    }

    public void setDuration(LocalDate duration) {
        this.duration = duration;
    }
}
