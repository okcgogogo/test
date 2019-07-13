package com.charlie.test.mapper;

import com.charlie.test.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{modified},#{creator},#{tag})")
    void saveQuestion(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> queryQuestionList(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(*) from question")
    int queryTotalNums();

    @Select("select count(*) from question where creator = #{id}")
    int queryTotalNumsByUserId(@Param("id") Integer id);

    @Select("select * from question where creator = #{id} limit #{offset},#{size}")
    List<Question> queryQuestionListByUserId(@Param("id") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);
}
