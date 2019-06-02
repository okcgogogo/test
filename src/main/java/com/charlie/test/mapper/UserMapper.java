package com.charlie.test.mapper;

import com.charlie.test.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (id,account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{id},#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user where token = #{value}")
    User getUserByToken(@Param("value") String value);
}
