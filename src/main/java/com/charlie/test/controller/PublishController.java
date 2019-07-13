package com.charlie.test.controller;

import com.charlie.test.mapper.QuestionMapper;
import com.charlie.test.model.Question;
import com.charlie.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "/publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            HttpServletRequest request,
            Model model,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag){

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
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setGmtCreate(System.currentTimeMillis());
            question.setModified(System.currentTimeMillis());
            question.setCreator(user.getId());
            questionMapper.saveQuestion(question);
            return "redirect:/";
        }
    }

}
