package edu.wj.sport.service.interceptor;

import edu.wj.sport.service.bean.AdminBean;
import edu.wj.sport.service.bean.UserBean;
import edu.wj.sport.service.memory.MemoryCache;
import edu.wj.sport.service.service.AdminService;
import edu.wj.sport.service.service.UserService;
import edu.wj.sport.service.utils.CollectUtils;
import edu.wj.sport.service.utils.GsonUtils;
import edu.wj.sport.service.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        if (((HandlerMethod) handler).getBeanType().getAnnotation(IgnoreLogin.class) != null){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        if (((HandlerMethod) handler).getMethod().getAnnotation(IgnoreLogin.class) != null){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        if (request.getServletPath().equals("/error")){
            return true;
        }


        String id = request.getHeader("id");
        String deviceInfo = request.getHeader("deviceInfo");

        if (id == null || deviceInfo == null){
            buildBadResult(response);
            return false;
        }
        if (id.isEmpty() || deviceInfo.isEmpty()){
            buildBadResult(response);
            return false;
        }

        UserBean sel = userService.findById(id);
        AdminBean adminBean = adminService.findById(id);
        if (adminBean != null && sel == null){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        if (sel == null){
            buildBadResult(response);
            return false;
        }

        if (sel.getStatus() == 1){
            buildBlackResult(response);
            return false;
        }


        if (!MemoryCache.check(id, deviceInfo)){
            buildUnAuthResult(response);
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    private void buildBadResult(HttpServletResponse response) throws IOException {
        buildResult(response, HttpStatus.BAD_REQUEST, "非法请求");
    }

    private void buildUnAuthResult(HttpServletResponse response) throws IOException {
        buildResult(response, HttpStatus.UNAUTHORIZED, "登录状态失效");
    }

    private void buildBlackResult(HttpServletResponse response) throws IOException {
        buildResult(response, HttpStatus.FORBIDDEN, "当前用户已被拉黑...");
    }

    private void buildResult(HttpServletResponse response, HttpStatus status, String msg) throws IOException {
        response.setStatus(status.value());
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(msg);
    }
}
