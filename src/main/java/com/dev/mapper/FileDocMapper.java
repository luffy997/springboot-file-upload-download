package com.dev.mapper;

import com.dev.entity.FileDoc;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import javax.xml.ws.soap.MTOM;

/**
 * @author 路飞
 * @create 2020/11/2
 */
@Mapper
@Repository
public interface FileDocMapper {

    //上传文件，插入数据库记录
    @Insert("INSERT INTO file VALUES(DEFAULT,#{file_name},now(),#{ip_addr})")
    int uploadFile(FileDoc fileDoc);
}
