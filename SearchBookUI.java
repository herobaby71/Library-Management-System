/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Shade
 */
public class SearchBookUI extends VerticalLayout {
    
    //
    private BeanItemContainer<Book> allBooksBean = new BeanItemContainer<Book>(Book.class);

    //books that user want to borrow
    private List<Book> basket = new ArrayList<>();
    private List<Book> allBooks = null;
    
    //All Components in the UI
    private Table bookTable = new Table("Books Table");
    private Button searchButton = new Button();
    private Button addBookButton = new Button();
    private TextField searchText = new TextField();
    private Label errorLabel = new Label("");
    
    private HorizontalLayout searchBar = new HorizontalLayout();
    
    //Database manager to get the data
    private DatabaseManager db = new DatabaseManager();

  
    //Setting up the SearchBookUI
    public SearchBookUI()
    {        
        
        addComponent(searchBar);
        addComponent(bookTable);
        addComponent(addBookButton);
        addComponent(errorLabel);
        //Set button on listening mode
        setUpSearchBar();

        //Textfield
        searchText.setInputPrompt("Search");
        
        //Set all the table attributes
        setUpTable();
        
        //Set up add button
        setUpAddBookButton();
        
    }
    
    //get the basket
    public List<Book> getBasket()
    {
        return this.basket;
    }
    
    //Set up the search bar
    private void setUpSearchBar()
    {
        searchBar.addComponent(searchText);
        searchBar.addComponent(searchButton);
        
        setUpSearchButton();
        setUpSearchText();
    }
    
    //Set up the table in all possible ways
    private void setUpTable()
    {
        //Setting the outlook of the table
        bookTable.setColumnReorderingAllowed(true);
        bookTable.setColumnCollapsingAllowed(true);
        
        
        bookTable.setContainerDataSource(allBooksBean);
        
        
        //Setting up the table data row and column
        /*bookTable.addContainerProperty("id", Integer.class, null);
        bookTable.addContainerProperty("book name",String.class, null);
        bookTable.addContainerProperty("author name", String.class, null);
        bookTable.addContainerProperty("description", String.class, null);
        bookTable.addContainerProperty("book genres", String.class, null);
        */
        //The initial values in the table    
        db.connectDB();
        try{
            allBooks = db.getAllBooks();
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        db.closeDB();
        allBooksBean.addAll(allBooks);
        
        //Set Visible columns (show certain columnes)
        bookTable.setVisibleColumns(new Object[]{"id","bookName", "authorName", "description" ,"bookGenreString"});
        
        //Set height and width
        bookTable.setHeight("370px");
        bookTable.setWidth("1000px");
        
        //Allow the data in the table to be selected
        bookTable.setSelectable(true);
        
        //Save the selected row by saving the change Immediately
        bookTable.setImmediate(true);
        //Set the table on listener for value change
        bookTable.addValueChangeListener(new Property.ValueChangeListener()
        {
                public void valueChange(ValueChangeEvent event) {
                
                }

        });
    }
    
    //Set the searchButton on listener to search for data
    private void setUpSearchButton()
    {
        searchButton.setCaption("Go");
        searchButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event)
            {
                //Grab the data from the database base on the search text
                //Assume all search is author name for now
                String searchValue = searchText.getValue();
                
                //Remove all values in the table
                bookTable.removeAllItems();
                
                //Grab Data from database
                List<Book> books = collectDataFromDatabase(searchValue);
                
                //Put the data into the table
                allBooksBean.addAll(books);
                //putDataIntoTable(books);
            }
        });
    }  
    
    //Set up add book button
    private void setUpAddBookButton()
    {
        setComponentAlignment(addBookButton, Alignment.BOTTOM_LEFT);
        addBookButton.setCaption("Add");
        addBookButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event)
            {
                Object id = bookTable.getValue();
                int book_id = (int)bookTable.getContainerProperty(id,"id").getValue();
                basket.add(allBooksBean.getIdByIndex(book_id-1));
                //basket.add(allBooks.get(book_id-1));
                errorLabel.setCaption("Successfully added book to Basket");
            }
        });
    }
    //Set the searchTextField
    private void setUpSearchText()
    {
        //Allow the textField to be null
        searchText.setNullSettingAllowed(true);
        
        //Change the vaue of the textField immediately
        searchText.setImmediate(true);
    }
    
    private List<Book> collectDataFromDatabase(String searchString)
    {
        List<Book> books = new ArrayList<>();
        try{
            db.connectDB();
            books = db.getBooksByKeyWord(searchString);
            db.closeDB();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return books;
    }    
      
    //The following method grabbing the data from the database and add it the the table container
    private void putDataIntoTable(List<Book> books)
    {
        for(int i = 0; i<books.size(); i++)
        {
            bookTable.addItem(new Object[]{books.get(i).getId(), books.get(i).getBookName(), books.get(i).getAuthorName(), books.get(i).getDescription(), books.get(i).getBookGenreString()}, i+1);
        }
    }

}
