package com.charlie.test.service;

import com.charlie.test.CommentTypeEnum;
import com.charlie.test.exception.CustomizeErrorCode;
import com.charlie.test.exception.CustomizeException;
import com.charlie.test.mapper.CommentMapper;
import com.charlie.test.mapper.QuestionMapper;
import com.charlie.test.model.Comment;
import com.charlie.test.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            // 回复评论
            Comment commentDB = commentMapper.getCommentById(comment.getParentId());
            if (commentDB == null) {
                throw  new  CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            // 回复问题
            Question questionDB = questionMapper.getById(comment.getParentId());
            if (questionDB == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(questionDB.getId());
        }

    }
}
