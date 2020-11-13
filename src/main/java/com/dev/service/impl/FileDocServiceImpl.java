package com.dev.service.impl;

import com.dev.entity.FileDoc;
import com.dev.mapper.FileDocMapper;
import com.dev.service.FileDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 路飞
 * @create 2020/11/2
 */
@Service
public class FileDocServiceImpl implements FileDocService {

    @Autowired
    private FileDocMapper fileDocMapper;

    @Override
    public int uploadFile(FileDoc fileDoc){
        return fileDocMapper.uploadFile(fileDoc);
    }
}
