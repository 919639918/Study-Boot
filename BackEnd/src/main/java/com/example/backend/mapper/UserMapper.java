package com.example.backend.mapper;

import com.example.backend.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{text}")
    Account findAccountByName(String text);

    @Insert("insert into user (username,password) values(#{username},#{password})")
    int createAccount(String username, String password);
}
