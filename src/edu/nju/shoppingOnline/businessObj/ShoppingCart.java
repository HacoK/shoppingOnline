package edu.nju.shoppingOnline.businessObj;

import edu.nju.shoppingOnline.domain.Goods;
import edu.nju.shoppingOnline.domain.Order;
import edu.nju.shoppingOnline.domain.User;
import edu.nju.shoppingOnline.repository.*;
import edu.nju.shoppingOnline.service.TransService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map nameMap=new HashMap<Integer,String>();
    private Map priceMap=new HashMap<Integer,Double>();
    private Map quantityMap=new HashMap<Integer,Integer>();

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

    public String payItems(String account,ArrayList<Integer> list,Double amount) {
        UserRepo userRepo=(UserRepo) RepoFactory.getFactory().getInstance("UserRepo");
        GoodsRepo goodsRepo=(GoodsRepo) RepoFactory.getFactory().getInstance("GoodsRepo");
        TransService transService =(TransService) RepoFactory.getFactory().getInstance("TransService");
        User user=userRepo.getUser(account);
        Order order;
        Goods goods;
        ArrayList<Order> orders=new ArrayList<>();
        ArrayList<Goods> gList=new ArrayList<>();
        if(user==null)
            return "User doesn\'t exist!";
        if(user.getBalance()<amount)
            return "User doesn\'t have enough money!";
        for(Integer i:list){
            goods=goodsRepo.getGoods(i);
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
        transService.payBill(user,orders,gList);
        for(Integer i:list){
            removeItem(i);
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
