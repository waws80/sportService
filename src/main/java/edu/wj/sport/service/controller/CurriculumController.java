package edu.wj.sport.service.controller;

import edu.wj.sport.service.service.CurriculumService;
import edu.wj.sport.service.utils.SportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("curriculum")
public class CurriculumController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CurriculumService curriculumService;

    @PostMapping("add")
    public SportResponse add(){
        String thumb = httpServletRequest.getParameter("thumb");
        String video = httpServletRequest.getParameter("video");
        String cname = httpServletRequest.getParameter("cname");
        String tip = httpServletRequest.getParameter("tip");
        String type = httpServletRequest.getParameter("type");
        String duration = httpServletRequest.getParameter("duration");
        String content = httpServletRequest.getParameter("content");

        if (thumb.isEmpty() || video.isEmpty() || cname.isEmpty() || tip.isEmpty()
                || type.isEmpty() || duration.isEmpty() || content.isEmpty()){
            return SportResponse.paramsError("参数错误");
        }

        long d;

        try {
            d = Long.parseLong(duration);
        }catch (Exception e){
            return SportResponse.paramsError("参数错误");
        }
        if (this.curriculumService.add(thumb,video, cname, tip, type, d, content)){
            return SportResponse.success("");
        }
        return SportResponse.serviceError();
    }


    @DeleteMapping("delete")
    public SportResponse delete(){
        String idStr = httpServletRequest.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idStr);
        }catch (Exception e){
            return SportResponse.paramsError("参数错误");
        }
        if (curriculumService.delete(id)){
            return SportResponse.success("");
        }
        return SportResponse.serviceError();
    }

    @PutMapping("update")
    public SportResponse update(){
        String idStr = httpServletRequest.getParameter("id");
        String tip = httpServletRequest.getParameter("tip");
        String type = httpServletRequest.getParameter("type");
        String content = httpServletRequest.getParameter("content");

        if (tip.isEmpty() || type.isEmpty() || content.isEmpty()){
            return SportResponse.paramsError("参数错误");
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        }catch (Exception e){
            return SportResponse.paramsError("参数错误");
        }
        if (this.curriculumService.update(id, tip, type, content)){
            return SportResponse.success("");
        }
        return SportResponse.serviceError();
    }

    @GetMapping("find")
    public SportResponse find(){
        String type = httpServletRequest.getParameter("type");

        if (type == null || type.isEmpty()){
            return SportResponse.success(this.curriculumService.findAll());
        }
        return SportResponse.success(this.curriculumService.findByType(type));
    }


}
