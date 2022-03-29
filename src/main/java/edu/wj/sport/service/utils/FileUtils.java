package edu.wj.sport.service.utils;

import java.io.File;

/**
 * 文件工具类
 */
public class FileUtils {

    public static final String windowsPath = "E:/sport-resources/";

    public static final String macPath = "/Users/thanatos/sport-resources/";



    public static final String imagePath = "img";

    public static final String mediaPath = "media";

    public static final String avatarPath = "avatar";


    public static String getSystemPath(){
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")){
            return windowsPath;
        }else  if (os.startsWith("Mac")){
            return macPath;
        }else {
            throw new RuntimeException("操作系统不支持");
        }
    }

    public static long getFileSize(String url, FileType type) {
        File file = getFile(url, type);
        if (file == null){
            return 0;
        }
        return file.length();
    }


    public static File getFile(String url, FileType type) {
        if (url.contains("://")){
            return null;
        }
        return new File(getDir(type), url);
    }


    public static File getDir(FileType type) {
        String path = null;

        if (type == FileType.AVATAR){
            path = avatarPath;
        }else if (type == FileType.IMAGE){
            path = imagePath;
        }else if (type == FileType.MEDIA){
            path = mediaPath;
        }else {
            throw new RuntimeException("类型错误");
        }

        File dir = new File(getSystemPath(), path);
        if (!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

}
