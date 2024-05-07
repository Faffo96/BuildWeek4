import Dao.*;
import Dao.SellerDao.ShopDao;
import Dao.SellerDao.VendingMachineDao;
import Dao.TicketsDao.SubscriptionDao;
import Dao.TicketsDao.TicketDao;
import Entities.*;
import Entities.Sellers.Shop;
import Entities.Sellers.VendingMachine;
import Entities.Tickets.Subscription;
import Entities.Tickets.Ticket;
import enums.SubscriptionDuration;
import enums.VehicleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bus");
        EntityManager em = emf.createEntityManager();
        ShopDao shopDao = new ShopDao(em);
        VendingMachineDao vendingMachineDao = new VendingMachineDao(em);
        UserDao userDao = new UserDao(em);
        SubscriptionDao subscriptionDao = new SubscriptionDao(em);
        TicketDao ticketDao = new TicketDao(em);
        CardDao cardsDao = new CardDao(em);
        RoutesDao routesDao = new RoutesDao(em);
        VehiclesDao vehiclesDao = new VehiclesDao(em);
        TripDao tripDao = new TripDao(em);
        VehicleStateDao vehicleStateDao = new VehicleStateDao(em);



        Shop shop1 = new Shop("Via Roma");
        VendingMachine vending1 = new VendingMachine(true);
        User user1 = new User("Mario", "Rossi");
        Subscription subscription1 = new Subscription(vending1, SubscriptionDuration.WEEKLY, user1);
        Ticket ticket1 = new Ticket(shop1, user1);
        Card card1 = new Card(user1);
        Route route1 = new Route("Via Roma", "Via via da questa via", 15);
        Vehicle vehicle1 = new Vehicle(50, VehicleType.BUS, route1);
        Vehicle vehicle2 = new Vehicle(50, VehicleType.TRAM, route1);
        Trip trip1 = new Trip(vehicle1, route1);
        VehicleState vehicleState1 = new VehicleState(true, vehicle2);

        shopDao.save(shop1);
        vendingMachineDao.save(vending1);
        userDao.save(user1);
        subscriptionDao.save(subscription1);
        ticketDao.save(ticket1);
        cardsDao.save(card1);
        routesDao.save(route1);
        vehiclesDao.save(vehicle1);
        vehiclesDao.save(vehicle2);
        tripDao.save(trip1);
        vehicleStateDao.save(vehicleState1);


    }
}
