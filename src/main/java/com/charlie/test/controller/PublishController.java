package com.charlie.test.controller;

import com.charlie.test.dto.QuestionDTO;
import com.charlie.test.exception.CustomizeErrorCode;
import com.charlie.test.exception.CustomizeException;
import com.charlie.test.mapper.QuestionMapper;
import com.charlie.test.model.Question;
import com.charlie.test.model.User;
import com.charlie.test.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;



    @GetMapping("/publish")
    public String publish(){
        return "/publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("id",questionDTO.getId());
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        return "/publish";
    }



    @PostMapping("/publish")
    public String doPublish(
            HttpServletRequest request,
            Model model,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id){
       model.addAttribute("id",id);
       model.addAttribute("title",title);
       model.addAttribute("description",description);
       model.addAttribute("tag",tag);
        if (StringUtils.isEmpty(title)) {
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if (StringUtils.isEmpty(description)) {
            model.addAttribute("error","问题补充不能为空");
            return "/publish";
        }
        if (StringUtils.isEmpty(tag)) {
            model.addAttribute("error","标签不能为空");
            return "/publish";
        }
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "/publish";
        }else {
            Question question = new Question();
            question.setId(id);
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setGmtCreate(System.currentTimeMillis());
            question.setModified(System.currentTimeMillis());
            question.setCreator(user.getId());
            questionService.saveOrUpdate(question);
            return "redirect:/";
        }
    }

}
