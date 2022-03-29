package edu.wj.sport.service.controller;

import edu.wj.sport.service.interceptor.IgnoreLogin;
import edu.wj.sport.service.utils.CollectUtils;
import edu.wj.sport.service.utils.FileType;
import edu.wj.sport.service.utils.FileUtils;
import edu.wj.sport.service.utils.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class UploadController {



    @IgnoreLogin
    @PostMapping("upload")
    public ResponseEntity<Object> upload(FileType type, MultipartRequest files){

        if (type == null || files == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CollectUtils.mapOf(Pair.of("msg","参数异常")));
        }

        if (type == FileType.IMAGE && isImage(files)){
            return processImage(files);
        }else if (type == FileType.MEDIA && isMedia(files)){
            return processMedia(files);
        }else if (type == FileType.AVATAR){
            return processAvatar(files);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CollectUtils.mapOf(Pair.of("msg","参数异常")));
        }
    }

    private ResponseEntity<Object> processImage(MultipartRequest files) {

        return ResponseEntity.status(HttpStatus.OK).body(saveFile(FileType.IMAGE, files));
    }

    private ResponseEntity<Object> processMedia(MultipartRequest files) {

        return ResponseEntity.status(HttpStatus.OK).body(saveFile(FileType.MEDIA, files));
    }

    private ResponseEntity<Object> processAvatar( MultipartRequest files) {

        return ResponseEntity.status(HttpStatus.OK).body(saveFile(FileType.AVATAR, files));
    }

    private List<String> saveFile(FileType fileType, MultipartRequest files) {
        File dir = FileUtils.getDir(fileType);
        List<String> result = new ArrayList<>();
        for (MultipartFile value : files.getFileMap().values()) {
            String saveName = buildFileName(fileType, value.getOriginalFilename());
            File saveFile = new File(dir, saveName);
            try {
                value.transferTo(saveFile);
            }catch (IOException e){
                e.printStackTrace();
            }
            result.add(saveName);
        }
        return result;
    }



    private boolean isImage(MultipartRequest files) {
        long size = files.getFileMap().size();
        long size1 = files.getFileMap().values()
                .stream().map(multipartFile -> {
                    try {
                        return ImageIO.read(multipartFile.getInputStream()) != null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).filter(o -> o).count();
        return size == size1;
    }


    private boolean isMedia(MultipartRequest files) {
        long size = files.getFileMap().size();
        long size1 = files.getFileMap().values()
                .stream().map(multipartFile -> {
                    String contentType = URLConnection.getFileNameMap().getContentTypeFor(multipartFile.getOriginalFilename());
                    return contentType.contains("video/") || contentType.contains("audio/");
                }).filter(aBoolean -> aBoolean).count();
        return size == size1;
    }



    private String buildFileName(FileType type, @Nullable String originName){

        int i = 0;
        if (originName != null){
             i = originName.lastIndexOf('.');
        }
        String extension = "";
        if (i > 0){
            extension = originName.substring(i+1);
        }

        String id = UUID.randomUUID().toString().replace("-","");
        String end = "";
        if (!extension.isEmpty()){
            end = "." + extension;
        }
        return   type.name().toLowerCase() + "-" + id + end;
    }



}
