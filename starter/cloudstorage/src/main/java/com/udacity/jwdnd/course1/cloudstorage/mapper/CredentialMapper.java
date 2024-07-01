package com.udacity.jwdnd.course1.cloudstorage.mapper;

//credentialid INT PRIMARY KEY auto_increment,
//url VARCHAR(100),
//username VARCHAR (30),
//key_ VARCHAR,
//password VARCHAR,
//userid INT,
//foreign key (userid) references USERS(userid)

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from CREDENTIALS")
    List<Credential> getAllCredentials();

    @Insert("insert into CREDENTIALS (url, username, key_, password, userid) values (#{url}, #{username}, #{key_}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "")
    int addCredential(Credential credential);

    @Delete("delete from CREDENTIALS where credentialid = #{credentialid}")
    int deleteCredential(Integer credentialid);

    @Update("update CREDENTIALS set url = #{url}, username = #{username}, password = #{password} where credentialid = #{credentialid}")
    int updateCredential(Credential credential);

}
