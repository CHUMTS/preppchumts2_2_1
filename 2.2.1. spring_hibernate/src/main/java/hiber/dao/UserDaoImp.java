package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {


   private SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
      return query.getResultList();
   }


   public User getUserByCar(String model, int series) {
      Car car = sessionFactory.getCurrentSession()
              .createQuery("from Car where model=:model and series=:series", Car.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .getSingleResult();

      TypedQuery<User> userQuery = sessionFactory.getCurrentSession()
              .createQuery("from User where userCar =" +car.getId(), User.class);
      return userQuery.getSingleResult();
   }

}