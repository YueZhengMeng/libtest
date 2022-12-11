package com.pwb.libtest.repository;

import com.pwb.libtest.bean.LibUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserRepository {
    @Select("Select * from libUsers where userID=#{id}")
    LibUser selectUserByID(Integer id);

    @Select("Select * from libUsers")
    List<LibUser> selectAllUser();

    @Select("Select * from libUsers where userName=#{name}")
    LibUser selectUserByName(String name);

    @Update("update libusers set role='banned' where userId=#{userId}")
    void banUser(Integer userId);

    @Update("update libusers set role='vip0' where userId=#{userId}")
    void restoreUser(Integer userId);

    @Insert("insert into libusers(userName,password,role)" +
            "values(#{userName},#{password},#{role})")
    void registeUser(LibUser libUser);
}
