package com.pwb.libtest.controller.restful;


import com.pwb.libtest.bean.Book;
import com.pwb.libtest.bean.LibUser;
import com.pwb.libtest.bean.Order;
import com.pwb.libtest.repository.BookRepository;
import com.pwb.libtest.repository.OrderRepository;
import com.pwb.libtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class RestAdminController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping(value = "/home")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> adminHome() {
        List<Book> books = bookRepository.selectAllBook();
        Integer sumTotalCount = books.parallelStream().mapToInt(Book::getTotalCount).sum();
        Integer sumLendCount = books.parallelStream().mapToInt(Book::getLendCount).sum();
        Integer sumNowCount = books.parallelStream().mapToInt(Book::getNowCount).sum();

        Map<String, Integer> map = new HashMap<>();
        map.put("sumTotalCount", sumTotalCount);
        map.put("sumLendCount", sumLendCount);
        map.put("sumNowCount", sumNowCount);
        return map;
    }

    @GetMapping(value = "/orderhome")
    @ResponseStatus(HttpStatus.OK)
    public Integer adminOrderGalance() {
        List<Order> orders = orderRepository.selectAllOrder();
        return orders.size();
    }

    @GetMapping(value = "/bookinfo")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> adminBook() {
        List<Book> books = bookRepository.selectAllBook();
        return books;
    }

    @GetMapping(value = "/userinfo")
    @ResponseStatus(HttpStatus.OK)
    public List<LibUser> adminUser() {
        List<LibUser> users = userRepository.selectAllUser();
        return users;
    }

    @GetMapping(value = "/orderinfo")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> adminOrder() {
        List<Order> orders = orderRepository.selectAllOrder();
        return orders;
    }

    @PutMapping(value = "/banuser/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer banUser(@PathVariable Integer id) {
        userRepository.banUser(id);
        return id;
    }

    @PutMapping(value = "/unbanuser/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer restoreUser(@PathVariable Integer id) {
        userRepository.restoreUser(id);
        return id;
    }

    @PutMapping(value = "/forcereturn/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Order returnBook(@PathVariable Integer id) {
        Order temp = new Order(id, new Date(), 1);
        orderRepository.updateOrder(temp);
        return temp;
    }

    @DeleteMapping(value = "/deleteorder/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Integer deleteOrder(@PathVariable Integer id) {
        orderRepository.deleteOrder(id);
        return id;
    }

    @PostMapping("/addbook")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) {
        bookRepository.insertBook(book);
        return book;
    }

    @DeleteMapping(value = "/deletebook/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Integer deleteBook(@PathVariable Integer id) {
        bookRepository.deleteBook(id);
        return id;
    }
}
