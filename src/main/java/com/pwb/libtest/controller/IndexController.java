package com.pwb.libtest.controller;

import com.pwb.libtest.bean.Book;
import com.pwb.libtest.bean.LibUser;
import com.pwb.libtest.bean.Order;
import com.pwb.libtest.repository.BookRepository;
import com.pwb.libtest.repository.OrderRepository;
import com.pwb.libtest.repository.UserRepository;
import com.pwb.libtest.service.LogedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    static int regflag = 0;

    @RequestMapping(value = "/login")
    public String login() {
        regflag = 0;
        return "login";
    }

    @RequestMapping(value = "/lend")
    public String lend(@ModelAttribute(value = "lendId") Integer lendId, Model model) {
        Book book = bookRepository.selectBookByID(lendId);
        String userName = LogedUserService.getLoginUser();
        LibUser libUser = userRepository.selectUserByName(userName);
        book.lendChanged();
        bookRepository.updateBook(book);

        Order order = new Order(book, libUser);
        orderRepository.insertOrder(order);
        return "redirect:/index";
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        List<Book> books = bookRepository.selectAllBook();
        model.addAttribute("books", books);
        return "index";
    }

    @RequestMapping(value = "/myorder")
    public String adminOrder(Model model) {
        String userName = LogedUserService.getLoginUser();
        LibUser libUser = userRepository.selectUserByName(userName);
        List<Order> orders = orderRepository.selectOrderByUserId(libUser.getUserId());
        model.addAttribute("orders", orders);
        return "myorder";
    }

    @GetMapping(value = "/return/{id}")
    public String returnBook(@PathVariable Integer id) {
        Order temp = new Order(id, new Date(), 1);
        orderRepository.updateOrder(temp);
        return "redirect:/myorder";
    }

    @RequestMapping(value = "/toregiste")
    public String toregiste(Model model) {
        if (regflag == 0)
            model.addAttribute("pwderror", 0);
        else if (regflag == 1)
            model.addAttribute("pwderror", 1);
        else if (regflag == 2)
            model.addAttribute("pwderror", 2);
        return "register";
    }

    @RequestMapping(value = "/doregiste")
    public String doregiste(@ModelAttribute(value = "username") String username,
                            @ModelAttribute(value = "password") String password,
                            @ModelAttribute(value = "repeatpassword") String repeatpassword) {
        if (!password.equals(repeatpassword)) {
            regflag = 1;
            return "redirect:/toregiste";
        }

        LibUser l = userRepository.selectUserByName(username);
        if (l != null) {
            regflag = 2;
            return "redirect:/toregiste";
        }

        LibUser temp = new LibUser();
        temp.setUserName(username);
        temp.setPassword(password);
        temp.setRole("vip0");
        userRepository.registeUser(temp);
        regflag = 0;
        return "login";
    }
}
