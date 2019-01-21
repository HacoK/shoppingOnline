package edu.nju.shoppingOnline.servlet;

import edu.nju.shoppingOnline.service.ShoppingCart;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/Cart/*")
public class CartServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session=req.getSession();
        ShoppingCart cart=(ShoppingCart)session.getAttribute("cart");
        PrintWriter out = res.getWriter();
        out.println("{");
        out.println("\"code\": 0,");
        out.println("\"msg\": \"\",");
        if(cart==null){
            try {
                session.setAttribute("cart", new ShoppingCart());
                cart=(ShoppingCart)session.getAttribute("cart");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            out.println("\"count\": "+0+",");
            out.println("\"data\": [");
        }else{
            int count=cart.getItemCount();
            out.println("\"count\": "+count+",");
            out.println("\"data\": [");
            ArrayList<Integer> keys=cart.getKeys();
            Integer key;
            for(int i=0;i<count;i++)
            {
                key=keys.get(i);
                out.println ("{");
                out.println ("\"gid\": "+key+",");
                out.println ("\"name\": \""+cart.getName(key)+"\",");
                out.println ("\"price\": "+cart.getPrice(key)+",");
                out.println ("\"quantity\": "+cart.getQuantity(key)+",");
                out.println ("\"amount\": "+cart.getPrice(key)*cart.getQuantity(key));
                if(i!=count-1)
                    out.println ("},");
                else
                    out.println ("}");
            }
        }
        res.getWriter().println("]\n" + "}");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String uri=req.getRequestURI();
        if(uri.equals("/Cart/addItem")){
            HttpSession session=req.getSession();
            ShoppingCart cart=(ShoppingCart)session.getAttribute("cart");
            if(cart==null){
                try {
                    session.setAttribute("cart", new ShoppingCart());
                    cart=(ShoppingCart)session.getAttribute("cart");
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
            cart.addGoods(Integer.parseInt(req.getParameter("gid")),req.getParameter("name"),Double.parseDouble(req.getParameter("price")),Integer.parseInt(req.getParameter("quantity")));
            res.getWriter().println("Added to shopping cart successfully!");
        }
        else if(uri.equals("/Cart/delItem")){
            HttpSession session=req.getSession();
            ShoppingCart cart=(ShoppingCart)session.getAttribute("cart");
            String gid=req.getParameter("gid");
            if(gid==null){
                cart.clear();
                res.getWriter().println("Clear shopping cart successfully!");
            }
            else{
                cart.removeItem(Integer.parseInt(gid));
                res.getWriter().println("Remove shopping item successfully!");
            }
        }
        else if(uri.equals("/Cart/updateItem")){
            HttpSession session=req.getSession();
            ShoppingCart cart=(ShoppingCart)session.getAttribute("cart");
            String gid=req.getParameter("gid");
            String quantity=req.getParameter("quantity");
            cart.updateItem(Integer.parseInt(gid),Integer.parseInt(quantity));
            res.getWriter().println("Update shopping item successfully!");
        }
    }
}
