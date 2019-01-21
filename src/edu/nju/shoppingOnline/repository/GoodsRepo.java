package edu.nju.shoppingOnline.repository;

import edu.nju.shoppingOnline.model.Goods;
import edu.nju.shoppingOnline.utli.HQLUtil;
import org.hibernate.loader.custom.sql.SQLQueryParser;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;

@Stateless
@LocalBean
public class GoodsRepo {
//    @PersistenceContext
//    private EntityManager em;


    public GoodsRepo(){
    }

    public Goods getGoods(Integer gid){
        Goods goods=HQLUtil.getSession().get(Goods.class, gid);
        //Goods goods=em.find(Goods.class, gid);
        return goods;
    }

    public ArrayList<Goods> listGoods(String type){
        ArrayList<Goods> list;
        if(type==null||type.equals("all")){
//            Query query = em.createNativeQuery("SELECT * FROM goods",Goods.class);
//            list = (ArrayList<Goods>) query.getResultList();
//            em.clear();
            list = (ArrayList<Goods>)HQLUtil.find("from Goods");
        }else{
//            Query query = em.createNativeQuery("SELECT * FROM goods WHERE type = \'"+type+"\'",Goods.class);
//            list = (ArrayList<Goods>) query.getResultList();
//            em.clear();
            list = (ArrayList<Goods>)HQLUtil.find("from Goods where type = \'"+type+"\'");
        }
        return list;
    }

    public Integer countGoods(String type){
        Integer count=0;
        if(type==null||type.equals("all")){
//            Query query =em.createNativeQuery("SELECT count(*) FROM goods");
//            count = Integer.valueOf(query.getSingleResult().toString());
            // 1、得到Query对象，并写入hql语句
            org.hibernate.query.Query query = HQLUtil.getSession().createQuery("select count(*) from Goods");
            //2、获取结果（结果为long类型）
            count = Integer.valueOf(query.uniqueResult().toString());
        }else{
//            Query query =em.createNativeQuery("SELECT count(*) FROM goods WHERE type = \'"+type+"\'");
//            count = Integer.valueOf(query.getSingleResult().toString());

            org.hibernate.query.Query query = HQLUtil.getSession().createQuery("select count(*) from Goods where type = \'"+type+"\'");
            count = Integer.valueOf(query.uniqueResult().toString());
        }
        return count;
    }

}
