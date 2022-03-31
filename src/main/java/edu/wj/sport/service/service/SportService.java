package edu.wj.sport.service.service;

import edu.wj.sport.service.bean.SportBean;
import edu.wj.sport.service.mapper.SportDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportService {

    @Autowired
    private SportDataMapper sportDataMapper;

    public boolean add(SportBean bean){
        return sportDataMapper.addData(bean.getId(), bean.getUserId(),
                bean.getMileage(), bean.getDuration(), bean.getPoints(), bean.getCreateTime()) > 0;
    }


    public boolean deleteOne(String id){
        return sportDataMapper.deleteOne(id) > 0;
    }


    public List<SportBean> findByUserId(String userId){
        return sportDataMapper.findByUserId(userId);
    }

}
