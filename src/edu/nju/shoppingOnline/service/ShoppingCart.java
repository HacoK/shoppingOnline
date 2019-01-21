package edu.nju.shoppingOnline.service;

import edu.nju.shoppingOnline.model.Goods;
import edu.nju.shoppingOnline.model.Order;
import edu.nju.shoppingOnline.model.User;
import edu.nju.shoppingOnline.repository.*;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Stateful
public class ShoppingCart {
    private Map nameMap=new HashMap<Integer,String>();
    private Map priceMap=new HashMap<Integer,Double>();
    private Map quantityMap=new HashMap<Integer,Integer>();


    public ShoppingCart() throws NamingException {
    }


    public void addGoods(Integer gid,String name,Double price,Integer quantity){
        if(nameMap.containsKey(gid)){
            Integer preAmount=(Integer)quantityMap.get(gid);
            quantityMap.put(gid,preAmount+quantity);
        }else{
            nameMap.put(gid,name);
            priceMap.put(gid,price);
            quantityMap.put(gid,quantity);
        }
    }

    @Remove
    public void clear(){
        nameMap.clear();
        priceMap.clear();
        quantityMap.clear();
    }

    public void removeItem(Integer key){
        nameMap.remove(key);
        priceMap.remove(key);
        quantityMap.remove(key);
    }

    public void updateItem(Integer key,Integer quantity){
        if(quantity==0)
            removeItem(key);
        else{
            quantityMap.put(key,quantity);
        }
    }

    public String payItems(String account,ArrayList<Integer> list,Double amount) throws NamingException {
        User user=((UserRepo)new InitialContext().lookup("java:global/webapp/UserRepo")).getUser(account);
        Order order;
        Goods goods;
        ArrayList<Order> orders=new ArrayList<>();
        ArrayList<Goods> gList=new ArrayList<>();
        if(user==null)
            return "User doesn\'t exist!";
        if(user.getBalance()<amount)
            return "User doesn\'t have enough money!";
        for(Integer i:list){
            goods=((GoodsRepo)new InitialContext().lookup("java:global/webapp/GoodsRepo")).getGoods(i);
            if(goods.getNum()<getQuantity(i))
                return "Purchase quantity exceeds inventory!";
            goods.setNum(goods.getNum()-getQuantity(i));
            gList.add(goods);

            order=new Order();
            order.setAccount(account);
            order.setAmount(getPrice(i)*getQuantity(i));
            order.setDate(LocalDate.now());
            order.setGid(i);
            order.setOid(0);
            order.setQuantity(getQuantity(i));
            orders.add(order);
        }
        user.setBalance(user.getBalance()-amount);
        ((TransService)new InitialContext().lookup("java:global/webapp/TransService")).payBill(user,orders,gList);
        if(list.size()==nameMap.size())
            clear();
        else{
            for(Integer i:list){
                removeItem(i);
            }
        }
        return "Pay finished successfully!";
    }

    public Integer getItemCount(){
        return priceMap.keySet().size();
    }

    public ArrayList<Integer> getKeys(){
        return new ArrayList(priceMap.keySet());
    }
    public String getName(Integer key){
        return (String) nameMap.get(key);
    }
    public Double getPrice(Integer key){
        return (Double) priceMap.get(key);
    }
    public Integer getQuantity(Integer key){
        return (Integer) quantityMap.get(key);
    }
}
