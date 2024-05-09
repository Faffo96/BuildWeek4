package Dao.SellerDao;

import Entities.Sellers.Seller;
import Entities.Services.Service;
import Entities.Services.Subscription;
import Entities.Services.Ticket;
import enums.VehicleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDao {
    private static EntityManager em;

    public SellerDao(EntityManager em) {
        this.em = em;
    }

    public void save(Seller seller){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(seller);
        et.commit();
    }

    public Seller getById(int id){
        return em.find(Seller.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Seller seller = getById(id);

        if (seller != null){
            em.remove(seller);
        } else {
            System.out.println("Rivenditore non trovato");
        }
        et.commit();
    }

    public static Map<Seller, Map<String, Integer>> soldServices(LocalDate startDate, LocalDate endDate, int serviceTypeChoice, int sellerTypeChoice) {
        Map<Seller, Map<String, Integer>> soldServicesMap = new HashMap<>();

        //query in base ai parametri ricevuti
        StringBuilder queryBuilder = new StringBuilder("SELECT s FROM Service s WHERE s.purchaseDate BETWEEN :startDate AND :endDate");
        if (serviceTypeChoice != 3) {
            queryBuilder.append(" AND (s INSTANCE OF ");
            if (serviceTypeChoice == 1) {
                queryBuilder.append("Ticket");
            } else {
                queryBuilder.append("Subscription");
            }
            queryBuilder.append(")");
        }
        if (sellerTypeChoice != 3) {
            queryBuilder.append(" AND (");
            if (sellerTypeChoice == 1) {
                queryBuilder.append("s.seller INSTANCE OF Shop");
            } else {
                queryBuilder.append("s.seller INSTANCE OF VendingMachine");
            }
            queryBuilder.append(")");
        }

        TypedQuery<Service> serviceQuery = em.createQuery(queryBuilder.toString(), Service.class);
        serviceQuery.setParameter("startDate", startDate);
        serviceQuery.setParameter("endDate", endDate);

        List<Service> services = serviceQuery.getResultList();
        for (Service service : services) {
            Seller seller = service.getSeller();
            String serviceType = (service instanceof Ticket) ? "Ticket" : "Subscription";
            soldServicesMap.putIfAbsent(seller, new HashMap<>());
            Map<String, Integer> sellerServices = soldServicesMap.get(seller);
            sellerServices.put(serviceType, sellerServices.getOrDefault(serviceType, 0) + 1);
        }
        return soldServicesMap;
    }

}
