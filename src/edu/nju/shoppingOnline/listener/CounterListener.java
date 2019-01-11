package edu.nju.shoppingOnline.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class CounterListener implements ServletContextListener,HttpSessionListener ,HttpSessionAttributeListener {
    public void contextInitialized(ServletContextEvent cse) {
        ServletContext servletContext=cse.getServletContext();
        servletContext.setAttribute("loginCounter", 0);
        servletContext.setAttribute("touristCounter", 0);
        servletContext.setAttribute("totalCounter", 0);
        System.out.println("Application initialized");
    }
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println ("Application shut down");
    }
    public void sessionCreated(HttpSessionEvent se){
        ServletContext servletContext=se.getSession().getServletContext();
        int total=(Integer)servletContext.getAttribute("totalCounter")+1;
        servletContext.setAttribute("totalCounter",total);
    }

    public void sessionDestroyed(HttpSessionEvent se){
        String userType=(String)se.getSession().getAttribute("userType");
        ServletContext ctx=se.getSession().getServletContext();
        int total=(Integer)ctx.getAttribute("totalCounter")-1;
        ctx.setAttribute("totalCounter",total);
        if(userType.equals("Login")){
            ctx.setAttribute("loginCounter",(Integer)ctx.getAttribute("loginCounter")-1);
        }else{
            ctx.setAttribute("touristCounter",(Integer)ctx.getAttribute("touristCounter")-1);
        }
    };

    public void attributeAdded(HttpSessionBindingEvent sbe) {
        if(sbe.getName().equals("userType")){
            ServletContext ctx=sbe.getSession().getServletContext();
            String userType=(String) sbe.getValue();
            if(userType.equals("Login")){
                ctx.setAttribute("loginCounter",(Integer)ctx.getAttribute("loginCounter")+1);
            }else{
                ctx.setAttribute("touristCounter",(Integer)ctx.getAttribute("touristCounter")+1);
            }
        }
    }
}
