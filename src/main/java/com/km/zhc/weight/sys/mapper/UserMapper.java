package com.km.zhc.weight.sys.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    @Insert("insert into user(user_name,password)values(#{userName},#{password})")
    int addUser(@Param("userName") String userName,@Param("password") String password);

    @Select("select count(1) from user where user_name=#{userName}")
    long countUserByUserName(@Param("userName") String userName);

    @Select("select count(1) from user where user_name=#{userName} and password=#{password}")
    long countUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

}
