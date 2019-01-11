package edu.nju.shoppingOnline.service;

import edu.nju.shoppingOnline.domain.Goods;
import edu.nju.shoppingOnline.domain.Order;
import edu.nju.shoppingOnline.domain.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class TransService {
    Context ctx;
    DataSource ds;
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;
    int opNum;

    public TransService(){
        try {
            ctx=new InitialContext();
            ds= (DataSource) ctx.lookup("java:comp/env/jdbc/shoppingDB") ;
        } catch (NamingException e) {
            System.out.println("Initial Error: "+e);
        }
    }
    public void payBill(User user, ArrayList<Order> oList, ArrayList<Goods> gList){
        if(user==null)
            return;
        Goods goods;
        Order order;
        try {
            con= ds.getConnection();
            con.setAutoCommit(false);
            for(int i=0;i<gList.size();i++){
                goods=gList.get(i);
                pstmt = con.prepareStatement("UPDATE goods SET name = ?,type = ?,num = ?,price = ? WHERE gid = ? ");
                pstmt.setString(1,goods.getName());
                pstmt.setString(2,goods.getType());
                pstmt.setInt(3,goods.getNum());
                pstmt.setDouble(4,goods.getPrice());
                pstmt.setInt(5,goods.getGid());
                opNum= pstmt.executeUpdate();
            }

            for(int i=0;i<oList.size();i++)
            {
                order=oList.get(i);
                pstmt = con.prepareStatement("INSERT INTO `order` (oid,account,gid,quantity,amount,date) VALUES(?,?,?,?,?,?)");
                stmt = con.createStatement();
                rs=stmt.executeQuery("SELECT count(*) FROM `order`");
                rs.next();
                pstmt.setInt(1,rs.getInt(1)+1);
                pstmt.setString(2,order.getAccount());
                pstmt.setInt(3,order.getGid());
                pstmt.setInt(4,order.getQuantity());
                pstmt.setDouble(5,order.getAmount());
                pstmt.setDate(6,Date.valueOf(order.getDate()));
                opNum= pstmt.executeUpdate();
            }

            pstmt = con.prepareStatement("UPDATE user SET balance = ?,email = ?,passwd = ? WHERE account = ? ");
            pstmt.setDouble(1,user.getBalance());
            pstmt.setString(2,user.getEmail());
            pstmt.setString(3,user.getPasswd());
            pstmt.setString(4,user.getAccount());
            opNum= pstmt.executeUpdate();

            con.commit();
            con.setAutoCommit(true);
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            try {
                con.rollback();
                if(rs!=null)
                    rs.close();
                if(stmt!=null)
                    stmt.close();
                if(con!=null)
                    con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
