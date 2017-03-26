package net.nicken.repository.jpa;

import net.nicken.model.User;
import net.nicken.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaUserRepositoryImpl implements UserRepository {

    /*@Autowired
    private SessionFactory sessionFactory;

    private Session openSession(){
        return sessionFactory.getCurrentSession();
    }*/

    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
        if(user.isNew()){
            em.persist(user);
            return user;
        }else {
            return em.merge(user);
        }
    }

    @Override
    public boolean delete(int id) {
        /*User ref = em.getReference(User.class, id);
        em.remove(ref);*/

        Query query = em.createQuery("DELETE FROM User u WHERE u.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
