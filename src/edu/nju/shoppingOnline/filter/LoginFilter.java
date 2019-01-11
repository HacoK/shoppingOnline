package edu.nju.shoppingOnline.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns="/*", filterName= "LoginFilter")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session=req.getSession(false);
        String path=req.getRequestURI();
        if(session!=null||path.endsWith("Counter")||path.endsWith("Login")||path.endsWith("Register")||path.endsWith("Tourist")||path.endsWith(".css")||path.endsWith(".js")||path.endsWith(".map")||path.endsWith(".jpg")||path.endsWith(".png")){
            chain.doFilter(req,res);
            return;
        }
        res.sendRedirect("/Login");
    }
}
