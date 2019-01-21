package edu.nju.shoppingOnline.repository;

import edu.nju.shoppingOnline.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserRepo {
    @PersistenceContext
    private EntityManager em;

    public UserRepo(){
    }

    public boolean addUser(User user){
        Query query=em.createNativeQuery("INSERT INTO user (account,passwd,email,balance) VALUES(?1,?2,?3,0)");
        query.setParameter(1,user.getAccount());
        query.setParameter(2,user.getPasswd());
        query.setParameter(3,user.getEmail());
        query.executeUpdate();
        return true;
    }

    public User getUser(String account){
        User user=em.find(User.class, account);
        return user;
    }

    public boolean updateUser(User user){
        Query query=em.createNativeQuery("UPDATE user SET balance = ?1,email = ?2,passwd = ?3 WHERE account = ?4 ");
        query.setParameter(1,user.getBalance());
        query.setParameter(2,user.getEmail());
        query.setParameter(3,user.getPasswd());
        query.setParameter(4,user.getAccount());
        query.executeUpdate();
        return true;
    }
}
