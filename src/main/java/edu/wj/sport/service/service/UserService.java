package edu.wj.sport.service.service;

import edu.wj.sport.service.bean.UserBean;
import edu.wj.sport.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean addUser(String id, String nickName, String phoneNumber, String pwd, int sex, long birthDate, String avatar){
        int result = userMapper.addUser(id, nickName, phoneNumber, pwd, sex, birthDate, avatar, System.currentTimeMillis(), 0);
        return result > 0;
    }

    public boolean updateStatus(String id, int status){
        return userMapper.updateStatus(id, status) > 0;
    }

    public boolean updateInfo(String id, String nickName, String pwd, String avatar){
        return userMapper.updateInfo(id, nickName, pwd, avatar) > 0;
    }


    public UserBean findByPhone(String phone){
        return userMapper.findByPhone(phone);
    }

    public UserBean findById(String userId){
        return userMapper.findById(userId);
    }

    public List<UserBean> findAll(){
        return userMapper.findAll();
    }
}
