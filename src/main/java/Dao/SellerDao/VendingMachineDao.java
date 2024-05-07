package Dao.SellerDao;

import Entities.Sellers.VendingMachine;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class VendingMachineDao {
    private EntityManager em;

    public VendingMachineDao(EntityManager em) {
        this.em = em;
    }

    public void save(VendingMachine vendingMachine){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(vendingMachine);
        et.commit();
    }

    public VendingMachine getById(int id){
        return em.find(VendingMachine.class, id);
    }

    public void delete(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        VendingMachine vendingMachine = getById(id);

        if (vendingMachine != null){
            em.remove(vendingMachine);
        } else {
            System.out.println("non trovato");
        }
        et.commit();
    }
}
