package Entities.Services;

import Dao.SellerDao.ShopDao;
import Dao.UserDao;
import Entities.Route;
import Entities.Sellers.Seller;
import Entities.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket extends Service {

    private LocalDate stampDate;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    private boolean validity = true;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bus");
    EntityManager em = emf.createEntityManager();
    UserDao userDao = new UserDao(em);

    public Ticket(Seller seller, User user) {
        super(seller);
        this.user = user;
    }

    public Ticket() {
    }

    public LocalDate getStampDate() {
        return stampDate;
    }

    public void setStampDate(LocalDate stampDate) {
        this.stampDate = stampDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void buyTicket(int quantity) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            tickets.add(new Ticket(seller, user));
        }
        user.setTickets(tickets);
        userDao.save(user);
    }
}