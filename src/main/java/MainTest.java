import Dao.SellerDao.ShopDao;
import Dao.UserDao;
import Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainTest {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("bus");
    static EntityManager em = emf.createEntityManager();

    static UserDao userDao = new UserDao(em);

    public static void main(String[] args) {
        User user = new User();

        UserDao.getById(7);

    }
}
