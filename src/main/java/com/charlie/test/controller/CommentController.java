package com.charlie.test.controller;

import com.charlie.test.dto.CommentDTO;
import com.charlie.test.dto.ResultDTO;
import com.charlie.test.exception.CustomizeErrorCode;
import com.charlie.test.model.Comment;
import com.charlie.test.model.User;
import com.charlie.test.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public ResultDTO postComment(HttpServletRequest request, @RequestBody CommentDTO commentDTO){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        commentDTO.setGmtCreate(System.currentTimeMillis());
        commentDTO.setGmtModified(System.currentTimeMillis());
        commentDTO.setCommentator(user.getId());
        commentDTO.setLikeCount(0L);
        BeanUtils.copyProperties(commentDTO,comment);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
