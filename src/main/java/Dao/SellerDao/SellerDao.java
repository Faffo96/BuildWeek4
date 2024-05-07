package Dao.SellerDao;

import Entities.Sellers.Seller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class SellerDao {
    private EntityManager em;

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
}
