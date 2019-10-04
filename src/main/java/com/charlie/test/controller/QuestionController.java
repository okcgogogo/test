package com.charlie.test.controller;

import com.charlie.test.dto.CommentDTO;
import com.charlie.test.dto.QuestionDTO;
import com.charlie.test.service.CommentService;
import com.charlie.test.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
     List<CommentDTO> comments = commentService.listByQuestionId(id);
        // 阅读数累加
        questionService.incrview(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }

}
