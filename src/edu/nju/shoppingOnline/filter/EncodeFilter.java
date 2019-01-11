package edu.nju.shoppingOnline.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns="/*", filterName= "EncodeFilter")
public class EncodeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        request.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html;charset=utf-8");
        chain.doFilter(req,res);
    }
}
