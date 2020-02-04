package Pirex.src.gui;


import java.awt.Color;

import javax.swing.*;


import nongui.Opus;

/**
 * Tab to have an option to read a section or whole opus.
 * 
 * @author Kylie Davidson
 *
 */
public class OpusBrowser extends JPanel
{
  private JTextArea opusBrowse;
  private String words;
  private JFileChooser files;
  
  
  /**
   * Create a tab for the Opus Browser.
   */
  public OpusBrowser() 
  {
    super();
    
    words = "";
    opusBrowse = new JTextArea( 50, 50);
    JScrollPane scroll = new JScrollPane(opusBrowse);
    this.add(scroll);
   
    opusBrowse.setLineWrap(true);
    opusBrowse.setEditable(false);
    
    
    
    
    this.add(opusBrowse);
    opusBrowse.setVisible(true);
    this.setVisible(true);
    

  }
  
  public void openOpus(Opus opus)
	{		
		for(int i = 0; i < opus.getDocumentCount(); i ++)
		{
			words += opus.getDocument(i).getDocument();
		}
		setOpusArea(words);
	}
  
  public void setOpusArea(String str)
	{
	  opusBrowse.setText(str);
	  //System.out.println(opusBrowse.isDisplayable());
	  //System.out.println(opusBrowse.getText());
	  opusBrowse.setSelectedTextColor(Color.BLACK);
	}
	
	public JTextArea getOpusArea()
	{
		return opusBrowse;
	}

}
