package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserMapper userMapper;
    private final FileService fileService;
    private final NoteService noteService;

    private final CredentialService credentialService;

    public HomeController(UserMapper userMapper, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String homeView(@RequestParam(name = "tab", required = false) String tab, Model model) {
        model.addAttribute("tab", tab);
        model.addAttribute("noteForm", new Note());
        model.addAttribute("credentialForm", new Credential());

        if (tab != null && tab.equalsIgnoreCase("file")) {
            model.addAttribute("files", this.fileService.getAllFiles());
        }
        if (tab != null && tab.equalsIgnoreCase("note")) {
            model.addAttribute("notes", noteService.getAllNotes());
        }
        if (tab != null && tab.equalsIgnoreCase("credential")) {
            model.addAttribute("credentials", credentialService.getAllCredentials());
        }

        return "Home";
    }

    @PostMapping("/upload")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        String username = authentication.getName();
        User user = userMapper.getUser(username);

        redirectAttributes.addFlashAttribute("tab", "file");
        String uploadError = null;

        if (file.isEmpty()) {
           uploadError = "File is empty";
        }

        if (uploadError != null) {
            redirectAttributes.addFlashAttribute("error", uploadError);
            return "redirect:/result?error";
        }

        fileService.addFile(file, user.getUserId());

        return "redirect:/result?success";
    }

    @GetMapping("/upload/delete")
    public String deleteUploadedFile(@RequestParam int id) {

        fileService.deleteFile(id);

        return "redirect:/home?tab=file";
    }

    @GetMapping("/upload/download")
    public String downloadUploadedFile(@RequestParam int id, HttpServletResponse response) throws IOException {
        File file = fileService.getFile(id);
        byte[] filedata = file.getFiledata();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getFilename());
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(filedata);
        outputStream.close();

        return "redirect:/home?tab=file";
    }

    @PostMapping("/note")
    public String postNote(Authentication authentication, Note noteForm, Model model) {
        String username = authentication.getName();
        User user = userMapper.getUser(username);
        noteForm.setUserid(user.getUserId());

        if (noteForm.getNoteid() != null) {
            noteService.updateNote(noteForm);
        } else {
            noteService.addNote(noteForm);
        }

        return "redirect:/home?tab=note";
    }

    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam int id) {

        noteService.deleteNote(id);

        return "redirect:/home?tab=note";
    }

    @PostMapping("/credential")
    public String postCredential(Authentication authentication, Credential credentialForm, Model model) {
        String username = authentication.getName();
        User user = userMapper.getUser(username);
        credentialForm.setUserid(user.getUserId());

        if (credentialForm.getCredentialid() != null) {
            credentialService.updateCredential(credentialForm);
        } else {
            credentialService.addCredential(credentialForm);
        }

        return "redirect:/home?tab=credential";
    }

    @GetMapping("/credential/delete")
    public String deleteCredential(@RequestParam int id) {

        credentialService.deleteCredential(id);

        return "redirect:/home?tab=credential";
    }

}
