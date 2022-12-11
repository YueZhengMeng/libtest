package com.pwb.libtest.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    int orderID;
    Book book;
    LibUser libUser;
    String lendTime;
    String returnTime;
    int flag;

    public static String transTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
    }

    public static String getSystemTime(){
        Date date=new Date();
        return transTime(date);
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = transTime(returnTime);
    }

    public void setLendTime(Date lendTime) {
        this.lendTime = transTime(lendTime);
    }

    public Order() {
        this.lendTime=getSystemTime();
        this.returnTime=null;
    }

    public Order(int orderID, Date returnTime, int flag) {
        this.orderID = orderID;
        this.returnTime = transTime(returnTime);
        this.flag = flag;

        this.lendTime=null;
        this.book=null;
        this.libUser=null;
    }

    public Order(Book book, LibUser libUser) {
        this.book = book;
        this.libUser = libUser;

        this.lendTime=getSystemTime();
        this.returnTime=null;
        this.flag=0;
    }

    public Order(int orderID, Book book, LibUser libUser, String lendTime, String returnTime, int flag) {
        this.orderID = orderID;
        this.book = book;
        this.libUser = libUser;
        this.lendTime = lendTime;
        this.returnTime = returnTime;
        this.flag = flag;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LibUser getLibUser() {
        return libUser;
    }

    public void setLibUser(LibUser libUser) {
        this.libUser = libUser;
    }

    public String getLendTime() {
        return lendTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
