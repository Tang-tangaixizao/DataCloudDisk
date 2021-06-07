package com.example.test.dao;

import com.example.test.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyDao {
    UserInfo UserLogin(@Param("username")String username,@Param("userpwd")String userpwd);

    int UserRegister(@Param("id") String id,
                     @Param("name") String name,
                     @Param("pwd") String pwd,
                     @Param("sex")String sex,
                     @Param("userType")String userType);
    List<UserInfo> queryAllInfo();
    int delUser(@Param("id") String id);
    int updateUserInfo(@Param("id")String id,
                       @Param("sex")String sex,
                       @Param("type")String type);
    UserInfo queryUserById(@Param("id") String id);
}
