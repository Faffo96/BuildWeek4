import Dao.*;
import Dao.SellerDao.ShopDao;
import Dao.SellerDao.VendingMachineDao;
import Dao.ServicesDao.SubscriptionDao;
import Dao.ServicesDao.TicketDao;
import Entities.*;
import Entities.Sellers.Shop;
import Entities.Sellers.VendingMachine;
import Entities.Services.Subscription;
import Entities.Services.Ticket;
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
        RouteDao routeDao = new RouteDao(em);
        VehicleDao vehicleDao = new VehicleDao(em);
        TripDao tripDao = new TripDao(em);
        VehicleStateDao vehicleStateDao = new VehicleStateDao(em);

        /*Shop shop1 = new Shop("Via Roma");
        shopDao.save(shop1);

        VendingMachine vending1 = new VendingMachine(true);
        vendingMachineDao.save(vending1);

        User user1 = new User("Mario", "Rossi");
        userDao.save(user1);

        Card card1 = new Card(user1);
        cardsDao.save(card1);

        Subscription subscription1 = new Subscription(vending1, SubscriptionDuration.WEEKLY, card1);
        subscriptionDao.save(subscription1);
        card1.setSubscription(subscription1);
        cardsDao.save(card1);

        Ticket ticket1 = new Ticket(shop1, user1);
        ticketDao.save(ticket1);

        Route route1 = new Route("Via Roma", "Via via da questa via", 15);
        routeDao.save(route1);

        Vehicle vehicle1 = new Vehicle(50, VehicleType.BUS, route1);
        Vehicle vehicle2 = new Vehicle(50, VehicleType.TRAM, route1);
        vehicleDao.save(vehicle1);
        vehicleDao.save(vehicle2);

        Trip trip1 = new Trip(vehicle1, route1);
        tripDao.save(trip1);

        VehicleState vehicleState1 = new VehicleState(true, vehicle2);
        vehicleStateDao.save(vehicleState1);*/


    }
}
