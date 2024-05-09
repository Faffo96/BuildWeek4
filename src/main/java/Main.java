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
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

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

    public void checkIn(User user, Route route) {

        Vehicle vehicle = checkVehicleAvailabilityByRoute(route);
        User user = user;

        if (vehicle == null) {
            List<Vehicle> availableVehicles = getAvailableVehicles();
            if (availableVehicles.isEmpty()) {
                System.out.println("No vehicles available on this route.");
            }
            vehicle = availableVehicles.get(0);
        }

        if (vehicle.checkMaxCapacity()) {
            System.out.println("We are full, wait for another vehicle.");
            return;
        }


        if (user.checkUserCard) {
            // se true ha la card
            Card card = user.getCard();
            if (card.getSubscription() != null) {
                Subscription subscription = card.getSubscription();
                if (subscription.checkSubscriptionValidity()) {
                    System.out.println("Welcome on board");
                    vehicle.setUsersOnBoard(vehicle.getUsersOnBoard() + 1);
                }
            }
        } else {
            if (user.getTickets().isEmpty()){
                System.out.println("Ticket/Subscription not found.");
                return;
            } else {
                Ticket ticket = user.getTickets().get(0);
                TicketDao.checkTicket(ticket);
                System.out.println("Welcome on board");
                vehicle.setUsersOnBoard(vehicle.getUsersOnBoard() + 1);
            }
        }


    }
}
