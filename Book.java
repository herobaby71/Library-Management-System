/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;

/**
 * @author Shade
 * Object book
 * In a library database, this will be the most fundamental object.
 * Variable contains: id, book name, author, general description 
 */
import java.util.List;
import java.util.ArrayList;

public class Book {
    private int id =0;
    private String bookName = "";
    private String authorName = "";
    private String description = "";
    private List<String> bookGenre = null;

    //Initialization requires all information for the book
    public Book(int id, String bookName, String authorName, String description, List<String> bookGenre)
    {
        this.id = id;
        this.bookName = bookName;
        this.authorName = authorName;
        this.description = description;
        this.bookGenre = bookGenre;
    }

    //getters and setters
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getBookGenre() {
        return bookGenre;
    }

    public String getBookGenreString()
    {
        String genres = "";
        for(int i = 0; i<this.bookGenre.size();i++)
        {
            genres+=bookGenre.get(i) + ", ";
        }
        return genres;
    }
    public void setBookGenre(List<String> bookGenre) {
        this.bookGenre = bookGenre;
    }
    public void addBookGenre(String genre)
    {
        this.bookGenre.add(genre);
    }


    
    
}
