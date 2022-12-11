package com.pwb.libtest.controller;

import com.pwb.libtest.bean.Book;
import com.pwb.libtest.bean.LibUser;
import com.pwb.libtest.bean.Order;
import com.pwb.libtest.repository.BookRepository;
import com.pwb.libtest.repository.OrderRepository;
import com.pwb.libtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(value = "/admin/home")
    public String adminHome(Model model) {
        List<Book> books = bookRepository.selectAllBook();
        Integer sumTotalCount=books.parallelStream().mapToInt(Book::getTotalCount).sum();
        Integer sumLendCount=books.parallelStream().mapToInt(Book::getLendCount).sum();
        Integer sumNowCount=books.parallelStream().mapToInt(Book::getNowCount).sum();
        model.addAttribute("sumTotalCount",sumTotalCount);
        model.addAttribute("sumLendCount",sumLendCount);
        model.addAttribute("sumNowCount",sumNowCount);
        return "admin/home";
    }

    @RequestMapping(value = "/admin/ordergalance")
    public String adminOrderGalance(Model model) {
        List<Order> orders = orderRepository.selectAllOrder();
        model.addAttribute("orderCount",orders.size());
        return "admin/ordergalance";
    }

    @RequestMapping(value = "/admin/bookinfo")
    public String adminBook(Model model) {
        List<Book> books= bookRepository.selectAllBook();
        model.addAttribute("books",books);
        return "admin/bookinfo";
    }

    @RequestMapping(value="/admin/userinfo")
    public String adminUser(Model model) {
        List<LibUser> users = userRepository.selectAllUser();
        model.addAttribute("users",users);
        return "/admin/userinfo";
    }

    @RequestMapping(value="/admin/orderinfo")
    public String adminOrder(Model model){
        List<Order> orders = orderRepository.selectAllOrder();
        model.addAttribute("orders",orders);
        return "/admin/orderinfo";
    }

    @GetMapping(value = "/admin/forcereturn/{id}")
    public String returnBook(@PathVariable Integer id)
    {
        Order temp=new Order(id,new Date(),1);
        orderRepository.updateOrder(temp);
        return "redirect:/admin/orderinfo";
    }

    @GetMapping(value = "/admin/deleteorder/{id}")
    public String deleteOrder(@PathVariable Integer id)
    {
        orderRepository.deleteOrder(id);
        return "redirect:/admin/orderinfo";
    }

    @GetMapping(value = "/admin/banuser/{id}")
    public String banUser(@PathVariable Integer id)
    {
        userRepository.banUser(id);
        return "redirect:/admin/userinfo";
    }

    @GetMapping(value = "/admin/restoreuser/{id}")
    public String restoreUser(@PathVariable Integer id)
    {
        userRepository.restoreUser(id);
        return "redirect:/admin/userinfo";
    }

    @GetMapping(value = "/admin/deletebook/{id}")
    public String deleteBook(@PathVariable Integer id)
    {
        bookRepository.deleteBook(id);
        return "redirect:/admin/bookinfo";
    }

    @RequestMapping(value ="/admin/toaddbook")
    public String toAddBook()
    {
        return "/admin/addbook";
    }

    @RequestMapping(value ={"/admin/doaddbook"})
    public String doAddbook(@ModelAttribute(value="bookName")String bookName,
                            @ModelAttribute(value="author")String author,
                            @ModelAttribute(value="publishing")String publishing,
                            @ModelAttribute(value="price")double price,
                            @ModelAttribute(value="totalCount")int totalCount,
                            @ModelAttribute(value="lendCount")int lendCount)
    {
        Book book=new Book();
        book.setBookName(bookName);
        book.setPrice(price);
        book.setAuthor(author);
        book.setPublishing(publishing);
        book.setTotalCount(totalCount);
        book.setLendCount(lendCount);
        book.setNowCount(book.getTotalCount()- book.getLendCount());
        bookRepository.insertBook(book);
        return "redirect:/admin/bookinfo";
    }
}
