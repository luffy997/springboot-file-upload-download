package com.dev.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 路飞
 * @create 2020/11/1
 */

public class FileUtils {


        /**
         *检查文件大小
         * @param file
         * @param size
         * @param unit
         * @return
         */
        public static boolean checkFileSize(MultipartFile file,int size,String unit){
                long len = file.getSize();
                double fileSize = 0;
                if ("B".equals(unit.toUpperCase())){
                        fileSize = len;
                }else if ("k".equals(unit.toUpperCase())){
                        fileSize = (double)len / 1024;
                }else if ("M".equals(unit.toUpperCase())){
                        fileSize = (double)len / 1048576;
                }else if ("G".equals(unit.toUpperCase())){
                        fileSize = (double)len / 1073741824;
                }
                if (fileSize > size){
                        return false;
                }
                return true;
        }

}
