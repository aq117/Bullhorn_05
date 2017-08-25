package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        model.addAttribute("userlist", userRepository.findAll());
        return "list";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/add")
    public String messageForm(Model model){
        model.addAttribute("message", new Message());
        model.addAttribute("user", new User());

        return "messageform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Message message, BindingResult result, @RequestParam("file")MultipartFile file){
        if(result.hasErrors() || file.isEmpty()){
            return "messageform";
        }
        message.setPosteddate(message.getCurrentDate());
        messageRepository.save(message);
        try{
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            message.setPic(uploadResult.get("url").toString());
            messageRepository.save(message);
        }catch(IOException e){
            e.printStackTrace();
            return "messageform";
        }
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model){
        model.addAttribute("message", messageRepository.findOne(id));
        return "show";
    }
}
