package edu.wj.sport.service.controller;

import edu.wj.sport.service.bean.UserBean;
import edu.wj.sport.service.interceptor.IgnoreLogin;
import edu.wj.sport.service.memory.MemoryCache;
import edu.wj.sport.service.service.UserService;
import edu.wj.sport.service.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息增删改查
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @IgnoreLogin
    @GetMapping("index")
    public SportResponse index(){
        return SportResponse.success(CollectUtils.mapOf(Pair.of("week", DateUtils.getWeekNumber(System.currentTimeMillis()))));
    }


    @IgnoreLogin
    @PostMapping("register")
    public SportResponse registerUser(){
        String phone = httpServletRequest.getParameter("phone");
        String nickname = httpServletRequest.getParameter("nickname");
        String pwd = httpServletRequest.getParameter("pwd");
        String sex = httpServletRequest.getParameter("sex");
        String birthDate = httpServletRequest.getParameter("birthDate");
        String avatar = httpServletRequest.getParameter("avatar");
        String deviceInfo = httpServletRequest.getHeader("deviceInfo");

        if (phone.isEmpty() || pwd.isEmpty() || nickname.isEmpty() || sex.isEmpty()
                || birthDate.isEmpty() || avatar.isEmpty() || deviceInfo.isEmpty()){
            return SportResponse.paramsError("参数不能为空");
        }

        UserBean sel = userService.findByPhone(phone);
        if (sel == null){
            boolean success = userService.addUser(SecurityUtils.generateUserId(),
                    nickname, SecurityUtils.base64(phone), SecurityUtils.md5(pwd),
                    Integer.parseInt(sex),Long.parseLong(birthDate), avatar);
            if (success){
                UserBean result = userService.findByPhone(SecurityUtils.base64(phone));
                result.setPwd("");
                result.setPhoneNumber(phone);
                MemoryCache.putCache(result.getId(), deviceInfo);
                return SportResponse.success(result);
            }else {
                return SportResponse.serviceError();
            }
        }
        return SportResponse.paramsError("当前用户已存在");
    }



    @IgnoreLogin
    @PostMapping("login")
    public SportResponse login(){
        String phone = httpServletRequest.getParameter("phone");
        String pwd = httpServletRequest.getParameter("pwd");
        String deviceInfo = httpServletRequest.getHeader("deviceInfo");
        if (phone.isEmpty() || pwd.isEmpty() || deviceInfo.isEmpty()){
            return SportResponse.paramsError("参数不能为空");
        }
        UserBean sel = userService.findByPhone(SecurityUtils.base64(phone));
        if (sel == null){
            return SportResponse.paramsError("当前用户已存在");
        }
        if (!sel.getPwd().equals(SecurityUtils.md5(pwd))){
            return SportResponse.paramsError("密码错误");
        }
        sel.setPwd("");
        sel.setPhoneNumber(SecurityUtils.decoderBase64(sel.getPhoneNumber()));
        MemoryCache.putCache(sel.getId(), deviceInfo);
        return SportResponse.success(sel);
    }


    @PutMapping("status")
    public SportResponse updateStatus(){
        String status = httpServletRequest.getParameter("status");
        String userId = httpServletRequest.getHeader("id");
        if (status.isEmpty()){
            return SportResponse.paramsError("参数不能为空");
        }
        if (userService.updateStatus(userId, Integer.parseInt(status))){
            return SportResponse.success("");
        }
        return SportResponse.serviceError();
    }




    @PutMapping("info")
    public SportResponse updateInfo(){
        String nickName = httpServletRequest.getParameter("nickname");
        String pwd = httpServletRequest.getParameter("pwd");
        String avatar = httpServletRequest.getParameter("avatar");

        String userId = httpServletRequest.getHeader("id");

        UserBean sel = userService.findById(userId);

        if (!nickName.isEmpty()){
            sel.setNickname(nickName);
        }
        if (!pwd.isEmpty()){
            sel.setPwd(SecurityUtils.md5(pwd));
        }
        if (!avatar.isEmpty()){
            sel.setAvatar(avatar);
        }

        if (userService.updateInfo(userId, sel.getNickname(), sel.getPwd(), sel.getAvatar())){
            sel.setPwd("");
            sel.setPhoneNumber(SecurityUtils.decoderBase64(sel.getPhoneNumber()));
            return SportResponse.success(sel);
        }
        return SportResponse.serviceError();
    }

    @GetMapping("allUser")
    public SportResponse getAllUser(){
        return SportResponse.success(userService.findAll());
    }


}
