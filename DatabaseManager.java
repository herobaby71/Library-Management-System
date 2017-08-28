/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

//DatabaseManager acts as a Data Access Object by executing sql commands and using build-in java objects to grab data
public class DatabaseManager {
    private Connection conn = null;
    private Statement stm = null;
    private String user = "";
    private String pass = "";
    
    //Constructors for the DAO DatabaseManager: set up the user name and password to login to the database
    public DatabaseManager()
    {
        user = "root";
        pass ="";
    }
    
    public DatabaseManager(String user, String pass)
    {
        this.user = user;
        this.pass = pass;
    }
    
    //Connect the database using the username, password through port 3306
    public void connectDB()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", user, pass);
            stm = conn.createStatement();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
    }

    //Grab all books currently in the database
    public List<Book> getAllBooks() throws SQLException
    {
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        try{
            String sql;
            sql = "SELECT books.id, books.bookName, books.description, authors.firstName, authors.lastName, genres.name FROM books LEFT JOIN authors ON books.author_id = authors.id LEFT JOIN bookgenres ON books.id = bookgenres.book_id LEFT JOIN genres ON genres.id = bookgenres.genre_id";
            rs = stm.executeQuery(sql);
            int id = 0;
            //software engineering
            while(rs.next())
            {
                if (rs.getInt("id") == id)
                {
                    
                    books.get(id-1).addBookGenre(rs.getString("name"));
                }
                else
                {
                    ArrayList<String> genres = new ArrayList<>();
                    genres.add(rs.getString("name"));
                    books.add(
                        new Book(rs.getInt("id"),
                        rs.getString("bookName"),
                        rs.getString("firstName")+ " " + rs.getString("lastName"),
                        rs.getString("description"),genres)
                    );                        
                    id = rs.getInt("id");
                }
            }
            rs.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return books;
    }
    
    //Grab books bases on the author name (currently unused)
    public List<Book> getBooksByAuthor(String author) throws SQLException
    {
        ResultSet rs = null;
        List<Book> books = new ArrayList<Book>();
        try{
            String sql;
            sql ="SELECT books.id, books.bookName, books.description, authors.firstName, authors.lastName, genres.name FROM books\n" +
                    "LEFT JOIN authors ON books.author_id = authors.id\n" + 
                    "LEFT JOIN bookgenres ON books.id = bookgenres.book_id\n" +
                    "LEFT JOIN genres ON genres.id = bookgenres.genre_id\n" +
                    "WHERE authors.firstName = \"" + author + "\" OR authors.lastName = \"" + author + "\"";
            rs = stm.executeQuery(sql);
            int id = 0;
            while(rs.next())
            {
                if (rs.getInt("id") == id)
                {
                    
                    books.get(id-1).addBookGenre(rs.getString("name"));
                }
                else
                {
                    ArrayList<String> genres = new ArrayList<>();
                    genres.add(rs.getString("name"));
                    books.add(
                        new Book(rs.getInt("id"),
                        rs.getString("bookName"),
                        rs.getString("firstName")+ " " + rs.getString("lastName"),
                        rs.getString("description"),genres)
                    );                        
                    id = rs.getInt("id");
                }
            }
            rs.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return books;
    } 
    
    //Grab books bases on keywords and characters for searching purposes
    public List<Book> getBooksByKeyWord(String keyword) throws SQLException
    {
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        try{
            String sql;
            sql ="SELECT books.id, books.bookName, books.description, authors.firstName, authors.lastName, genres.name FROM books\n" +
                "LEFT JOIN authors ON books.author_id = authors.id\n" +
                "LEFT JOIN bookgenres ON books.id = bookgenres.book_id \n" +
                "LEFT JOIN genres ON genres.id = bookgenres.genre_id\n" +
                "WHERE\n" +
                "   books.bookName LIKE \"%" + keyword + "%\"\n" +
                "    OR authors.firstName LIKE \"%"+ keyword + "%\"\n" +
                "    OR authors.lastName LIKE \"%" + keyword + "%\"\n" +
                "    OR genres.name LIKE \"%" + keyword + "%\"";
            rs = stm.executeQuery(sql);
            int id = 0;
            int i = 0;
            while(rs.next())
            {
                if (rs.getInt("id") == id)
                {
                    
                    books.get(i-1).addBookGenre(rs.getString("name"));
                }
                else
                {
                    ArrayList<String> genres = new ArrayList<>();
                    genres.add(rs.getString("name"));
                    books.add(
                        new Book(rs.getInt("id"),
                        rs.getString("bookName"),
                        rs.getString("firstName")+ " " + rs.getString("lastName"),
                        rs.getString("description"),genres)
                    );                        
                    id = rs.getInt("id");
                    i=i+1;
                }
            }
            rs.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        return books;
    }
    
    //Takes in username and password to verify the login information (unpractical)
    public User verifyUserIDPassword(String userName, String password) throws SQLException
    {
        User user = null;
        ResultSet rs = null;
        try{
            String sql;
            sql = "SELECT * FROM `users` WHERE users.userName = \"" + userName + "\" AND users.password = \"" + password+ "\"";
            rs = stm.executeQuery(sql);
            if(rs.next())
            {
                user = new User(rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("authority"),
                        rs.getString("address"),
                        rs.getString("email"));
                        
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        finally
        {
            rs.close();
        }
        return user;
    }
    
    //get books that the User borrow (currently unused)
    public List<Book> getUserBook(int userID) throws SQLException
    {
        List<Book> borrowedBooks = new ArrayList<>();
        ResultSet rs = null;
        try{
            String sql = "SELECT books.id, books.bookName, books.description, authors.firstName, authors.lastName, genres.name FROM books\n" +
                            "LEFT JOIN authors ON books.author_id = authors.id\n" +
                            "LEFT JOIN bookgenres ON books.id = bookgenres.book_id\n" +
                            "LEFT JOIN genres ON genres.id = bookgenres.genre_id\n" +
                            "LEFT JOIN borrowedlog ON borrowedlog.book_id = books.id\n" +
                            "LEFT JOIN users ON borrowedlog.user_id = users.id\n" +
                            "WHERE users.id = " + userID;
            rs = stm.executeQuery(sql);
            int id = 0;
            int i =0;
            while(rs.next())
            {
                if (rs.getInt("id") == id)
                {
                    
                    borrowedBooks.get(i-1).addBookGenre(rs.getString("name"));
                }
                else
                {
                    ArrayList<String> genres = new ArrayList<>();
                    genres.add(rs.getString("name"));
                    borrowedBooks.add(
                        new Book(rs.getInt("id"),
                        rs.getString("bookName"),
                        rs.getString("firstName")+ " " + rs.getString("lastName"),
                        rs.getString("description"),genres)
                    );                        
                    id = rs.getInt("id");
                    i+=1;
                }
            }
            rs.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        
        }
        return borrowedBooks;
    }
    
    //Insert into borrowedlog the books that the user borrows through user_id and book_id
    public void insertBorrowedLog(int userID, int bookID, Date borrowDate, Date returnDate) throws SQLException
    {
        try{
            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sql = "INSERT INTO borrowedlog\n" +
                        "(book_id, user_id, dateLended, dateReturned)\n" +
                        "VALUES\n" +
                        "("+ userID +","+ bookID +","+"\'"+DateFormat.format(borrowDate)+"\',\'"+DateFormat.format(returnDate)+"\')";
                        
            stm.executeUpdate(sql);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
    }
    
    //get the current logged in user borrow log
    public List<borrowlog> getBorrowedLog(int userID) throws SQLException
    {
        ResultSet rs = null;
        List<borrowlog> borrowLog = new ArrayList<>();
        try{
            String sql = "SELECT books.bookName, authors.firstName, authors.lastName, borrowedlog.dateLended, borrowedlog.dateReturned FROM borrowedlog \n" +
                        "LEFT JOIN books ON books.id = borrowedlog.book_id\n" +
                        "LEFT JOIN authors ON authors.id = books.author_id\n" +
                        "WHERE borrowedlog.user_id = " + userID;
            rs = stm.executeQuery(sql);
            while(rs.next())
            {
                borrowLog.add(
                        new borrowlog(
                                rs.getString("bookName"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("dateLended"),
                                rs.getString("dateReturned"))
                );
                
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return borrowLog;
    }
    
    //Close database by shutting down all connections
    public void closeDB()
    {
        try{
            stm.close();
            conn.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
               if(stm != null)
                  stm.close();
            }catch(SQLException se2){

            }

            try{
                if(conn!=null){
                  conn.close();
                }
            } catch(SQLException se){
               se.printStackTrace();
            }//end finally try
        }//end try
    }
    
}

