package edu.wj.sport.service.mapper;

import edu.wj.sport.service.bean.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface UserMapper {


    @Insert("insert into " +
            "table_user (id, nickName, phoneNumber, pwd, sex, birthDate, avatar, createTime, status) " +
            "values (#{id}, #{nickName}, #{phoneNumber}, #{pwd}, #{sex}, #{birthDate}, #{avatar}, #{createTime}, #{status});")
    int addUser(String id, String nickName, String phoneNumber, String pwd, int sex, long birthDate, String avatar, long createTime, int status);

    @Update("update table_user " +
            "set status = #{status} " +
            "where id = #{id};")
    int updateStatus(String id, int status);

    @Update("update table_user " +
            "set nickName = #{nickName}, pwd = #{pwd}, avatar = #{avatar} " +
            "where id = #{id};")
    int updateInfo(String id, String nickName, String pwd, String avatar);

    @Select("select * " +
            "from table_user " +
            "where phoneNumber = #{phone};")
    UserBean findByPhone(String phone);


    @Select("select * " +
            "from table_user " +
            "where id = #{id};")
    UserBean findById(String id);

    @Select("select * " +
            "from table_user;")
    List<UserBean> findAll();

}
