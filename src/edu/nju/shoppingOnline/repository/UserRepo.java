package edu.nju.shoppingOnline.repository;

import edu.nju.shoppingOnline.model.User;
import org.springframework.stereotype.Repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserRepo {
    Context ctx;
    DataSource ds;
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;
    int opNum;
    public UserRepo(){
        try {
            ctx=new InitialContext();
            ds= (DataSource) ctx.lookup("java:comp/env/jdbc/shoppingDB") ;
        } catch (NamingException e) {
            System.out.println("Initial Error: "+e);
        }
    }

    public boolean addUser(User user){
        try {
            con= ds.getConnection();
            pstmt = con.prepareStatement("INSERT INTO user (account,passwd,email,balance) VALUES(?,?,?,0)");
            pstmt.setString(1,user.getAccount());
            pstmt.setString(2,user.getPasswd());
            pstmt.setString(3,user.getEmail());
            opNum= pstmt.executeUpdate();
            pstmt.close();
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

    public User getUser(String account){
        User user=new User();
        try {
            con= ds.getConnection();
            stmt = con.createStatement();
            rs=stmt.executeQuery("SELECT * FROM user WHERE account = \'"+account+"\'");
            if(rs.next()){
                user.setAccount(account);
                user.setBalance(rs.getDouble("balance"));
                user.setEmail(rs.getString("email"));
                user.setPasswd(rs.getString("passwd"));
            }else{
                user=null;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Service[SQL] Error: "+e);
        }
        return user;
    }

    public boolean updateUser(User user){
        try {
            con= ds.getConnection();
            pstmt = con.prepareStatement("UPDATE user SET balance = ?,email = ?,passwd = ? WHERE account = ? ");
            pstmt.setDouble(1,user.getBalance());
            pstmt.setString(2,user.getEmail());
            pstmt.setString(3,user.getPasswd());
            pstmt.setString(4,user.getAccount());
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
