package hiber.service;

import hiber.config.AppConfig;
import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.Queue;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional
   @Override
   public User getUserByCar(String model, int series) {
      User user = null;
      try(Session session = new AppConfig().getSessionFactory().getObject().openSession()) {
         String HQL = "FROM User u LEFT OUTER JOIN FETCH u.car c WITH c.model=:m";
         user = (User) session.createQuery(HQL).setParameter("m",model)
                 .uniqueResult();
      } catch (HeadlessException e) {
         e.printStackTrace();
      }
      return user;
   }
}
