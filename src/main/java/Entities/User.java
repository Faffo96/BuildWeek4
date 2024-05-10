package Entities;

import Entities.Services.Subscription;
import Entities.Services.Ticket;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String lastName;

    @OneToOne (mappedBy = "user")
    private Card card;

    @OneToMany (mappedBy = "user")
    private List<Ticket> tickets;


    public User() {
    }

    public User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        this.tickets = new ArrayList<>();
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", card=" + card +
                ", subscription=" +
                '}';
    }

    public boolean checkUserCard(){
        return card != null;
    }

}
