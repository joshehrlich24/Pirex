package Pirex.src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nongui.Load;
import nongui.Opus;
import nongui.Search;

/**
 * Tab for Search for Documents.
 * 
 * @author Kylie Davidson
 * @version 3/29/18
 *
 */
public class SearchTab extends JPanel
{
  private static final Object CLEAR = "Clear";
  private ArrayList<String> searches;
  private Search searcher;
  private Opus opus;
  private ActionListener listener;
  private JComboBox<String> query = new JComboBox<>();
  private JScrollPane scrollPane;
  private JScrollPane shortResScrollPane;
  private JTextArea documentsRet;
  private JList list;
  private ArrayList<String> shortFormResults;
  private JSplitPane split3;

  /**
   * Constructor for the Search Tab. Called from the GUI Driver.
   */
  public SearchTab()
  {
    super();

    super.setLayout(new BorderLayout());

    searches = new ArrayList<>();
    opus = new Opus();
    JTextArea longFormRes;
    JButton button = new JButton("Clear");
    ButtonGroup buttons = new ButtonGroup();
    JButton search = new JButton("Search");
    JSplitPane split = new JSplitPane();
    split.setEnabled(false);
    JSplitPane split2 = new JSplitPane();
    split2.setEnabled(false);
    JSplitPane split4 = new JSplitPane();
    split4.setEnabled(false);
    split3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    split3.setEnabled(false);
    buttons.add(button);
    buttons.add(search);

    query = new JComboBox<String>();
    query.setEditable(true);

    JLabel label = new JLabel();
    label.setText("Query:  ");
    split2.setLeftComponent(label);
    split2.setRightComponent(query);
    split.setLeftComponent(split2);
    split.setDividerLocation(650);
    split.setRightComponent(button);
    this.add(split, BorderLayout.NORTH);

    split3.setOrientation(JSplitPane.VERTICAL_SPLIT);
    split3.setDividerLocation(325);

    longFormRes = new JTextArea();
    documentsRet = new JTextArea("Retrieved: ");

    split4.setOrientation(JSplitPane.VERTICAL_SPLIT);
    split4.setDividerLocation(20);
    split4.setTopComponent(documentsRet);
    split4.setBottomComponent(longFormRes);
    split3.setBottomComponent(split4);
    scrollPane = new JScrollPane(longFormRes);
    scrollPane.setForeground(Color.BLACK);
    split4.setBottomComponent(scrollPane);

    longFormRes.setEnabled(false);
    split3.setBottomComponent(split4);
    shortResScrollPane = new JScrollPane(list);

    split3.add(shortResScrollPane, JSplitPane.TOP);
    split3.add(split4, JSplitPane.BOTTOM);
    split4.setForeground(Color.BLACK);

    split3.setForeground(Color.BLACK);

    this.add(split3);
    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        query.setSelectedItem("");
        longFormRes.setText("");
        list = new JList();
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        shortResScrollPane = new JScrollPane(list);
        split3.add(shortResScrollPane, JSplitPane.TOP);
        shortFormResults = new ArrayList<String>();

      }
    });

    this.setVisible(true);

    query.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
    	  String str = (String) query.getSelectedItem();
        if (!query.getSelectedItem().equals(""))
        {
          if (!searches.contains(str))
          {
            searches.add(str);
            query.addItem(str);
          }
          for (int i = 0; i < searches.size(); i++)
          {
            if (searches.get(i).startsWith((String) query.getSelectedItem()))
            {
              query.setSelectedItem(searches.get(i));
              //str = searches.get(i);
            }
          }

          searcher = new Search();
          String noStopWords = searcher.searchStopWords(str);
          Set<String> strings = null;

          if (Load.invertedIndex.size() > 0)
          {
            strings = searcher.searchTerms(str);
            shortFormResults = new ArrayList<String>();
            list = new JList();
            documentsRet.setText("Retrieved " + strings.size() + " documents");
            Iterator it = strings.iterator();
            while (it.hasNext())
            {
              String temp = (String) it.next();
              shortFormResults.add(temp);
              it.remove();
            }
            list = new JList(shortFormResults.toArray());
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            shortResScrollPane = new JScrollPane(list);
            split3.add(shortResScrollPane, JSplitPane.TOP);
            list.addListSelectionListener(new ListSelectionListener()
            {

              @Override
              public void valueChanged(ListSelectionEvent arg0)
              {
                JList listtemp = (JList) arg0.getSource();
                Object[] selected;
                selected = listtemp.getSelectedValues();
                String temp = "";
                for (int i = 0; i < selected.length; i++)
                {
                  temp = (String) selected[i];
                }
                String value = temp.replaceAll("[^0-9]", "");
                
                if(searcher.getOpuses().size() > 0)
                	opus = searcher.getOpuses().get(searcher.getNumber() - 1);
                String test = opus.getDocument(Integer.parseInt(value)).getDocument();
                longFormRes.setForeground(Color.BLACK);
                longFormRes.setText(test);
              }
            });

          }
        }
      }
    });

  }
}
