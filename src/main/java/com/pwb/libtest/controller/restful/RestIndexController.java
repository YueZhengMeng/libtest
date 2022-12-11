package com.pwb.libtest.controller.restful;

import com.pwb.libtest.bean.Book;
import com.pwb.libtest.bean.LibUser;
import com.pwb.libtest.bean.Order;
import com.pwb.libtest.repository.BookRepository;
import com.pwb.libtest.repository.OrderRepository;
import com.pwb.libtest.repository.UserRepository;
import com.pwb.libtest.service.LogedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestIndexController {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/lend")
    @ResponseStatus(HttpStatus.CREATED)
    public Order lendBook(@RequestBody Integer lendId) {
        Book book = bookRepository.selectBookByID(lendId);
        String userName = LogedUserService.getLoginUser();
        LibUser libUser = userRepository.selectUserByName(userName);
        book.lendChanged();
        bookRepository.updateBook(book);

        Order order = new Order(book, libUser);
        orderRepository.insertOrder(order);
        return order;
    }

    @GetMapping("/index")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> index() {
        List<Book> books = bookRepository.selectAllBook();
        return books;
    }

    @GetMapping(value = "/myorder")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> adminOrder() {
        String userName = LogedUserService.getLoginUser();
        LibUser libUser = userRepository.selectUserByName(userName);
        List<Order> orders = orderRepository.selectOrderByUserId(libUser.getUserId());
        return orders;
    }

    @PutMapping(value = "/return/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Order returnBook(@PathVariable Integer id) {
        Order temp = new Order(id, new Date(), 1);
        orderRepository.updateOrder(temp);
        return temp;
    }

    @PostMapping(value = "/doregiste")
    public LibUser doregiste(@RequestBody LibUser libUser,
                             @RequestParam(value = "repeatpassword") String repeatpassword) {
        if (!libUser.getPassword().equals(repeatpassword)) {
            //return ;
        }

        LibUser l = userRepository.selectUserByName(libUser.getUserName());
        if (l != null) {
            //return ;
        }

        userRepository.registeUser(libUser);
        return libUser;
    }

}
