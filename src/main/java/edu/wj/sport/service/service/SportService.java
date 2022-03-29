//package edu.wj.sport.service.service;
//
//import com.keep.custom.bean.SportBean;
//import com.keep.custom.mapper.SportDataMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SportService {
//
//    @Autowired
//    private SportDataMapper sportDataMapper;
//
//    public boolean add(SportBean bean){
//        return sportDataMapper.addData(bean.getType(), bean.getDistance(),
//                bean.getPower(), bean.getTimeLen(), bean.getUserId(), bean.getDateTime()) > 0;
//    }
//
//
//    public List<SportBean> findByUserId(int userId){
//        return sportDataMapper.findByUserId(userId);
//    }
//
//    public List<SportBean> findAll(){
//        return sportDataMapper.findAll();
//    }
//}
