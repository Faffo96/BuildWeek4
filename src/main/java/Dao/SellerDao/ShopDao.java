package Dao.SellerDao;

import Entities.Sellers.Shop;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ShopDao {
    private EntityManager em;

    public ShopDao(EntityManager em) {
        this.em = em;
    }

    public void save(Shop shop){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(shop);
        et.commit();
    }

    public Shop getById(int id){
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
}
