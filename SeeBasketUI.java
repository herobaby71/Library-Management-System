/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Shade
 */
//This UI is used to see the books the user wants to borrow
public class SeeBasketUI extends VerticalLayout{
    private List<Book> books = null;
    private User user = null;
    private DatabaseManager db = new DatabaseManager();
    
    //Components of the UI: basket is a table that holds the books user wants to borrow
    private Label intro = new Label();
    private Table basket = new Table();
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private Button borrow_button = new Button();
    private Button remove_button = new Button();
    private Label addDBSuccess = new Label();
    
    //Initiallizing the UI. books is a list of book added by the User user.
    public SeeBasketUI(List<Book> books, User user)
    {
        this.books = books;
        this.user = user;
        intro.setCaption("Click on Borrow button if you want to borrow the books\nClick on Remove button if you want to remove the book from the basket");
        
        //Add components to the UI:
        addComponent(intro);
        addComponent(basket);
        addComponent(buttonLayout);
        
        buttonLayout.addComponent(remove_button);
        buttonLayout.addComponent(borrow_button);
        
        setUpRemoveButton();
        setUpBorrowButton();
        setUpTable();        
    }
    
    //Set up basket table's container and its data, including book name, author name, borrow date, and return date.
    private void setUpTable()
    {
        basket.setColumnReorderingAllowed(true);
        basket.setColumnCollapsingAllowed(true);
        basket.setPageLength(0);
        basket.setSelectable(true);
        
        basket.addContainerProperty("id", Integer.class, null);
        basket.addContainerProperty("Book name", String.class, null);
        basket.addContainerProperty("Author name", String.class, null);
        basket.addContainerProperty("Borrow date", String.class, null);
        basket.addContainerProperty("Return date", String.class, null);
                
        addDataToTable();
    }
    
    //grab the books the user wants to borrow and add it to table basket container
    private void addDataToTable()
    {
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for(int i = 0; i < books.size(); i++)
        {            
            Calendar borrowDate = Calendar.getInstance();
            Calendar returnDate = Calendar.getInstance();
            returnDate.add(Calendar.DATE, 12);
            basket.addItem(new Object[]{i+1,books.get(i).getBookName(), books.get(i).getAuthorName(), DateFormat.format(borrowDate.getTime()), DateFormat.format(returnDate.getTime())}, i+1);
        }
    }
    
    //Set up button listener to remove selected book from table basket. The method I used is not really efficientcy.
    //Instead of deleting the book manually and renewing the 
    private void setUpRemoveButton()
    {
        remove_button.setCaption("Remove");
        remove_button.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event)
            {
                Object id = basket.getValue();
                int i = (int)basket.getContainerProperty(id,"id").getValue();
                books.remove(i-1);
                basket.removeAllItems();
                addDataToTable();
            }
        });        
    }
    
    //Set up the borrow button listener:
    //1: Connect to database
    //2: Insert the data into the database from the basket table
    private void setUpBorrowButton()
    {
        borrow_button.setCaption("Borrow");
        borrow_button.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event)
            {
                //Call upon data manager to add to borrowLog
                try{
                    db.connectDB();
                    Calendar borrowDate = Calendar.getInstance();
                    Calendar returnDate = Calendar.getInstance();
                    returnDate.add(Calendar.DATE, 12);
                    for(int i = 0; i < books.size(); i++)
                    {
                        db.insertBorrowedLog(user.getId(), books.get(i).getId(), borrowDate.getTime(), returnDate.getTime());
                    }
                    db.closeDB();
                }catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
                //Call upon date manager to add to
                basket.removeAllItems();
                addDBSuccess.setCaption("Successfully borrowed the books. Please return it in time");
            }
        });
}

    }