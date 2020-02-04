
package Pirex.src.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import nongui.Load;

/**
 * Load tab for the GUI.
 * 
 * @author Kylie Davidson
 * @version 4/1/18
 *
 */
public class LoadTab extends JPanel implements ActionListener
{

  private JTextArea textArea;
  private JTextField textFile;
  private JTextField titleField;
  private JTextField authorField;
  private JFileChooser files;
  private Load load;
  private OpusBrowser browseOpus;

  /**
   * Constructor for the Load Tab.
   */
  public LoadTab()
  {
    super();
    
    
    this.setBounds(0, 0, 800, 800);
    this.setLayout(null);

    load = new Load();
    browseOpus = new OpusBrowser();
   

    JPanel panel = new JPanel();
    panel.setBounds(0, 0, 775, 775);
    panel.setLayout(null);
    this.add(panel);
    JLabel lblTextFile = new JLabel("Text File: ");
    lblTextFile.setBounds(12, 13, 63, 16);
    panel.add(lblTextFile);

    textFile = new JTextField();
    textFile.setBounds(87, 10, 550, 22);
    panel.add(textFile);
    textFile.setColumns(10);

    JButton btnBrowse = new JButton("Browse");
    btnBrowse.setBounds(675, 9, 84, 25);
    panel.add(btnBrowse);
    btnBrowse.addActionListener(this);

    JLabel lblTextFileType = new JLabel("Text File Type: ");
    lblTextFileType.setBounds(12, 53, 90, 16);
    panel.add(lblTextFileType);

    JComboBox<String> comboBox = new JComboBox<String>();
    comboBox.setBounds(114, 50, 600, 22);
    comboBox.addItem("Project Gutenberg File");
    comboBox.addItem(".txt File");
    comboBox.addItem(".pdf File");
    comboBox.addItem(".doc File");
    panel.add(comboBox);

    JLabel lblTitle = new JLabel("Title: ");
    lblTitle.setBounds(12, 92, 56, 16);
    panel.add(lblTitle);

    titleField = new JTextField();
    titleField.setBounds(53, 92, 300, 22);
    panel.add(titleField);
    titleField.setColumns(10);

    JLabel lblAuthor = new JLabel("Author: ");
    lblAuthor.setBounds(400, 95, 56, 16);
    panel.add(lblAuthor);

    authorField = new JTextField();
    authorField.setBounds(450, 92, 300, 22);
    panel.add(authorField);
    authorField.setColumns(10);

    JButton btnProcess = new JButton("Process");
    btnProcess.setBounds(12, 150, 97, 25);
    panel.add(btnProcess);
    btnProcess.addActionListener(this);
    JButton remove = new JButton("Remove");
    remove.setBounds(400, 150, 97, 25);
    panel.add(remove);
    remove.addActionListener(this);

    textArea = new JTextArea();
    textArea.setBounds(12, 229, 750, 450);
    textArea.setForeground(Color.BLACK);
    panel.add(textArea);

  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Browse"))
    {
      try
      {
        createJFileChooser();
      }
      catch (IOException ioe)
      {
        System.out.print("File not found");
      }
    }
    if (e.getActionCommand().equals("Process") && load.getLoaded())
    {
      load.getOpus().setAuthor(authorField.getText());
      textArea.setText("");
      authorField.setText("");
      titleField.setText("");
      textFile.setText("");
      load.createSummary();
      textArea.setText(load.getOpus().getSummary());
      
       browseOpus.openOpus(load.getOpus());

    }
    if (e.getActionCommand().equals("Remove"))
    {
      load.remove();
    }
  }

  /**
   * Creates a file chooser.
   * 
   * @throws IOException
   *           if an exception is thrown
   */
  public void createJFileChooser() throws IOException
  {
    JFrame fileFrame = new JFrame();
    fileFrame.setSize(1, 1);
    files = new JFileChooser();
    fileFrame.add(files);
    fileFrame.setVisible(true);

    int result = files.showOpenDialog(this);

    if (result == JFileChooser.CANCEL_OPTION)
    {
      fileFrame.dispose();
    }
    if (result == JFileChooser.APPROVE_OPTION)
    {
      File file = files.getSelectedFile();
      load.parseFile(file);
      authorField.setText(load.getOpus().getAuthor());
      titleField.setText(load.getOpus().getTitle());
      textFile.setText(load.getOpus().getFilename());
      fileFrame.dispose();
    }
  }
}
