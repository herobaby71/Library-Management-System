/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import java.util.List;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
/**
 *
 * @author Shade
 */
@Theme("bookManager")
@Widgetset("valo")
public class ManageUI extends VerticalLayout{
    //Top menu bar
    private MenuBar barMenu = new MenuBar();

    
    //Two Vertical Layout on the Left and Right sides of the screen
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private VerticalLayout leftMenuBar = new VerticalMenuBarUI();
    private VerticalLayout controlLayout = null;
    
    // Database manager to grab data from SQL
    private DatabaseManager db = new DatabaseManager();
    
    //The user that using the UI
    private User user = null;
    
    //initializing the Main UI:
    //The controlLayout can be swapped into another UI components
    public ManageUI(User user)
    {
        //set up the user account using the program
        this.user = user;
        setImmediate(true);
        addComponent(barMenu);
        createbarMenu();
        
        addComponent(mainLayout);
        mainLayout.setWidth("500px");
        mainLayout.addComponent(leftMenuBar);
        controlLayout = new SearchBookUI();
        mainLayout.addComponent(controlLayout);
    }
    
    //Set up the menubar. commands (Listener)
    //Set up Menu items.
    private void createbarMenu()
    {
        barMenu.setVisible(true);
        barMenu.setWidth("100%");
        
        MenuBar.Command mycommand = new MenuBar.Command()
        {
            public void menuSelected(MenuItem selectedItem)
            {
                if (selectedItem.getText().contains("logout"))
                {
                    ((MyUI)getUI()).setLoginLayout();
                }
                if (selectedItem.getText().contains("Library"))
                {
                    mainLayout.removeComponent(controlLayout);
                    controlLayout = null;
                    controlLayout = new SearchBookUI();
                    mainLayout.addComponent(controlLayout);
                }
                if(selectedItem.getText().contains("BorrowedLog"))
                {
                    mainLayout.removeComponent(controlLayout);
                    controlLayout = null;
                    controlLayout = new BorrowedLogUI(user);
                    mainLayout.addComponent(controlLayout);                    
                }
                if(selectedItem.getText().contains("Profile"))
                {
                    
                }
                if(selectedItem.getText().contains("See Basket"))
                {
                    
                    List<Book> basket = ((SearchBookUI)controlLayout).getBasket();
                    mainLayout.removeComponent(controlLayout);
                    controlLayout = null;
                    controlLayout = new SeeBasketUI(basket, user);
                    mainLayout.addComponent(controlLayout);
                    
                }
                
            }
        };
      
        MenuItem logout = barMenu.addItem("logout", mycommand);
        MenuItem library = barMenu.addItem("Library", mycommand);
        MenuItem borrowLoged = barMenu.addItem("BorrowedLog", mycommand);
        MenuItem accountProfile = barMenu.addItem("settings", null);
        accountProfile.addItem("Profile", mycommand);
        accountProfile.addItem("See Basket", mycommand);

        MenuItem welcomeSign = barMenu.addItem((user.getAuthority()==1)?"Welcome user " + user.getUserName():"Welcome admin" + user.getUserName(), null);
        
    }    
    
}
