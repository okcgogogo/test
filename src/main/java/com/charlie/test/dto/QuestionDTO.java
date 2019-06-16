package com.charlie.test.dto;

import com.charlie.test.model.Question;
import com.charlie.test.model.User;
import lombok.Data;

@Data
public class QuestionDTO extends Question {
    private User user;
}
