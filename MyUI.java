package tom.fryeburgacademy.librarymanager;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;


/**
 *
 */
@Theme("mytheme")
@Widgetset("com.mycompany.librarymanager.MyAppWidgetset")
public class MyUI extends UI {
    
    final VerticalLayout mainLayout = new VerticalLayout();
    private TextField userText = new TextField("user");
    private TextField password = new TextField("password");
    private Button loginButton = new Button("login");
    private Label loginLabel = new Label("");
    
    private DatabaseManager db = new DatabaseManager();
    
    protected void init(VaadinRequest vaadinRequest)
    {

        //MAin login page
        setLoginLayout();
        setContent(mainLayout);        
        
    }
    
    //Login Layout - Connect to database through DAO to verify the Username and password the user enters in the text fields
    public void setLoginLayout()
    {   
        mainLayout.removeAllComponents();
        //Ctrl + space: show choices
        //Set up the layout
        mainLayout.addComponent(userText);
        mainLayout.addComponent(password);
        mainLayout.addComponent(loginButton);
        mainLayout.addComponent(loginLabel);
        
        userText.setValue("Mushi.Mushi");
        password.setValue("123456");
        //Set the login button on listening mode
                
        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event/*,final VaadinRequest request*/)
            {
                try{
                    User user = null;
                    db.connectDB();
                    user = db.verifyUserIDPassword(userText.getValue(), password.getValue());
                    db.closeDB();
                    if(user!=null)
                    {
                        //goToManageUI(request);
                        mainLayout.removeAllComponents();
                        ManageUI x = new ManageUI(user);
                        mainLayout.addComponent(x);
                    }
                    else
                    {
                        loginLabel.setCaption("username or password does not exis");
                    }
                }catch (SQLException ex){
                    ex.printStackTrace();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        );
        
        //setComponentAlignment(user,Alignment.MIDDLE_CENTER);
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
