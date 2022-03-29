package edu.wj.sport.service.mapper;

import edu.wj.sport.service.bean.AdminBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {


    @Insert("insert into table_admin (id, userName, pwd) " +
            "values (#{id}, #{userName}, #{pwd});")
    int add(String id, String userName, String pwd);

    @Select("select * " +
            "from table_admin where id = #{id};")
    AdminBean findById(String id);

    @Select("select * " +
            "from table_admin where userName = #{userName};")
    AdminBean findByName(String userName);


    @Select("select * " +
            "from table_admin;")
    List<AdminBean> findAll();
}
