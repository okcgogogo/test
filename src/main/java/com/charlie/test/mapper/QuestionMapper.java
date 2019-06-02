package com.charlie.test.mapper;

import com.charlie.test.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{modified},#{creator},#{tag})")
    void saveQuestion(Question question);

}
