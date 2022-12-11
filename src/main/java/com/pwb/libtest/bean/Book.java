package com.pwb.libtest.bean;

public class Book {
    int bookID;
    String bookName;
    String author;
    String publishing;
    double price;
    int totalCount;
    int lendCount;
    int nowCount;

    public Book() {
    }

    public Book(int bookID, String bookName, String author, String publishing, double price, int totalCount, int lendCount, int nowCount) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publishing = publishing;
        this.price = price;
        this.totalCount = totalCount;
        this.lendCount = lendCount;
        this.nowCount = nowCount;
    }

    public void lendChanged() {
        this.lendCount++;
        this.nowCount--;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLendCount() {
        return lendCount;
    }

    public void setLendCount(int lendCount) {
        this.lendCount = lendCount;
    }

    public int getNowCount() {
        return nowCount;
    }

    public void setNowCount(int nowCount) {
        this.nowCount = nowCount;
    }
}
