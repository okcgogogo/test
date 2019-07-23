package com.charlie.test.service;

import com.charlie.test.dto.PaginationDTO;
import com.charlie.test.dto.QuestionDTO;
import com.charlie.test.exception.CustomizeErrorCode;
import com.charlie.test.exception.CustomizeException;
import com.charlie.test.mapper.QuestionMapper;
import com.charlie.test.mapper.UserMapper;
import com.charlie.test.model.Question;
import com.charlie.test.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size){
        PaginationDTO paginationDTO = new PaginationDTO();
        int totalNums = questionMapper.queryTotalNums();

        paginationDTO.setPagination(totalNums,page,size);
        List<Question> questionList = questionMapper.queryQuestionList(paginationDTO.getOffset(),size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.getUserById(question.getCreator());
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO listByUserID(Integer id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        int totalNums = questionMapper.queryTotalNumsByUserId(id);

        paginationDTO.setPagination(totalNums,page,size);
        List<Question> questionList = questionMapper.queryQuestionListByUserId(id,paginationDTO.getOffset(),size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.getUserById(question.getCreator());
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND.getMessage());
        }
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.getUserById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void saveOrUpdate(Question question) {
        if(question.getId() == null){
            questionMapper.saveQuestion(question);
        }else {
            int i = questionMapper.update(question);
            if (i != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND.getMessage());
            }
        }
    }

    public void incrview(Integer id) {
        questionMapper.updateViewCount(id);
    }
}
