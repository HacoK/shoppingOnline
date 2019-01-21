package edu.nju.shoppingOnline.repository;

import edu.nju.shoppingOnline.model.Goods;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;

@Stateless
@LocalBean
public class GoodsRepo {
    @PersistenceContext
    private EntityManager em;

    public GoodsRepo(){
    }

    public Goods getGoods(Integer gid){
        Goods goods=em.find(Goods.class, gid);
        return goods;
    }

    public ArrayList<Goods> listGoods(String type){
        ArrayList<Goods> list;
        if(type==null||type.equals("all")){
            Query query = em.createNativeQuery("SELECT * FROM goods",Goods.class);
            list = (ArrayList<Goods>) query.getResultList();
            em.clear();
        }else{
            Query query = em.createNativeQuery("SELECT * FROM goods WHERE type = \'"+type+"\'",Goods.class);
            list = (ArrayList<Goods>) query.getResultList();
            em.clear();
        }
        return list;
    }

    public Integer countGoods(String type){
        Integer count=0;
        if(type==null||type.equals("all")){
            Query query =em.createNativeQuery("SELECT count(*) FROM goods");
            count = Integer.valueOf(query.getSingleResult().toString());
        }else{
            Query query =em.createNativeQuery("SELECT count(*) FROM goods WHERE type = \'"+type+"\'");
            count = Integer.valueOf(query.getSingleResult().toString());
        }
        return count;
    }

}
