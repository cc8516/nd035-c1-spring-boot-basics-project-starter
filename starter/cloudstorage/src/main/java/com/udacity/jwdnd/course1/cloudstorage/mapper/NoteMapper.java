package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

//    noteid INT PRIMARY KEY auto_increment,
//    notetitle VARCHAR(20),
//    notedescription VARCHAR (1000),
//    userid INT,
//    foreign key (userid) references USERS(userid)

@Mapper
public interface NoteMapper {

    @Select("select * from notes")
    List<Note> getAllNotes();

    @Insert("insert into notes (notetitle, notedescription, userid) values (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "")
    int addNote(Note note);

    @Delete("delete from notes where noteid = #{noteid}")
    int deleteNote(Integer noteid);

    @Update("update notes set notetitle = #{notetitle}, notedescription = #{notedescription} where noteid = #{noteid}")
    int updateNote(Note note);
}
