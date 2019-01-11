package edu.nju.shoppingOnline.servlet;

import edu.nju.shoppingOnline.domain.User;
import edu.nju.shoppingOnline.repository.RepoFactory;
import edu.nju.shoppingOnline.repository.UserRepo;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns={"/Login/*","/Logout","/Leave","/Counter"})
public class LoginServlet extends HttpServlet {

    public void init(){
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        UserRepo userRepo=(UserRepo)RepoFactory.getFactory().getInstance("UserRepo");
        User user=userRepo.getUser(req.getParameter("account"));
        if(user!=null){
            String passwd=user.getPasswd();
            if(passwd.equals(req.getParameter("password"))){
                HttpSession session=req.getSession();
                session.setAttribute("userType","Login");
                session.setAttribute("userID",req.getParameter("account"));
                res.sendRedirect("/Home");
            }else{
                res.getWriter().println("<script language='javascript'>alert('Error:Password does not match!');</script>");
                try {
                    req.getRequestDispatcher("index.html").include(req,res);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            }
        }else{
            res.getWriter().println("<script language='javascript'>alert('Error:Account does not exist!');</script>");
            try {
                req.getRequestDispatcher("index.html").include(req,res);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if(req.getRequestURI().equals("/Login/Info")){
            HttpSession session=req.getSession();
            if(session.getAttribute("userType").equals("Tourist")){
                res.getWriter().println("Tourist don\'t have privilege to check balance!");
                return;
            }
            String account=(String)session.getAttribute("userID");
            UserRepo userRepo=(UserRepo) RepoFactory.getFactory().getInstance("UserRepo");
            User user=userRepo.getUser(account);
            res.getWriter().println("Hello "+account+"!<br>");
            res.getWriter().println("Your balance is &yen;"+user.getBalance());
        }else if(req.getRequestURI().equals("/Logout")){
            HttpSession session=req.getSession();
            session.invalidate();
            session = null;
        }else if(req.getRequestURI().equals("/Login")){
            try {
                req.getRequestDispatcher("index.html").include(req,res);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }else if(req.getRequestURI().equals("/Leave")){
            req.getSession().invalidate();
        }else if(req.getRequestURI().equals("/Counter")){
            ServletContext ctx=req.getServletContext();
            int login=(Integer)ctx.getAttribute("loginCounter");
            int tourist=(Integer)ctx.getAttribute("touristCounter");
            int total=(Integer)ctx.getAttribute("totalCounter");
            res.setContentType("text/html;charset=utf-8");
            PrintWriter out=res.getWriter();
            out.println("<h1>统计在线人数</h1>");
            out.println("<hr>");
            out.println("<p>总人数："+total+"</p>");
            out.println("<p>已登录人数："+login+"</p>");
            out.println("<p>游客人数："+tourist+"</p>");
        }
    }
}
