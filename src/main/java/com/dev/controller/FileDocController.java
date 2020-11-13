package com.dev.controller;



import com.dev.entity.FileDoc;
import com.dev.service.FileDocService;
import com.dev.util.CodeGenerateUtil;
import com.dev.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author 路飞
 * @create 2020/11/1
 */
@RestController
public class FileDocController {


    private final static String FILEPATH ="F:/testUploadDownLoad/file/";

    private static final Logger log = LoggerFactory.getLogger(FileDocController.class);

    @Autowired
    private FileDocService fileDocService;


    /**
     * 单文件上传
     * @param file 前端传文件的参数
     * @return
     */
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        FileDoc fileDoc = new FileDoc();
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            //获取大小
            long size = file.getSize();
            log.info("文件大小：" + size);
            //判断文件上传大小
            if (!FileUtils.checkFileSize(file, 20, "M")) {
                log.info("上传文件规定小于20MB");
                return "上传文件规定小于20MB";
            }
            //获取文件名
            String filename = file.getOriginalFilename();
            log.info("上传的文件名为：" + filename);
            //获取文件后缀名
            String suffixName = filename.substring(filename.lastIndexOf("."));
            log.info("文件的后后缀名：" + suffixName);

            //若要判断文件上传的类型，
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

    /**
     * 多文件上传
     * @param request
     * @return
     */
    @PostMapping("/batch")
    public String hanleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        FileDoc fileDoc = new FileDoc();
        fileDoc.setIp_addr(request.getRemoteAddr());
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            //检查文件大小
            if (!FileUtils.checkFileSize(file, 20, "M")) {
                log.info("第 "+ (i+1) + "文件大于20MB");
                return "上传文件规定小于20MB"+" 第"+ (i+1) + "文件大于20MB";
            }

            if (!file.isEmpty()) {
                try {
                    //获取文件名
                    String filename = file.getOriginalFilename();
                    log.info("上传的文件名为：" + filename);
                    //获取文件后缀名
                    String suffixName = filename.substring(filename.lastIndexOf("."));
                    log.info("文件的后后缀名：" + suffixName);
                    //生成新文件名 6位随机数+文件后缀名
                    fileDoc.setFile_name(CodeGenerateUtil.generateVerCode(6).toString()+suffixName);

                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream((new FileOutputStream(new File(FILEPATH + fileDoc.getFile_name())))); //设置文件路径和文件名
                    stream.write(bytes);
                    stream.close();
                    int index = fileDocService.uploadFile(fileDoc);
                    if (index < 0){
                        return "第 " + (i+1) + " 文件上传失败";
                    }
                } catch (Exception e) {
                    stream = null;
                    return "第 " + (i+1) + " 文件上传失败===>" + e.getMessage();
                }
            } else {
                return "第 " + (i+1) + " 个文件上传失败因为文件为空";
            }
        }
        return "上传成功";
    }

    /**
     *文件下载
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {

        //测试文件下载
        String fileName = "bg.jpg";
        if (fileName != null) {
            File file = new File(FILEPATH + fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fileInputStream = null;
                BufferedInputStream bufferedInputStream = null;
                try {
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream =new BufferedInputStream(fileInputStream);
                    OutputStream outputStream = response.getOutputStream();
                    int i = bufferedInputStream.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bufferedInputStream.read(buffer);
                    }
                    return "下载成功";
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }
}
