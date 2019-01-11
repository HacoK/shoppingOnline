package edu.nju.shoppingOnline.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Tourist")
public class TouristServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session=req.getSession();
        session.setAttribute("userType","Tourist");
        res.sendRedirect("/Home");
    }
}
