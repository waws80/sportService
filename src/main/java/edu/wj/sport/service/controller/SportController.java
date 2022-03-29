//package edu.wj.sport.service.controller;
//
//import com.keep.custom.bean.SportBean;
//import com.keep.custom.bean.SportType;
//import com.keep.custom.bean.UserBean;
//import com.keep.custom.service.SportService;
//import com.keep.custom.service.UserService;
//import com.keep.custom.utils.DateUtils;
//import com.keep.custom.utils.ResponseBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//import java.util.function.Predicate;
//import java.util.function.ToDoubleFunction;
//import java.util.stream.DoubleStream;
//
//@RequestMapping("sport")
//@RestController
//public class SportController {
//
//    @Autowired
//    private HttpServletRequest httpServletRequest;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SportService sportService;
//
//    @PostMapping("add")
//    public ResponseBean addData(){
//        SportType type = SportType.valueOf(httpServletRequest.getParameter("sportType"));
//        double distance = Double.parseDouble(httpServletRequest.getParameter("distance"));
//        int power = Integer.parseInt(httpServletRequest.getParameter("power"));
//        long timeLen = Long.parseLong(httpServletRequest.getParameter("timeLen"));
//        int userId = Integer.parseInt(httpServletRequest.getParameter("userId"));
//
//        UserBean userBean = userService.findById(userId);
//        if (userBean == null){
//            return ResponseBean.failed("当前用户不存在");
//        }
//        boolean result = sportService.add(new SportBean(type.getType(), distance, power, timeLen, userId));
//
//        if (result){
//            return ResponseBean.success("");
//        }else {
//            return ResponseBean.failed("添加数据失败");
//        }
//    }
//
//
//    @GetMapping("search")
//    public ResponseBean search(){
//        String userId = httpServletRequest.getParameter("userId");
//        String type = httpServletRequest.getParameter("sportType");
//        if (userId == null){
//            return ResponseBean.failed("参数错误");
//        }
//        int user = Integer.parseInt(userId);
//        UserBean userBean = userService.findById(user);
//        if (userBean == null){
//            return ResponseBean.failed("当前用户不存在");
//        }
//        List<SportBean> sportBeans = sportService.findByUserId(user);
//        sportBeans.sort(new Comparator<SportBean>() {
//            @Override
//            public int compare(SportBean o1, SportBean o2) {
//                return (int) (o2.getDateTime() - o1.getDateTime());
//            }
//        });
//        if (type == null){
//            return ResponseBean.success(sportBeans);
//        }
//        try {
//            SportType sportType = SportType.valueOf(type);
//
//            if (sportType == SportType.WALKING){
//                return ResponseBean.success(sportBeans.stream().filter(new Predicate<SportBean>() {
//                    @Override
//                    public boolean test(SportBean bean) {
//                        return bean.getSportType() == SportType.WALKING;
//                    }
//                }).distinct().toArray());
//            }else if (sportType == SportType.RUNNING){
//                return ResponseBean.success(sportBeans.stream().filter(new Predicate<SportBean>() {
//                    @Override
//                    public boolean test(SportBean bean) {
//                        return bean.getSportType() == SportType.RUNNING;
//                    }
//                }).distinct().toArray());
//            }else if (sportType == SportType.CYCLING){
//                return ResponseBean.success(sportBeans.stream().filter(new Predicate<SportBean>() {
//                    @Override
//                    public boolean test(SportBean bean) {
//                        return bean.getSportType() == SportType.CYCLING;
//                    }
//                }).distinct().toArray());
//            }
//        }catch (Exception e){
//            return ResponseBean.failed("参数错误");
//        }
//
//        return ResponseBean.failed("请求失败");
//
//    }
//
//
//
//    @GetMapping("overview")
//    public ResponseBean overview(){
//        String userId = httpServletRequest.getParameter("userId");
//        if (userId == null){
//            return ResponseBean.failed("参数错误");
//        }
//        int user = Integer.parseInt(userId);
//        UserBean userBean = userService.findById(user);
//        if (userBean == null){
//            return ResponseBean.failed("当前用户不存在");
//        }
//
//        List<SportBean> sportBeanList = sportService.findByUserId(user);
//        HashMap<String, String> body = new HashMap<>();
//        if (sportBeanList.isEmpty()){
//            body.put("time", "- 分钟");
//            body.put("qk", "总消耗 - 千卡");
//
//        }else {
//            long duration = 0;
//            int qk = 0;
//            for (SportBean sportBean : sportBeanList) {
//                duration += sportBean.getTimeLen();
//                qk += sportBean.getPower();
//            }
//            body.put("time", DateUtils.formatMin(duration) + " 分钟");
//            body.put("qk", "总消耗 " + qk + " 千卡");
//        }
//
//        return ResponseBean.success(body);
//
//    }
//
//
//
//    @GetMapping("detail")
//    public ResponseBean sportDetail(){
//        String userId = httpServletRequest.getParameter("userId");
//        if (userId == null){
//            return ResponseBean.failed("参数错误");
//        }
//        int user = Integer.parseInt(userId);
//        UserBean userBean = userService.findById(user);
//        if (userBean == null){
//            return ResponseBean.failed("当前用户不存在");
//        }
//        List<SportBean> sportBeanList = sportService.findByUserId(user);
//        HashMap<String, Object> body = new HashMap<>();
//        ArrayList<SportBean> walkingList = new ArrayList<>();
//        ArrayList<SportBean> runningList = new ArrayList<>();
//        ArrayList<SportBean> cyclingList = new ArrayList<>();
//        if (!sportBeanList.isEmpty()){
//            for (SportBean sportBean : sportBeanList) {
//                if (sportBean.getSportType() == SportType.WALKING){
//                    walkingList.add(sportBean);
//                }else if (sportBean.getSportType() == SportType.RUNNING){
//                    runningList.add(sportBean);
//                }else if (sportBean.getSportType() == SportType.CYCLING){
//                    cyclingList.add(sportBean);
//                }
//            }
//        }
//
//        DoubleStream walkingStream = walkingList.stream().mapToDouble(new ToDoubleFunction<SportBean>() {
//            @Override
//            public double applyAsDouble(SportBean value) {
//                return value.getDistance();
//            }
//        });
//
//        DoubleStream walkingStream1 = walkingList.stream().mapToDouble(new ToDoubleFunction<SportBean>() {
//            @Override
//            public double applyAsDouble(SportBean value) {
//                return value.getDistance();
//            }
//        });
//
//        if (walkingList.isEmpty()){
//            body.put("walking_total", "总距离:0.0公里");
//            body.put("walking_max", "最远距离:0.0公里");
//        }else {
//            body.put("walking_total", "总距离:" + DateUtils.formatNumber(walkingStream.sum() / 1000) + "公里");
//            body.put("walking_max", "最远距离:" + DateUtils.formatNumber(walkingStream1.distinct().max().getAsDouble() / 1000) + "公里");
//        }
//
//        DoubleStream runningStream = runningList.stream().mapToDouble(new ToDoubleFunction<SportBean>() {
//            @Override
//            public double applyAsDouble(SportBean value) {
//                return value.getDistance();
//            }
//        });
//        DoubleStream runningStream1 = runningList.stream().mapToDouble(new ToDoubleFunction<SportBean>() {
//            @Override
//            public double applyAsDouble(SportBean value) {
//                return value.getDistance();
//            }
//        });
//        if (runningList.isEmpty()){
//            body.put("running_total", "总距离:0.0公里");
//            body.put("running_max", "最远距离:0.0公里");
//        }else {
//            body.put("running_total", "总距离:" + DateUtils.formatNumber(runningStream.sum() / 1000) + "公里");
//            body.put("running_max", "最远距离:" + DateUtils.formatNumber(runningStream1.distinct().max().getAsDouble() / 1000) + "公里");
//        }
//
//        DoubleStream cyclingStream = cyclingList.stream().mapToDouble(new ToDoubleFunction<SportBean>() {
//            @Override
//            public double applyAsDouble(SportBean value) {
//                return value.getDistance();
//            }
//        });
//        DoubleStream cyclingStream1 = cyclingList.stream().mapToDouble(new ToDoubleFunction<SportBean>() {
//            @Override
//            public double applyAsDouble(SportBean value) {
//                return value.getDistance();
//            }
//        });
//        if (cyclingList.isEmpty()){
//            body.put("cycling_total", "总距离:0.0公里");
//            body.put("cycling_max", "最远距离:0.0公里");
//        }else {
//            body.put("cycling_total", "总距离:" + DateUtils.formatNumber(cyclingStream.sum() / 1000) + "公里");
//            body.put("cycling_max", "最远距离:" + DateUtils.formatNumber(cyclingStream1.distinct().max().getAsDouble() / 1000) + "公里");
//        }
//
//        return ResponseBean.success(body);
//    }
//
//
//    @GetMapping("weekData")
//    public ResponseBean getWeekOfYearSportData(){
//        String userId = httpServletRequest.getParameter("userId");
//        if (userId == null){
//            return ResponseBean.failed("参数错误");
//        }
//        int user = Integer.parseInt(userId);
//        UserBean userBean = userService.findById(user);
//        if (userBean == null){
//            return ResponseBean.failed("当前用户不存在");
//        }
//        List<SportBean> sportBeanList = sportService.findByUserId(user);
//
//        int weekOfYear = DateUtils.getWeekNumber(System.currentTimeMillis());
//        List<SportBean> weekData = new ArrayList<>();
//        for (SportBean sportBean : sportBeanList) {
//            if (DateUtils.getWeekNumber(sportBean.getDateTime()) == weekOfYear){
//                weekData.add(sportBean);
//            }
//        }
//        HashMap<String, Object> body = new HashMap<>();
//        body.put("one", 0L);
//        body.put("two", 0L);
//        body.put("three", 0L);
//        body.put("four", 0L);
//        body.put("five", 0L);
//        body.put("six", 0L);
//        body.put("seven", 0L);
//        int todayWeek = DateUtils.getWeekOfDate(System.currentTimeMillis());
//        String todayStr = "";
//        if (todayWeek == 0){
//            todayStr = "seven";
//        }else if (todayWeek == 1){
//            todayStr = "one";
//        }
//        else if (todayWeek == 2){
//            todayStr = "two";
//        }
//        else if (todayWeek == 3){
//            todayStr = "three";
//        }
//        else if (todayWeek == 4){
//            todayStr = "four";
//        }
//        else if (todayWeek == 5){
//            todayStr = "five";
//        }else if (todayWeek == 6){
//            todayStr = "six";
//        }
//        body.put("today", todayStr);
//        for (SportBean wd : weekData) {
//            int weekOfDay = DateUtils.getWeekOfDate(wd.getDateTime());
//            if (weekOfDay == 0){
//                body.put("seven", (Long.parseLong(body.get("seven").toString()) + wd.getTimeLen()));
//            }else if (weekOfDay == 1){
//                body.put("one", (Long.parseLong(body.get("one").toString()) + wd.getTimeLen()));
//            }else if (weekOfDay == 2){
//                body.put("two", (Long.parseLong(body.get("two").toString()) + wd.getTimeLen()));
//            }else if (weekOfDay == 3){
//                body.put("three", (Long.parseLong(body.get("three").toString()) + wd.getTimeLen()));
//            }else if (weekOfDay == 4){
//                body.put("four", (Long.parseLong(body.get("four").toString()) + wd.getTimeLen()));
//            }else if (weekOfDay == 5){
//                body.put("five", (Long.parseLong(body.get("five").toString()) + wd.getTimeLen()));
//            }else if (weekOfDay == 6){
//                body.put("six", (Long.parseLong(body.get("six").toString()) + wd.getTimeLen()));
//            }
//        }
//        return ResponseBean.success(body);
//    }
//
//
//
//
//    @GetMapping("sportTypeOverview")
//    public ResponseBean getSportTypeOverview(){
//        String userId = httpServletRequest.getParameter("userId");
//        String type = httpServletRequest.getParameter("sportType");
//        if (userId == null || type == null){
//            return ResponseBean.failed("参数错误");
//        }
//        int user = Integer.parseInt(userId);
//        UserBean userBean = userService.findById(user);
//        if (userBean == null){
//            return ResponseBean.failed("当前用户不存在");
//        }
//        List<SportBean> sportBeanList = sportService.findByUserId(user);
//
//        SportType sportType = SportType.valueOf(type);
//
//        List<SportBean> targetList = new ArrayList<>();
//        for (SportBean sportBean : sportBeanList) {
//            if (sportBean.getSportType() == sportType){
//                targetList.add(sportBean);
//            }
//        }
//
//        HashMap<String, Object> body = new HashMap<>();
//
//        if (targetList.isEmpty()){
//            body.put("empty", true);
//        }else {
//            body.put("empty", false);
//            HashMap<String, Object> data = new HashMap<>();
//
//
//            Date now = new Date();
//            Calendar nowCalendar = Calendar.getInstance();
//            nowCalendar.setTime(now);
//            int year = nowCalendar.get(Calendar.YEAR);
//            int month = nowCalendar.get(Calendar.MONTH);
//            int week = nowCalendar.get(Calendar.WEEK_OF_YEAR);
//            int day = nowCalendar.get(Calendar.DAY_OF_YEAR);
//
//            //计算总
//            double totalDistance = 0.0;
//            long totalTimeLen = 0;
//            int totalQk = 0;
//            for (SportBean sportBean : targetList) {
//                totalDistance += sportBean.getDistance();
//                totalTimeLen += sportBean.getTimeLen();
//                totalQk += sportBean.getPower();
//            }
//
//            data.put("totalDistance", "距离：" + DateUtils.formatNumber(totalDistance / 1000) + "公里");
//            data.put("totalTimeLen", "时长：" + DateUtils.formatDate(totalTimeLen));
//            data.put("totalQk", "消耗：" + totalQk + "千卡");
//
//            //计算年
//            double yearDistance = 0.0;
//            long yearTimeLen = 0;
//            int yearQk = 0;
//            for (SportBean sportBean : targetList) {
//                Calendar yearCalendar = Calendar.getInstance();
//                yearCalendar.setTime(new Date(sportBean.getDateTime()));
//
//                if (year == yearCalendar.get(Calendar.YEAR)){
//                    yearDistance += sportBean.getDistance();
//                    yearTimeLen += sportBean.getTimeLen();
//                    yearQk += sportBean.getPower();
//                }
//
//            }
//
//            data.put("yearDistance", "距离：" + DateUtils.formatNumber(yearDistance / 1000) + "公里");
//            data.put("yearTimeLen", "时长：" + DateUtils.formatDate(yearTimeLen));
//            data.put("yearQk", "消耗：" + yearQk + "千卡");
//
//
//            //计算月
//            double monthDistance = 0.0;
//            long monthTimeLen = 0;
//            int monthQk = 0;
//            for (SportBean sportBean : targetList) {
//                Calendar monthCalendar = Calendar.getInstance();
//                monthCalendar.setTime(new Date(sportBean.getDateTime()));
//
//                if (month == monthCalendar.get(Calendar.MONTH)){
//                    monthDistance += sportBean.getDistance();
//                    monthTimeLen += sportBean.getTimeLen();
//                    monthQk += sportBean.getPower();
//                }
//
//            }
//
//            data.put("monthDistance", "距离：" + DateUtils.formatNumber(monthDistance / 1000) + "公里");
//            data.put("monthTimeLen", "时长：" + DateUtils.formatDate(monthTimeLen));
//            data.put("monthQk", "消耗：" + monthQk + "千卡");
//
//
//            //计算周
//            double weekDistance = 0.0;
//            long weekTimeLen = 0;
//            int weekQk = 0;
//            for (SportBean sportBean : targetList) {
//                Calendar monthCalendar = Calendar.getInstance();
//                monthCalendar.setTime(new Date(sportBean.getDateTime()));
//
//                if (week == monthCalendar.get(Calendar.WEEK_OF_YEAR)){
//                    weekDistance += sportBean.getDistance();
//                    weekTimeLen += sportBean.getTimeLen();
//                    weekQk += sportBean.getPower();
//                }
//
//            }
//
//            data.put("weekDistance", "距离：" + DateUtils.formatNumber(weekDistance / 1000) + "公里");
//            data.put("weekTimeLen", "时长：" + DateUtils.formatDate(weekTimeLen));
//            data.put("weekQk", "消耗：" + weekQk + "千卡");
//
//
//            //计算日
//            double dayDistance = 0.0;
//            long dayTimeLen = 0;
//            int dayQk = 0;
//            for (SportBean sportBean : targetList) {
//                Calendar monthCalendar = Calendar.getInstance();
//                monthCalendar.setTime(new Date(sportBean.getDateTime()));
//
//                if (day == monthCalendar.get(Calendar.DAY_OF_YEAR)){
//                    dayDistance += sportBean.getDistance();
//                    dayTimeLen += sportBean.getTimeLen();
//                    dayQk += sportBean.getPower();
//                }
//
//            }
//
//            data.put("dayDistance", "距离：" + DateUtils.formatNumber(dayDistance / 1000) + "公里");
//            data.put("dayTimeLen", "时长：" + DateUtils.formatDate(dayTimeLen));
//            data.put("dayQk", "消耗：" + dayQk + "千卡");
//
//            body.put("data", data);
//        }
//
//        return ResponseBean.success(body);
//
//    }
//
//
//}
