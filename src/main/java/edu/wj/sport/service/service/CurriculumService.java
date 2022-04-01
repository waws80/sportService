package edu.wj.sport.service.service;

import edu.wj.sport.service.bean.CurriculumBean;
import edu.wj.sport.service.mapper.CurriculumMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumMapper curriculumMapper;


    public boolean add(String thumb, String video, String cname, String tip, String type, long duration, String content){
        return this.curriculumMapper.add(thumb, video, cname, tip, type, duration, content) > 0;
    }


    public boolean delete(int id){
        return this.curriculumMapper.delete(id) > 0;
    }


    public boolean update(int id, String tip, String type, String content){
        return this.curriculumMapper.update(id, tip, type, content) > 0;
    }


    public List<CurriculumBean> findByType(String type){
        return this.curriculumMapper.findByType(type);
    }


    public List<CurriculumBean> findAll(){
        return this.curriculumMapper.findAll();
    }


}
