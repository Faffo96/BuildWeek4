package Dao.SellerDao;

import Entities.Sellers.Shop;
import Entities.Sellers.VendingMachine;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class ShopDao {
    private static EntityManager em;

    public ShopDao(EntityManager em) {
        this.em = em;
    }

    public static void save(Shop shop){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(shop);
        et.commit();
    }

    public static Shop getById(int id){
        return em.find(Shop.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Shop shop = getById(id);

        if (shop != null){
            em.remove(shop);
        } else {
            System.out.println("Riventidore autorizzato non trovato");
        }
        et.commit();
    }

    public void createShop(String address) {
        Shop shop = new Shop(address);
        save(shop);
    }

    public static List<Shop> getAllShops() {
        TypedQuery<Shop> query = em.createQuery("SELECT s FROM Shops s", Shop.class);
        return query.getResultList();
    }
}
