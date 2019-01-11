package edu.nju.shoppingOnline.servlet;

import edu.nju.shoppingOnline.domain.User;
import edu.nju.shoppingOnline.repository.RepoFactory;
import edu.nju.shoppingOnline.repository.UserRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {

    public void init(){
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            req.getRequestDispatcher("register.html").include(req,res);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        User user=new User();
        user.setAccount(req.getParameter("account"));
        user.setPasswd(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        UserRepo userRepo= (UserRepo) RepoFactory.getFactory().getInstance("UserRepo");
        if(userRepo.addUser(user)){
            res.sendRedirect("Login");
        }else{
            res.getWriter().println("<script language='javascript'>alert('Unexpected error:Your account registration failed!');</script>");
        }
        try {
            req.getRequestDispatcher("register.html").include(req,res);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
