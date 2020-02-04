package Pirex.src.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import nongui.Opus;

/**
 * Summarize Tab for the gui.
 * 
 * @author Kylie Davidson
 * @version 4/1/18
 *
 */
public class SumTab extends Container
{
  public static JTextArea longFormRes = new JTextArea();
  private static ArrayList<String> longSummaries = new ArrayList<>();
  /**
   * Creates the Summarize Tab for the GUI. Scroll bar is activated when there is enough text to
   * need scrolling to occur.
   */
  public SumTab()
  {
    super();
    this.setLayout(new BorderLayout());
    JScrollPane scrollPane;

    longFormRes.setEditable(false);
    scrollPane = new JScrollPane(longFormRes);
    scrollPane.setForeground(Color.BLACK);

    this.add(scrollPane, BorderLayout.CENTER);
    this.setVisible(true);
  }
  
  /**
   * Adds a summary to the summary tab.
   * 
   * @param opus The opus whose summary to add.
   * @param indexTerms The number of indexTerms.
   * @param postings The nuber of postings.
   */
  public static void addSummary(Opus opus, int indexTerms, int postings)
  {
    longSummaries.add(opus.longForm());
    longFormRes.setText(longSummaries.get(0));
    for (int i = 1; i < longSummaries.size(); i++)
    {
      longFormRes.setText(longFormRes.getText() + longSummaries.get(i));
    }
    longFormRes.setText(longFormRes.getText() + "\n" + "Index terms: " + indexTerms
                          + "\nPostings: " + postings);
  }
}
