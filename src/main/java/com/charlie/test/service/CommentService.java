package com.charlie.test.service;

import com.charlie.test.CommentTypeEnum;
import com.charlie.test.dto.CommentDTO;
import com.charlie.test.exception.CustomizeErrorCode;
import com.charlie.test.exception.CustomizeException;
import com.charlie.test.mapper.CommentMapper;
import com.charlie.test.mapper.QuestionMapper;
import com.charlie.test.mapper.UserMapper;
import com.charlie.test.model.Comment;
import com.charlie.test.model.Question;
import com.charlie.test.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment commentDB = commentMapper.getCommentById(comment.getParentId());
            if (commentDB == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            // 回复问题
            Question questionDB = questionMapper.getById(comment.getParentId());
            if (questionDB == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(questionDB.getId());
        }

    }

    public List<CommentDTO> listByQuestionId(Long id) {
        Comment comment = new Comment();
        comment.setParentId(id);
        comment.setType(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = questionMapper.selectByParentId(comment);
        if (comments == null || comments.size() == 0) {
            return new ArrayList<>();
        }
        Set<Long> userIds = comments.stream().map(comment1 -> comment1.getCommentator()).collect(Collectors.toSet());

        Map<Long, User> userMap = userIds.stream().map(userId -> {
            User user = userMapper.getUserById(userId);
            return user;
        }).collect(Collectors.toList())
                .stream()
                .collect(Collectors.toMap(user -> user.getId(), user -> user));


        List<CommentDTO> commentDTOS = comments.stream().map(comment1 -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment1, commentDTO);
            commentDTO.setUser(userMap.get(comment1.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
