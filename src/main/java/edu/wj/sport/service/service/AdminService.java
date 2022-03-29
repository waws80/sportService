package edu.wj.sport.service.service;

import edu.wj.sport.service.bean.AdminBean;
import edu.wj.sport.service.mapper.AdminMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;


    public boolean add(String id, String userName, String pwd){
        return adminMapper.add(id, userName, pwd) > 0;
    }

    public AdminBean findById(String id){
        return adminMapper.findById(id);
    }


    public AdminBean findByName(String userName){
        return adminMapper.findByName(userName);
    }


    public List<AdminBean> findAll(){
        return adminMapper.findAll();
    }
}
