package Dao.SellerDao;

import Entities.Sellers.Shop;
import Entities.Sellers.VendingMachine;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class VendingMachineDao {
    private static EntityManager em;

    public VendingMachineDao(EntityManager em) {
        this.em = em;
    }

    public void save(VendingMachine vendingMachine){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(vendingMachine);
        et.commit();
    }

    public static VendingMachine getById(int id){
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

    public void createVendingMachine(boolean operative) {
        VendingMachine vendingMachine = new VendingMachine(operative);
        save(vendingMachine);
    }

    public static List<VendingMachine> getAllVendingMachines() {
        TypedQuery<VendingMachine> query = em.createQuery("SELECT vm FROM VendingMachine vm", VendingMachine.class);
        return query.getResultList();
    }
}
