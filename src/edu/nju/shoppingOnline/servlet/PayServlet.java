package edu.nju.shoppingOnline.servlet;

import edu.nju.shoppingOnline.service.ShoppingCart;
import org.json.JSONArray;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/Pay")
public class PayServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try {
            req.getRequestDispatcher("shoppingcart.html").include(req,res);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String payList=req.getParameter("payList");
        Double payAmount=Double.parseDouble(req.getParameter("payAmount"));
        JSONArray jArray=new JSONArray(payList);
        ArrayList<Integer> gidList=new ArrayList<>();
        if(jArray.length()==0){
            res.getWriter().println("No items selected!");
            return;
        }
        for(int i=0;i<jArray.length();i++){
            gidList.add(jArray.getInt(i));
        }
        HttpSession session=req.getSession();
        if(session.getAttribute("userType").equals("Tourist")){
            res.getWriter().println("It\'s invalid for tourist to pay!");
            return;
        }
        ShoppingCart cart=(ShoppingCart)session.getAttribute("cart");
        String prompt= "Error!!!";
        try {
            prompt = cart.payItems((String)session.getAttribute("userID"),gidList,payAmount);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        res.getWriter().println(prompt);
    }
}
