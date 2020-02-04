package Pirex.src.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;


/**
 * Main driver for the GUI.
 * 
 * @author Kylie Davidson
 * @version 4/1/18
 *
 */
public class GUIDriver
{

  /**
   * Main Driver for the GUI. Calls the constructors for the tabs.
   * 
   * @param args
   *          not used
   */
  public static void main(String[] args)
  {
	  
	 
	    
    JFrame gui = new JFrame();
    java.net.URL imgUrl = GUIDriver.class.getResource("Pirex_Icon_16x16.png");
    ImageIcon img = new ImageIcon(imgUrl);
    JTabbedPane pane = new JTabbedPane();

    SearchTab search = new SearchTab();
    SumTab sum = new SumTab();
    LoadTab load = new LoadTab();
    OpusBrowser opusBrowser = new OpusBrowser();

    JMenuBar bar = new JMenuBar();
    JMenu options = new JMenu("Options");
    JMenu help = new JMenu("Help");
    
    JMenuItem docs = new JMenuItem("Documents");
    JMenuItem sources = new JMenuItem("Sources");
    JMenuItem about = new JMenuItem("About");
    about.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        JOptionPane helpwindow = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Load a document and search for words or phrases");
        
      }

    });

    options.add(docs);
    options.add(sources);
    help.add(about);
    bar.add(options);
    bar.add(help);

    pane.add(search, 0);
    pane.add(load, 1);
    pane.add(sum, 2);
    pane.add(opusBrowser, 3);

    pane.setTitleAt(0, "Search for Documents");
    pane.setTitleAt(1, "Load Documents");
    pane.setTitleAt(2, "Summarize Documents");
    pane.setTitleAt(3, "Browse Opus");
    
    String type = JOptionPane.showInputDialog("User or Administrator");
    type = type.toLowerCase();
    if(type.equals("user"))
    {
    	pane.remove(1);
    	load.setVisible(false);
    	
    }
    
    

    gui.add(pane);
    gui.setName("Pirex");
    gui.setTitle("Pirex");
    gui.setVisible(true);
    gui.setSize(800, 800);
    gui.setIconImage(img.getImage());
    gui.setJMenuBar(bar);
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}
