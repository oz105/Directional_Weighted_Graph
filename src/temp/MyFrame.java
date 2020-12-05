package temp;

import java.awt.Color;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame implements ActionListener
{
    private MenuItem menuItem1;
    private MenuItem menuItem2;

    public MyFrame()
    {
        initFrame();
        addMenu();
    }

    private void initFrame()
    {
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void addMenu()
    {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menuBar.add(menu);
        this.setMenuBar(menuBar);

        menuItem1 = new MenuItem("Item 1");
        menuItem1.addActionListener(this);

        menuItem2 = new MenuItem("Item 2");
        menuItem2.addActionListener(this);

        menu.add(menuItem1);
        menu.add(menuItem2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()== menuItem1)
        {
            System.out.println("menuItem1 clicked");
        }
        else if(e.getSource()==menuItem2)
        {
            System.out.println("menuItem2 clicked");
        }
    }


}
