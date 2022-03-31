package edu.wj.sport.service.mapper;

import edu.wj.sport.service.bean.SportBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SportDataMapper {


    @Insert("insert " +
            "into table_sport_history (id, userId, mileage, duration, points, createTime) " +
            "values (#{id}, #{userId}, #{mileage}, #{duration}, #{points}, #{createTime});")
    int addData(String id, String userId, double mileage, long duration, String points, long createTime);


    @Delete("delete " +
            "from table_sport_history " +
            "where id = #{id};")
    int deleteOne(String id);

    @Select("select * " +
            "from table_sport_history where userId = #{userId};")
    List<SportBean> findByUserId(String userId);

}
