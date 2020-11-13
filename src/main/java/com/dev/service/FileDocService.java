package com.dev.service;


import com.dev.entity.FileDoc;

/**
 * @author 路飞
 * @create 2020/11/2
 */
public interface FileDocService {
    //上传文件，插入数据库记录
    int uploadFile(FileDoc fileDoc);
}
