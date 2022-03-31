package edu.wj.sport.service.controller;


import edu.wj.sport.service.bean.SportBean;
import edu.wj.sport.service.bean.UserBean;
import edu.wj.sport.service.service.SportService;
import edu.wj.sport.service.service.UserService;
import edu.wj.sport.service.utils.DateUtils;
import edu.wj.sport.service.utils.SecurityUtils;
import edu.wj.sport.service.utils.SportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@RequestMapping("sport")
@RestController
public class SportController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @Autowired
    private SportService sportService;

    @PostMapping("add")
    public SportResponse addData(){
        double mileage = Double.parseDouble(httpServletRequest.getParameter("mileage"));
        String points = httpServletRequest.getParameter("points");
        long duration = Long.parseLong(httpServletRequest.getParameter("duration"));
        String userId = httpServletRequest.getHeader("id");


        UserBean userBean = userService.findById(userId);

        if (userBean == null){
            return SportResponse.unAuth();
        }
        boolean result = sportService.add(new SportBean(SecurityUtils.generateSportId(), userId, mileage,
                duration, SecurityUtils.base64(points), System.currentTimeMillis()));

        if (result){
            return SportResponse.success("");
        }else {
            return SportResponse.serviceError();
        }
    }


    @GetMapping("search")
    public SportResponse search(){
        String userId = httpServletRequest.getParameter("userId");
        UserBean userBean = userService.findById(userId);
        if (userBean == null){
            return SportResponse.paramsError("当前用户不存在");
        }
        List<SportBean> sportBeans = sportService.findByUserId(userId);
        sportBeans.sort(new Comparator<SportBean>() {
            @Override
            public int compare(SportBean o1, SportBean o2) {
                return (int) (o2.getCreateTime() - o1.getCreateTime());
            }
        });
        List<SportBean> beans = sportBeans.stream().map(new Function<SportBean, SportBean>() {
            @Override
            public SportBean apply(SportBean sportBean) {
                sportBean.setPoints(SecurityUtils.decoderBase64(sportBean.getPoints()));
                return sportBean;
            }
        }).collect(Collectors.toList());
        return SportResponse.success(beans);
    }



    @GetMapping("overview")
    public SportResponse overview(){
        String userId = httpServletRequest.getParameter("userId");
        List<SportBean> sportBeanList = sportService.findByUserId(userId);
        HashMap<String, String> body = new HashMap<>();
        if (sportBeanList.isEmpty()){
            body.put("sum", "总距离 0.0 千米");
            body.put("time", "总时长 00:00:00");

        }else {
            long duration = 0;
            double sum = 0;
            for (SportBean sportBean : sportBeanList) {
                duration += sportBean.getDuration();
                sum += sportBean.getMileage();
            }
            body.put("time", DateUtils.formatTime(duration));
            body.put("sum", "总距离 " + String.format("%.1f", sum) + " 千米");
        }

        return SportResponse.success(body);

    }


}
