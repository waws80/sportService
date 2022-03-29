//package edu.wj.sport.service.mapper;
//
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//
//import java.util.List;
//
//@Mapper
//public interface SportDataMapper {
//
//
//    @Insert("insert into table_sport_data (type, distance, power, timeLen, userId, dateTime)" +
//            "values (#{type},#{distance},#{power},#{timeLen},#{userId},#{dateTime});")
//    int addData(int type, double distance, int power, long timeLen, int userId, long dateTime);
//
//
//    @Select("select * " +
//            "from table_sport_data where userId = #{userId};")
//    List<SportBean> findByUserId(int userId);
//
//    @Select("select * " +
//            "from table_sport_data;")
//    List<SportBean> findAll();
//}
