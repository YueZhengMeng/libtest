package com.pwb.libtest.repository;

import com.pwb.libtest.bean.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BookRepository {
    @Select("Select * from books where bookID=#{id}")
    Book selectBookByID(Integer id);

    @Select("Select * from books")
    List<Book> selectAllBook();

    @Update("Update books set lendCount=#{lendCount},nowCount=#{nowCount} where bookID=#{bookID}")
    void updateBook(Book book);

    @Insert("insert into books(bookName,author,publishing,price,totalCount,lendCount,nowCount) " +
            "values(#{bookName},#{author},#{publishing},#{price},#{totalCount},#{lendCount},#{nowCount})")
    void insertBook(Book book);

    @Delete("delete from books where bookID=#{bookID}")
    void deleteBook(Integer bookID);
}
