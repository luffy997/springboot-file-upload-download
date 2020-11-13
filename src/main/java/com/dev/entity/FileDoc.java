package com.dev.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 路飞
 * @create 2020/11/2
 */
@Data
public class FileDoc {
    private Integer id; //主键
    private String file_name; //文件名字
    private Date upload_time; //上传日期
    private String ip_addr; //ip地址
}
