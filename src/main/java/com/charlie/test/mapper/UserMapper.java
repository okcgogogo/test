package com.charlie.test.mapper;

import com.charlie.test.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (id,account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{id},#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user where token = #{value}")
    User getUserByToken(@Param("value") String value);

    //@Select("select * from user where id = #{value}")
    User getUserById(Integer value);

    @Select("select * from user where account_id = #{account_id}")
    User getUserByAccountId(@Param("account_id") String account_id);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmt_modified},avatar_url=#{avatarUrl} where id = #{id}")
    void updateUser(User user);
}
