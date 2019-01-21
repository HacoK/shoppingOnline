package edu.nju.shoppingOnline.repository;

import edu.nju.shoppingOnline.model.Order;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.*;

@Stateless
@LocalBean
public class OrderRepo {
//    @PersistenceContext
//    private EntityManager em;

    public OrderRepo(){
    }

    public boolean addOrder(Order order){
//        Integer oid=0;
//        Query query=em.createNativeQuery("SELECT count(*) FROM `order`");
//        oid=Integer.valueOf(query.getSingleResult().toString())+1;
//        query=em.createNativeQuery("INSERT INTO `order` (oid,account,gid,quantity,amount,date) VALUES(?1,?2,?3,?4,?5,?6)");
//        query.setParameter(1,oid);
//        query.setParameter(2,order.getAccount());
//        query.setParameter(3,order.getGid());
//        query.setParameter(4,order.getQuantity());
//        query.setParameter(5,order.getAmount());
//        query.setParameter(6,Date.valueOf(order.getDate()));
        return true;
    }
}
