package com.dev.controller;

import com.dev.entity.FileDoc;
import com.dev.service.FileDocService;
import com.dev.util.CodeGenerateUtil;
import com.dev.util.FileUtils;

import com.dev.util.ImageTypeUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author 路飞
 * @create 2020/11/13
 * 对文件进行resize
 */
@RestController
public class ResizeImageController {

    private final static String FILEPATH ="F:/testUploadDownLoad/file/";

    private static final Logger log = LoggerFactory.getLogger(FileDocController.class);

    @Autowired
    private FileDocService fileDocService;

    @RequestMapping("/uploadImage")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        FileDoc fileDoc = new FileDoc();
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            //获取大小
            long size = file.getSize();
            log.info("文件大小：" + size);
            //判断文件上传大小
            if (!FileUtils.checkFileSize(file, 10, "M")) {
                log.info("上传文件规定小于10MB");
                return "上传文件规定小于10MB";
            }
            //获取文件名
            String filename = file.getOriginalFilename();
            log.info("上传的文件名为：" + filename);
            //获取文件后缀名
            String suffixName = filename.substring(filename.lastIndexOf("."));
            log.info("文件的后后缀名：" + suffixName);

            //判断文件是否为图片
            if (!ImageTypeUtils.checkImageUtils(suffixName)){
                return "请上传图片格式为jpg,png,gif的图片";
            }

            //生成新文件名 6位随机数+文件后缀名
            fileDoc.setFile_name(CodeGenerateUtil.generateVerCode(6).toString()+suffixName);
            //拿到ip地址
            fileDoc.setIp_addr(request.getRemoteAddr());

            File dest = new File(FILEPATH+fileDoc.getFile_name());
            //检测目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs(); //新建文件夹
            }
            file.transferTo(dest); //文件写入

            //利用Thumbnails对图片进行resize
            Thumbnails.of(FILEPATH+fileDoc.getFile_name())
                    .scale(1f)
                    .outputQuality(0.7F)
                    .toFile(FILEPATH+fileDoc.getFile_name());
            log.info("图片压缩成功");

            int i = fileDocService.uploadFile(fileDoc);
            if (i > 0){
                return "文件上传成功";
            }else {
                return "文件上传失败";
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "上传失败";
    }

}
