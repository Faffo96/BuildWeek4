import Dao.*;
import Dao.SellerDao.SellerDao;
import Dao.SellerDao.ShopDao;
import Dao.SellerDao.VendingMachineDao;
import Dao.ServicesDao.ServiceDao;
import Dao.ServicesDao.SubscriptionDao;
import Dao.ServicesDao.TicketDao;
import Entities.*;
import Entities.Sellers.Seller;
import Entities.Sellers.Shop;
import Entities.Sellers.VendingMachine;
import Entities.Services.Service;
import Entities.Services.Subscription;
import Entities.Services.Ticket;
import enums.SubscriptionDuration;
import enums.VehicleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.transform.Source;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bus");
    EntityManager em = emf.createEntityManager();

    ShopDao shopDao = new ShopDao(em);
    SellerDao sellerDao = new SellerDao(em);
    VendingMachineDao vendingMachineDao = new VendingMachineDao(em);
    ServiceDao serviceDao = new ServiceDao(em);
    SubscriptionDao subscriptionDao = new SubscriptionDao(em);
    TicketDao ticketDao = new TicketDao(em);
    CardDao cardDao = new CardDao(em);
    RouteDao routeDao = new RouteDao(em);
    TripDao tripDao = new TripDao(em);
    UserDao userDao = new UserDao(em);
    VehicleDao vehicleDao = new VehicleDao(em);
    VehicleStateDao vehicleStateDao = new VehicleStateDao(em);

    static Main main = new Main();

    public static void main(String[] args) {


        boolean exit = false;

        while (!exit) {
            displayMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    main.createSeller();
                    break;
                case 2:
                    main.createUser();
                    break;
                case 3:
                    main.buyCard();
                    break;
                case 4:
                    main.buyService();
                    break;
                case 5:
                    main.checkInUser();
                    break;
                case 6:
                    main.calculateTicketsAndSubscriptions();
                    break;
                case 7:
                    main.createRouteAndAssignVehicle();
                    break;
                case 8:
                    main.changeVehicleState();
                    break;
                case 9:
                    main.printMaintenanceAndOperationPeriods();
                    break;
                case 10:
                    main.vehicleDeparture();
                    break;
                case 11:
                    main.calculateTripNumbersAndTotalTravelTime();
                    break;
                case 12:
                    main.calculateStampedTickets();
                    break;
                case 13:
                    main.createVehicle();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Program terminated.");

    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1 - Create Seller");
        System.out.println("2 - Create User");
        System.out.println("3 - Buy Card");
        System.out.println("4 - Buy Service");
        System.out.println("5 - Check-in User on Vehicle");
        System.out.println("6 - Calculate number of Tickets and/or Subscriptions issued by a Seller");
        System.out.println("7 - Create Route and (optional) assign Vehicle to this Route");
        System.out.println("8 - Modify Vehicle State");
        System.out.println("9 - Print maintenance and operation periods of a Vehicle");
        System.out.println("10 - Vehicle Departure (new Trip)");
        System.out.println("11 - Calculate number of Trips of a specific Vehicle on each Route and its total travel time");
        System.out.println("12 - Calculate number of stamped Tickets in a given time period or on a specific Vehicle");
        System.out.println("13 - Create a vehicle");
        System.out.println("0 - Exit");
    }

    private static int getChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    private void createSeller() {
        scanner.nextLine();
        System.out.println("Creating a new seller...");
        System.out.println("Enter seller type (1 for Shop, 2 for Vending Machine): ");
        int type = scanner.nextInt();
        scanner.nextLine();
        if (type == 1) {
            System.out.println("What's the shop's address?");
            String shopData = scanner.nextLine();
            Shop shop = new Shop(shopData);
            shopDao.save(shop);
        } else if (type == 2) {
            System.out.println("Enter vending machine state (true/false): ");
            boolean operative = scanner.nextBoolean();
            VendingMachine vendingMachine = new VendingMachine(operative);
            vendingMachineDao.save(vendingMachine);
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.println("Seller created successfully.");
    }


    private void createUser() {
        scanner.nextLine();
        System.out.println("Creating a new user...");
        System.out.println("Enter user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter user last name: ");
        String lastName = scanner.nextLine();

        User user = new User(name, lastName);

        userDao.save(user);

        System.out.println("User created successfully.");
    }

    private void buyCard() {
        scanner.nextLine();
        System.out.println("Creating a new card...");
        System.out.println("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = userDao.getById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (user.getCard() != null) {
            System.out.println("User already has a card.");
            return;
        }

        Card card = new Card(user);

        cardDao.save(card);

        System.out.println("Card created successfully.");
    }

    private void buyService() {
        System.out.println("Buying a new service...");
        System.out.println("Choose service type (1 for Ticket, 2 for Subscription): ");
        int serviceType = scanner.nextInt();
        scanner.nextLine();

        if (serviceType == 1) {
            buyTicket();
        } else if (serviceType == 2) {
            buySubscription();
        } else {
            System.out.println("Invalid service type.");
        }
    }

    private void buyTicket() {
        System.out.println("Buying a new ticket...");
        System.out.println("Enter user ID: ");
        int userId = (int) scanner.nextLong();
        scanner.nextLine();

        User user = userDao.getById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Choose a seller (1 for Shop, 2 for Vending Machine): ");
        int sellerType = scanner.nextInt();
        scanner.nextLine();

        Seller seller = null;
        if (sellerType == 1) {
            List<Shop> shops = ShopDao.getAllShops();
            if (shops.isEmpty()) {
                System.out.println("No shops available.");
                return;
            }
            System.out.println("Available shops: ");
            for (Shop shop : shops) {
                System.out.println(shop.getSellerId() + " - " + shop.getAddress());
            }
            System.out.println("Choose a shop: ");
            int shopId = scanner.nextInt();
            scanner.nextLine();
            seller = ShopDao.getById(shopId);
        } else if (sellerType == 2) {
            List<VendingMachine> vendingMachines = VendingMachineDao.getAllVendingMachines();
            if (vendingMachines.isEmpty()) {
                System.out.println("No vending machines available.");
                return;
            }
            System.out.println("Available vending machines: ");
            for (VendingMachine machine : vendingMachines) {
                System.out.println(machine.getSellerId() + " - Operative: " + machine.isState());
            }
            System.out.println("Choose a vending machine: ");
            int machineId = scanner.nextInt();
            scanner.nextLine();
            seller = VendingMachineDao.getById(machineId);
        } else {
            System.out.println("Invalid seller type.");
            return;
        }

        Ticket ticket = new Ticket(seller, user);


        ticketDao.save(ticket);

        System.out.println("Ticket purchased successfully.");
    }

    private void buySubscription() {
        System.out.println("Buying a new subscription...");
        System.out.println("Enter user ID: ");
        long userId = scanner.nextLong();
        scanner.nextLine();

        User user = userDao.getById((int) userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (!user.checkUserCard()) {
            System.out.println("To buy a subscription, you must first buy a card.");
            System.out.println("Would you like to buy a card? (Y/N)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                buyCard();
            } else {
                System.out.println("You can alternatively buy a ticket.");
                return;
            }
        }

        Card card = user.getCard();


        System.out.println("Choose subscription duration: ");
        System.out.println("1. Weekly");
        System.out.println("2. Monthly");
        int subscriptionDurationInt = scanner.nextInt();
        scanner.nextLine();
        SubscriptionDuration subscriptionDuration;
        if (subscriptionDurationInt == 1) {
            subscriptionDuration = SubscriptionDuration.WEEKLY;
        } else if (subscriptionDurationInt == 2) {
            subscriptionDuration = SubscriptionDuration.MONTHLY;
        } else {
            System.out.println("Wrong choice.");
            Main.displayMenu();
            return;
        }


        System.out.println("Choose a seller (1 for Shop, 2 for Vending Machine): ");
        int sellerType = scanner.nextInt();
        scanner.nextLine();

        Seller seller;
        if (sellerType == 1) {
            List<Shop> shops = ShopDao.getAllShops();
            if (shops.isEmpty()) {
                System.out.println("No shops available.");
                return;
            }
            System.out.println("Available shops: ");
            for (Shop shop : shops) {
                System.out.println(shop.getSellerId() + " - " + shop.getAddress());
            }
            System.out.println("Choose a shop: ");
            int shopId = scanner.nextInt();
            scanner.nextLine();
            seller = ShopDao.getById(shopId);
        } else if (sellerType == 2) {
            List<VendingMachine> vendingMachines = VendingMachineDao.getAllVendingMachines();
            if (vendingMachines.isEmpty()) {
                System.out.println("No vending machines available.");
                return;
            }
            System.out.println("Available vending machines: ");
            for (VendingMachine machine : vendingMachines) {
                System.out.println(machine.getSellerId() + " - Operative: " + machine.isState());
            }
            System.out.println("Choose a vending machine: ");
            int machineId = scanner.nextInt();
            scanner.nextLine();
            seller = VendingMachineDao.getById(machineId);
        } else {
            System.out.println("Invalid seller type.");
            return;
        }

        Subscription subscription = new Subscription(seller, subscriptionDuration, card);


        subscriptionDao.save(subscription);

        System.out.println("Subscription purchased successfully.");
    }

    private void checkInUser() {
        User user = selectUser();
        if (user == null) {
            return;
        }

        Route route = selectRoute();
        if (route == null) {
            return;
        }

        checkIn(user, route);
    }

    public User selectUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user ID:");
        long userId = scanner.nextLong();
        User user = userDao.getById((int) userId);
        if (user == null) {
            System.out.println("User not found.");
            return null;
        }
        return user;
    }

    public Route selectRoute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter route ID:");
        long routeId = scanner.nextLong();
        Route route = RouteDao.getById((int) routeId);
        if (route == null) {
            System.out.println("Route not found.");
            return null;
        }
        return route;
    }

    private void calculateTicketsAndSubscriptions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter start date (yyyy-MM-dd): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter end date (yyyy-MM-dd): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Select service type:");
        System.out.println("1 - Ticket");
        System.out.println("2 - Subscription");
        System.out.println("3 - Both");
        int serviceTypeChoice = scanner.nextInt();

        System.out.println("Select seller type:");
        System.out.println("1 - Shop");
        System.out.println("2 - Vending Machine");
        System.out.println("3 - Both");
        int sellerTypeChoice = scanner.nextInt();

        Map<Seller, Map<String, Integer>> soldServicesMap = SellerDao.soldServices(startDate, endDate, serviceTypeChoice, sellerTypeChoice);

        System.out.println("Services sold:");
        for (Map.Entry<Seller, Map<String, Integer>> entry : soldServicesMap.entrySet()) {
            Seller seller = entry.getKey();
            Map<String, Integer> serviceCounts = entry.getValue();
            System.out.println("Seller ID: " + seller.getSellerId());
            for (Map.Entry<String, Integer> serviceEntry : serviceCounts.entrySet()) {
                System.out.println("  " + serviceEntry.getKey() + ": " + serviceEntry.getValue());
            }
        }
    }

    private void createVehicle() {
        System.out.println("New Vehicle creation:");
        System.out.println("What's the vehicle's type?");
        System.out.println("1. BUS");
        System.out.println("2. TRAM");
        int vehicleInt = scanner.nextInt();
        VehicleType vehicleType;
        if (vehicleInt == 1) {
            vehicleType = VehicleType.BUS;
        } else if (vehicleInt == 2) {
            vehicleType = VehicleType.TRAM;
        } else {
            System.out.println("Wrong choice.");
            Main.displayMenu();
            return;
        }

        System.out.println("Do you want to assign a Route to this vehicle? Y/N");
        scanner.nextLine();
        String assignRouteChoice = scanner.nextLine();

        Route route = null;

        if (assignRouteChoice.equalsIgnoreCase("Y")) {
            List<Route> routes = routeDao.getAllRoutes();
            if (routes.isEmpty()) {
                System.out.println("No routes available");
            } else {
                for (Route r : routes) {
                    System.out.println("Route ID: " + r.getRouteId() + " - " + "Da " + r.getStartLocation() + " a " + r.getEndLocation() + ".");
                }
                System.out.println("Choose a route (ID): ");
                int routeId = scanner.nextInt();
                scanner.nextLine();
                route = RouteDao.getById(routeId);
            }
        }

        if (route != null) {
            Vehicle vehicle = new Vehicle(vehicleType, route);
            vehicleDao.save(vehicle);
            vehicleStateDao.save(new VehicleState(false, vehicle));
        } else {
            Vehicle vehicle = new Vehicle(vehicleType, null);
            vehicleDao.save(vehicle);
            vehicleStateDao.save(new VehicleState(false, vehicle));
        }
    }


    private void createRouteAndAssignVehicle() {
        System.out.println("Creating a new Route...");
        System.out.println("Enter start location:");
        scanner.nextLine();
        String startLocation = scanner.nextLine();
        System.out.println("Enter end location");
        String endLocation = scanner.nextLine();
        System.out.println("Enter duration (in minutes)");
        int duration = scanner.nextInt();
        scanner.nextLine();


        Route route = new Route(startLocation, endLocation, duration);


        System.out.println("Do you want to choose a vehicle for this route? Y/N");
        String assignVehicleChoice = scanner.nextLine();

        if (assignVehicleChoice.equalsIgnoreCase("Y")) {
            System.out.println("Choose a vehicle for this route");
            List<Vehicle> availableVehicles = vehicleDao.getAvailableVehicles();
            if (availableVehicles.isEmpty()) {
                System.out.println("No vehicles available.");
            } else {
                System.out.println("Vehicles available:");
                for (Vehicle vehicle : availableVehicles) {
                    System.out.println("Vehicle type: " + vehicle.getVehicleType() + " - " + "Vehicle ID: " + vehicle.getVehicleId());
                }
                System.out.println("Enter vehicle ID:");
                int vehicleId = scanner.nextInt();
                scanner.nextLine();

                Vehicle selectedVehicle = null;
                for (Vehicle vehicle : availableVehicles) {
                    if (vehicle.getVehicleId() == vehicleId) {
                        selectedVehicle = vehicle;
                        break;
                    }
                }

                if (selectedVehicle == null) {
                    System.out.println("Invalid vehicle ID. No Vehicle assigned to this route.");
                } else {
                    selectedVehicle.setRoute(route);
                    System.out.println("Vehicle with ID " + selectedVehicle.getVehicleId() + " assigned to Route " + route.getRouteId() + ".");
                }
            }
        }

        routeDao.save(route);
        System.out.println("Route created successfully.");
    }

    private void changeVehicleState() {
        List<Vehicle> allVehicles = vehicleDao.getAllVehicles();
        System.out.println("Lista dei veicoli:");

        for (Vehicle vehicle : allVehicles) {
            System.out.println(vehicle.getVehicleId() + " - " + vehicle.getVehicleType() + " - STATO: " + vehicleStateDao.getVehicleState(vehicle));
        }

        System.out.print("Inserisci l'ID del veicolo di cui vuoi cambiare lo stato di manutenzione: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();


            vehicleStateDao.updateVehicleMaintenanceStatus(vehicleId);
            System.out.println("Stato di manutenzione del veicolo " + vehicleId + " cambiato con successo.");

    }

    private void printMaintenanceAndOperationPeriods() {
        System.out.println("Enter vehicle ID:");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle = vehicleDao.getById(vehicleId);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        List<VehicleState> operationalPeriods = vehicleStateDao.getOperationalPeriodsByVehicleId(vehicleId);
        List<VehicleState> maintenancePeriods = vehicleStateDao.getMaintenancePeriodsByVehicleId(vehicleId);

        System.out.println("Operational Periods for Vehicle ID " + vehicleId + ":");
        for (VehicleState period : operationalPeriods) {
            System.out.println("Start Date: " + period.getStartState() + " - End Date: " + period.getEndState());
        }

        System.out.println("Maintenance Periods for Vehicle ID " + vehicleId + ":");
        for (VehicleState period : maintenancePeriods) {
            System.out.println("Start Date: " + period.getStartState() + " - End Date: " + period.getEndState());
        }
    }

    private void vehicleDeparture() {
        System.out.println("Start a trip!");
        System.out.println("Enter Vehicle ID:");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle = vehicleDao.getById(vehicleId);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.println("Inserisci l'ID della Route:");
        int routeId = scanner.nextInt();
        scanner.nextLine();

        Route route = routeDao.getById(routeId);
        if (route == null) {
            System.out.println("Route not found.");
            return;
        }

        vehicle.setRoute(route);
        vehicle.setUsersOnBoard(0);

        int baseDuration = route.getDuration();
        int usersDuration = vehicle.getUsersOnBoard() * 20;
        int tenPercentOfBaseDuration = baseDuration / 10;
        int randomFactor = (int) (Math.random() * 2) - 1;
        int adjustedRandomFactor = randomFactor * tenPercentOfBaseDuration;
        int totalDurationInSeconds = baseDuration + usersDuration + adjustedRandomFactor;

        Duration totalDuration = Duration.ofSeconds(totalDurationInSeconds);
        long totalDurationInMinutes = totalDuration.toMinutes();

        Trip trip = new Trip(vehicle, route, Duration.ofMinutes(totalDurationInMinutes));
        tripDao.createTrip(vehicle, route, Duration.ofMinutes(totalDurationInMinutes));
        System.out.println("Viaggio iniziato per il veicolo con ID " + vehicleId + " sul percorso con ID " + routeId + ". Durata totale: " + totalDurationInMinutes + " minuti.");
    }




    private void calculateTripNumbersAndTotalTravelTime() {
        System.out.println("Choose an operation:");
        System.out.println("1. Calculate the number of trips of a specific vehicle within a specified time frame");
        System.out.println("2. Calculate the total travel time of a vehicle and the average actual travel time of its trips");

        int operation = scanner.nextInt();
        scanner.nextLine();

        switch (operation) {
            case 1:
                System.out.println("Enter the vehicle ID:");
                int vehicleId = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter the start of the time frame (format YYYY-MM-DD HH:MM):");
                String startDateStr = scanner.nextLine();
                LocalDateTime startDate = LocalDateTime.parse(startDateStr);

                System.out.println("Enter the end of the time frame (format YYYY-MM-DD HH:MM):");
                String endDateStr = scanner.nextLine();
                LocalDateTime endDate = LocalDateTime.parse(endDateStr);

                int tripCount = tripDao.countTripsByVehicle(vehicleId, startDate, endDate);
                System.out.println("Number of trips in the specified period: " + tripCount);
                break;

            case 2:
                System.out.println("Enter the vehicle ID:");
                vehicleId = scanner.nextInt();
                scanner.nextLine();

                double averageDuration = tripDao.averageTripDuration(vehicleId);
                System.out.println("Total travel time: " + averageDuration + " minutes");
                break;

            default:
                System.out.println("Invalid option.");
                break;
        }
    }


    private void calculateStampedTickets() {
        scanner.nextLine();
        System.out.println("Calculating stamped tickets...");
        System.out.println("Enter start date (yyyy-MM-dd): ");
        String startDateStr = scanner.nextLine();
        System.out.println("Enter end date (yyyy-MM-dd): ");
        String endDateStr = scanner.nextLine();
        System.out.println("Enter vehicle ID (optional, press Enter to skip): ");
        String vehicleIdStr = scanner.nextLine();

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            Integer vehicleId = null;
            if (!vehicleIdStr.isEmpty()) {
                vehicleId = Integer.parseInt(vehicleIdStr);
            }

            int stampedTickets = ticketDao.checkStampedTickets(startDate, endDate, vehicleId);
            System.out.println("Number of stamped tickets: " + stampedTickets);
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter dates in the format yyyy-MM-dd and vehicle ID as an integer.");
        }
    }


    private void checkIn(User user, Route route) {

        Vehicle vehicle = vehicleDao.checkVehicleAvailabilityByRoute(route);

        if (vehicle == null) {
            List<Vehicle> availableVehicles = vehicleDao.getAvailableVehicles();
            if (availableVehicles.isEmpty()) {
                System.out.println("No vehicles available on this route.");
            }
            vehicle = availableVehicles.get(0);
        }

        if (vehicle.checkMaxCapacity()) {
            System.out.println("We are full, wait for another vehicle.");
            return;
        }


        if (user.checkUserCard()) {
            // se true ha la card
            Card card = user.getCard();
            if (card.getSubscription() != null) {
                Subscription subscription = card.getSubscription();
                if (subscription.checkSubscriptionValidity()) {
                    System.out.println("Your subscription is valid: welcome on board!");
                    vehicle.setUsersOnBoard(vehicle.getUsersOnBoard() + 1);
                }
            }
        } else {
            if (userDao.getTicketsByUser(user).isEmpty()) {
                System.out.println("Ticket/Subscription not found.");
                return;
            } else {
                Ticket ticket = userDao.getTicketsByUser(user).getFirst();
                ticketDao.checkTicket(ticket);
                System.out.println("Welcome on board");
                vehicle.setUsersOnBoard(vehicle.getUsersOnBoard() + 1);
            }
        }
    }
}







