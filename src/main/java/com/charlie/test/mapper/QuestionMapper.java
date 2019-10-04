package com.charlie.test.mapper;

import com.charlie.test.model.Comment;
import com.charlie.test.model.Question;
import org.apache.ibatis.annotations.*;

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
    int queryTotalNumsByUserId(@Param("id") Long id);

    @Select("select * from question where creator = #{id} limit #{offset},#{size}")
    List<Question> queryQuestionListByUserId(@Param("id") Long id,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title = #{title},description = #{description},modified = #{modified},tag = #{tag} where id=#{id}")
    int update(Question question);

    @Update("update question set view_count = view_count + 1 where id = #{id}")
    int updateViewCount(Long id);

    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    int incCommentCount(Long id);

    @Select("select * from comment where parent_id = #{parentId} and type = #{type} order by gmt_create desc ")
    List<Comment> selectByParentId(Comment comment);
}
