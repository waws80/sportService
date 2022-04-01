package edu.wj.sport.service.mapper;

import edu.wj.sport.service.bean.CurriculumBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CurriculumMapper {


    @Insert("insert into " +
            "table_curriculum (thumb, video, cname, tip, type, duration, content) " +
            "values (#{thumb}, #{video}, #{cname}, #{tip}, #{type}, #{duration}, #{content});")
    int add(String thumb, String video, String cname, String tip, String type, long duration, String content);


    @Delete("delete " +
            "from table_curriculum " +
            "where id = #{id};")
    int delete(int id);

    @Update("update table_curriculum " +
            "set tip = #{tip}, type = #{type}, content = #{content} " +
            "where id = #{id}; ")
    int update(int id, String tip, String type, String content);

    @Select("select * " +
            "from table_curriculum " +
            "where type = #{type};")
    List<CurriculumBean> findByType(String type);

    @Select("select * " +
            "from table_curriculum;")
    List<CurriculumBean> findAll();


}
