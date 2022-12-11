package com.pwb.libtest.repository;

import com.pwb.libtest.bean.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderRepository {
    @Results({
            @Result(column = "orderID",property = "orderID",id = true),
            @Result(column = "lendTime",property = "lendTime"),
            @Result(column = "returnTime",property = "returnTime"),
            @Result(column = "flag",property = "flag"),
            @Result(column = "bookID",property = "book",
                    one = @One(select="com.pwb.libtest.repository.BookRepository.selectBookByID",fetchType= FetchType.EAGER)),
            @Result(column = "userID",property = "libUser",
                    one = @One(select="com.pwb.libtest.repository.UserRepository.selectUserByID",fetchType= FetchType.EAGER))
    })
    @Select("select * from orders")
    List<Order> selectAllOrder();

    @Results({
            @Result(column = "orderID",property = "orderID",id = true),
            @Result(column = "lendTime",property = "lendTime"),
            @Result(column = "returnTime",property = "returnTime"),
            @Result(column = "flag",property = "flag"),
            @Result(column = "bookID",property = "book",
                    one = @One(select="com.pwb.libtest.repository.BookRepository.selectBookByID",fetchType= FetchType.EAGER)),
            @Result(column = "userID",property = "libUser",
                    one = @One(select="com.pwb.libtest.repository.UserRepository.selectUserByID",fetchType= FetchType.EAGER))
    })
    @Select("select * from orders where userID=#{userID}")
    List<Order> selectOrderByUserId(Integer userId);

    @Insert("insert into orders(bookID,userID,lendTime,flag) " +
            "values(#{book.bookID},#{libUser.userId},#{lendTime},#{flag})")
    void insertOrder(Order order);

    @Update("Update orders set returnTime=#{returnTime},flag=#{flag} where orderID=#{orderID}")
    void updateOrder(Order order);

    @Delete("delete from orders where orderID=#{orderID}")
    void deleteOrder(Integer orderID);
}
