package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;


//fileId INT PRIMARY KEY auto_increment,
//filename VARCHAR,
//contenttype VARCHAR,
//filesize VARCHAR,
//userid INT,
//filedata BLOB,
//foreign key (userid) references USERS(userid)

@Mapper
public interface FileMapper {

    @Select("select * from files")
    List<File> getAllFiles();

    @Select("select * from files where fileid = #{fileId}")
    File getFile(Integer fileId);

    @Insert("insert into files (filename, contenttype, filesize, userid, filedata) values (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Delete("delete from files where fileid = #{fileId}")
    int deleteFile(Integer fileId);
}
