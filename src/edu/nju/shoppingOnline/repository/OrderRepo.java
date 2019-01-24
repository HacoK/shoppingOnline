package edu.nju.shoppingOnline.repository;

import edu.nju.shoppingOnline.model.Order;
import org.springframework.stereotype.Repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

@Repository
public class OrderRepo {
    Context ctx;
    DataSource ds;
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;
    int opNum;

    public OrderRepo(){
        try {
            ctx=new InitialContext();
            ds= (DataSource) ctx.lookup("java:comp/env/jdbc/shoppingDB") ;
        } catch (NamingException e) {
            System.out.println("Initial Error: "+e);
        }
    }

    public boolean addOrder(Order order){
        try {
            con= ds.getConnection();
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
