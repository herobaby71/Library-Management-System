/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;

import java.util.List;

/**
 * @author Shade
 *
 * The user class is created to imitate the normal active directory
 * A user object has:
 * user name (string)
 * password (string): no hashing for simplicity sake
 * borrowed history (List): list of books borrowed in the past, can be repetitive
 * authority (int) : for simplicity sake 1 | normal user can add and borrow 2 | Admin
 */ 
public class User {
   private String userName = "";
   private String password = "";
   private List<Book> borrowedBooks;
   int id = 0;
   private int authority = 1;
   String address = "";
   String email = "";
   
    public User(int id, String userName, String password, int authority, String address, String email, List<Book> borrowedBooks){
       this.id = id;
       this.userName = userName;
       this.password = password;
       this.authority = authority;
       this.address = address;
       this.email = email;
       this.borrowedBooks = borrowedBooks;
    }
    public User(int id, String userName, String password, int authority, String address, String email)
    {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authority = authority;
        this.address = address;
        this.email = email;
    }
   
   
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
}
