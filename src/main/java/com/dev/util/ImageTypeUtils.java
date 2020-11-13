package com.dev.util;

/**
 * @author 路飞
 * @create 2020/11/13
 */
public class ImageTypeUtils {

    /**
     * 检查图片类型是否合法
     * @param suffixName 文件后缀名
     * @return
     */
    public static boolean checkImageUtils(String suffixName){
        if (suffixName.equals(".jpg") || suffixName.equals(".png") || suffixName.equals(".gif")){
            return true;
        }else {
            return false;
        }
    }
}
