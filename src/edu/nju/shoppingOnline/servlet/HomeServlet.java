package edu.nju.shoppingOnline.servlet;

import edu.nju.shoppingOnline.model.Goods;
import edu.nju.shoppingOnline.repository.GoodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Component
@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
    @Autowired
    GoodsRepo goodsRepo;
    public void init(ServletConfig config){
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            req.getRequestDispatcher("homepage.html").include(req,res);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String type=req.getParameter("type");
        Integer count=goodsRepo.countGoods(type);
        PrintWriter out = res.getWriter();
        out.println("{");
        out.println("\"code\": 0,");
        out.println("\"msg\": \"\",");
        out.println("\"count\": "+count+",");
        out.println("\"data\": [");

        Integer page=Integer.parseInt(req.getParameter("page"));
        Integer limit=Integer.parseInt(req.getParameter("limit"));
        Integer start=(page-1)*limit;
        Integer end=start+limit;
        ArrayList<Goods> list=goodsRepo.listGoods(type);
        while(start<list.size()&&start<end){
            Goods goods=list.get(start);
            out.println ("{");
            out.println ("\"gid\": "+goods.getGid()+",");
            out.println ("\"name\": \""+goods.getName()+"\",");
            out.println ("\"type\": \""+goods.getType()+"\",");
            out.println ("\"price\": "+goods.getPrice()+",");
            out.println ("\"num\": "+goods.getNum());
            if(start<list.size()-1&&start<end-1)
                out.println ("},");
            else
                out.println ("}");
            start++;
        }
        res.getWriter().println("]\n" + "}");
    }
}
