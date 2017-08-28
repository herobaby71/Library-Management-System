/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Shade
 */
public class BorrowedLogUI extends VerticalLayout{
    
    private User user = null;
    
    //UI Components
    private Label description = new Label();
    private Table borrowedTable = new Table();
    
    //Database Components
    private DatabaseManager db = new DatabaseManager();
    //allLogs will grab data from the database through DAO Databae Manager
    private List<borrowlog> allLogs = null;
    
    //Connect the borrowedTable Container with a list of borrowlog objects
    private BeanItemContainer<borrowlog> allLogsBean = new BeanItemContainer<borrowlog>(borrowlog.class);
    
    //Initialization requires user currently logged in. Not practical but works for now
    //Sets up the UI
    //Adds the data to the borrowedTable
    public BorrowedLogUI(User user)
    {
        this.user = user;
        setUpLabel();
        setUpTable();
       
        addComponent(description);
        addComponent(borrowedTable);
    }
    
    //Set up the label component of the UI
    public void setUpLabel()
    {
        description.setCaption("Your borrowed books:");
        description.setWidth("400px");
    }
    
    //Set up the table component of the UI, including the container, and the data in it
    public void setUpTable()
    {        
        borrowedTable.setColumnCollapsingAllowed(true);
        borrowedTable.setSelectable(true);
        
        borrowedTable.setContainerDataSource(allLogsBean);
        getLog();
        allLogsBean.addAll(allLogs);
    }
    
    //Grab data from the database and put it into allLogs List.
    public void getLog()
    {
        try{
            db.connectDB();
            allLogs = db.getBorrowedLog(user.id);
            db.closeDB();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
