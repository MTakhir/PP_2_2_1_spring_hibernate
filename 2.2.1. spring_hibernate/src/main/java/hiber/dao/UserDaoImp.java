package hiber.dao;

import hiber.config.AppConfig;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.awt.*;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
         String HQL = "FROM User u LEFT OUTER JOIN FETCH u.car c WHERE c.model=:m AND c.series=:s";
         User user = (User) sessionFactory.getCurrentSession().createQuery(HQL)
                 .setParameter("m",model).setParameter("s", series)
                 .uniqueResult();
      return user;
   }

}
