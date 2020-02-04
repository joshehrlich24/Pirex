package nongui;

import java.util.ArrayList;
import java.util.List;

/**
 * Documents are paragraphs in bigger texts called opuses.
 * 
 * @author Jake and Kylie
 *
 */
public class Document
{

  private List<String> lines;
  private int words;
  private int documentNumber;
  
  /**
   * Constructor for a document.
   */
  public Document()
  {
    lines = new ArrayList<>();
    words = 0;
    documentNumber = 0;
  }

  /**
   * Adds a line of strings to the document.
   * 
   * @param line
   *          string to add
   */
  public void add(String line)
  {
    lines.add(line);
    String[] sentence = line.split("\\s");
    words += sentence.length;
  }

  /**
   * Retrieves the first line of a document.
   * 
   * @return the first line
   */
  public String getFirstLine()
  {
    return lines.get(0);
  }

  /**
   * Gets the document.
   * 
   * @return a string document
   */
  public String getDocument()
  {
    String doc = "";
    for (String line : lines)
    {
      doc += "\n" + line;
    }
    return doc;
  }

  /**
   * Gets the number of words in a document.
   * 
   * @return an integer
   */
  public int size()
  {
    return words;
  }
  
  /**
   * Returns the number of lines in the document.
   * 
   * @return number of lines in the document
   */
  public int length()
  {
    return lines.size();
  }
  
  /**
   * Clears the documents.
   * 
   */
  public void clear()
  {
    lines.clear();
  }
  
  /**
   * Gets the document sans new line characters.
   * 
   * @return a string document
   */
  public String getDocumentAsOneLine()
  {
    String doc = "";
    for (String line : lines)
    {
      doc += line + " ";
    }
    doc = doc.trim();
    return doc;
  }

  /**
   * Sets the number of this document.
   * 
   * @param count the number to assing this document.
   */
  public void setDocumentNumber(int count)
  {
	  this.documentNumber = count;
  }
  
  /**
   * Returns the number for this document.
   * 
   * @return documentNumber the number for this document.
   */
  public int getDocumentNumber()
  {
	  return documentNumber;
  }
}
