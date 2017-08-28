/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Shade
 */

public class borrowlog {
    //bookName, authors.firstName, authors.lastName, borrowedlog.dateLended, borrowedlog.dateReturned
    private String bookName = "";
    private String authorFirstName = "";
    private String authorLastName = "";
    private String dateLended = "";
    private String dateReturned = "";
    
    public borrowlog()
    {
        
    }
    public borrowlog(String bookName, String authorFirstName, String authorLastName, String dateLended, String dateReturned)
    {
        this.bookName = bookName;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.dateLended = dateLended;
        this.dateReturned = dateReturned;
    }

    //Getters and Setters
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getDateLended() {
        return dateLended;
    }

    public void setDateLended(String dateLended) {
        this.dateLended = dateLended;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }
}
