package edu.wj.sport.service.controller;

import edu.wj.sport.service.bean.AdminBean;
import edu.wj.sport.service.interceptor.IgnoreLogin;
import edu.wj.sport.service.memory.MemoryCache;
import edu.wj.sport.service.service.AdminService;
import edu.wj.sport.service.utils.CollectUtils;
import edu.wj.sport.service.utils.Pair;
import edu.wj.sport.service.utils.SecurityUtils;
import edu.wj.sport.service.utils.SportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private HttpServletRequest httpServletRequest;


    @Autowired
    private AdminService adminService;

    @IgnoreLogin
    @GetMapping("checkStatus")
    public SportResponse isInit(){
        if (adminService.findAll().isEmpty()){
            return SportResponse.success(CollectUtils.mapOf(Pair.of("status", 1)));
        }else {
            return SportResponse.success(CollectUtils.mapOf(Pair.of("status", 0)));
        }
    }

    @IgnoreLogin
    @PostMapping("register")
    public SportResponse register(){
        String userName = httpServletRequest.getParameter("username");
        String pwd = httpServletRequest.getParameter("pwd");

        if (userName.isEmpty() || pwd.isEmpty()){
            return SportResponse.paramsError("参数错误");
        }
        if (adminService.findAll().isEmpty()){
            boolean result = adminService.add(SecurityUtils.generateUserId(), SecurityUtils.md5(userName),SecurityUtils.md5(pwd));
            if (result){
                return SportResponse.success("");
            }else {
                return SportResponse.paramsError("参数错误");
            }
        }else {
            return SportResponse.paramsError("错误的请求");
        }
    }


    @IgnoreLogin
    @PostMapping("login")
    public SportResponse login(){
        String userName = httpServletRequest.getParameter("username");
        String pwd = httpServletRequest.getParameter("pwd");
        String deviceInfo = httpServletRequest.getHeader("deviceInfo");

        if (userName.isEmpty() || pwd.isEmpty() || deviceInfo.isEmpty()){
            return SportResponse.paramsError("参数错误");
        }
        AdminBean sel = adminService.findByName(SecurityUtils.md5(userName));
        if (sel == null){
            return SportResponse.paramsError("参数错误");
        }
        if (sel.getPwd().equals(SecurityUtils.md5(pwd))){
            MemoryCache.putCache(sel.getId(), deviceInfo);
            return SportResponse.success(CollectUtils.mapOf(Pair.of("id", sel.getId())));
        }
        return SportResponse.paramsError("参数错误");
    }


}
