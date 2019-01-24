package edu.nju.shoppingOnline.repository;

import edu.nju.shoppingOnline.model.Goods;
import org.springframework.stereotype.Repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class GoodsRepo {
    Context ctx;
    DataSource ds;
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;
    int opNum;

    public GoodsRepo(){
        try {
            ctx=new InitialContext();
            ds= (DataSource) ctx.lookup("java:comp/env/jdbc/shoppingDB") ;
        } catch (NamingException e) {
            System.out.println("Initial Error: "+e);
        }
    }

    public Goods getGoods(Integer gid){
        Goods goods=new Goods();
        try {
            con= ds.getConnection();
            stmt = con.createStatement();
            rs=stmt.executeQuery("SELECT * FROM goods WHERE gid = "+gid);
            if(rs.next()){
                goods.setGid(gid);
                goods.setName(rs.getString("name"));
                goods.setType(rs.getString("type"));
                goods.setNum(rs.getInt("num"));
                goods.setPrice(rs.getDouble("price"));
            }else{
                goods=null;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Service[SQL] Error: "+e);
        }
        return goods;
    }

    public ArrayList<Goods> listGoods(String type){
        ArrayList<Goods> list=new ArrayList<>();
        try {
            con= ds.getConnection();
            stmt = con.createStatement();
            if(type==null||type.equals("all")){
                rs=stmt.executeQuery("SELECT * FROM goods");
            }else{
                rs=stmt.executeQuery("SELECT * FROM goods WHERE type = \'"+type+"\'");
            }
            while(rs.next()){
                Goods goods=new Goods();
                goods.setGid(rs.getInt("gid"));
                goods.setName(rs.getString("name"));
                goods.setType(rs.getString("type"));
                goods.setPrice(rs.getDouble("price"));
                goods.setNum(rs.getInt("num"));
                list.add(goods);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Service[SQL] Error: "+e);
        }
        return list;
    }

    public Integer countGoods(String type){
        Integer count=0;
        try {
            con= ds.getConnection();
            stmt = con.createStatement();
            if(type==null||type.equals("all")){
                rs=stmt.executeQuery("SELECT count(*) FROM goods");
            }else{
                rs=stmt.executeQuery("SELECT count(*) FROM goods WHERE type = \'"+type+"\'");
            }
            if(rs.next()){
                count=rs.getInt(1);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Service[SQL] Error: "+e);
        }
        return count;
    }

    public boolean updateGoods(Goods goods){
        try {
            con= ds.getConnection();
            pstmt = con.prepareStatement("UPDATE goods SET name = ?,type = ?,num = ?,price = ? WHERE gid = ? ");
            pstmt.setString(1,goods.getName());
            pstmt.setString(2,goods.getType());
            pstmt.setInt(3,goods.getNum());
            pstmt.setDouble(4,goods.getPrice());
            pstmt.setInt(5,goods.getGid());
            opNum= pstmt.executeUpdate();
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Service[SQL] Error: "+e);
        }
        if(opNum==1){
            return true;
        }else{
            return false;
        }
    }
}
