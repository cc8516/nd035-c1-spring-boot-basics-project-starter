package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public void addNote(Note note) {
        noteMapper.addNote(note);
    }

    public void deleteNote(Integer noteid) {
        noteMapper.deleteNote(noteid);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

}
