/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tom.fryeburgacademy.librarymanager;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Shade
 */
public class VerticalMenuBarUI extends VerticalLayout{
    private Label Label1 = new Label("Option 1");
    private Label Label2 = new Label("Option 2");
    private Label Label3 = new Label("Option 3");
    
    public VerticalMenuBarUI()
    {
        addComponent(Label1);
        addComponent(Label2);
        addComponent(Label3);
        
        addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(LayoutClickEvent event) {
                // capture click and do whatever you'd like to do
                if (event.getClickedComponent() != null) {
                    if (event.getClickedComponent().equals(Label1))
                    {
                                                
                    }
                    if (event.getClickedComponent().equals(Label2))
                    {
                        
                    }
                    if (event.getClickedComponent().equals(Label3))
                    {
                        
                    }
                }
            }
        });
        
        //align the Components
        alignComponents();
    }
    public void alignComponents()
    {
        setMargin(true);
    }
}
