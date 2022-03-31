package edu.wj.sport.service.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeaderEditFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response != null && response instanceof HttpServletResponse){
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, PUT, OPTIONS, DELETE");
            ((HttpServletResponse) response).setHeader("Access-Control-Max-Age", "3600");
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, id, deviceInfo");
        }
        chain.doFilter(request, response);
    }
}
