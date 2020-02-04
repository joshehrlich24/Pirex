package nongui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object that stores information about an Opus.
 * 
 * @author Mark Sizemore, Jake and Kylie 
 *
 */
public class Opus
{
  private String author;
  private String title;
  private String filename;
  private String summary;
  private int documentCount;
  private int opusNumber;
  private File source;
  private List<Document> documents;
  private Map<String, List<Integer>> indexTerms;

  /**
   * Default Constructor.
   */
  public Opus()
  {
	  author = "";
	  title = "";
	  filename = "";
	  summary = "";
	  documentCount = 0;
	  opusNumber = 0;
	  source = null;
	  documents = new ArrayList<>();
	  indexTerms = new HashMap<>();
  }

  /**
   * Find document number of given word number.
   * 
   * @param wordNum
   *          the placement of the word in that document.
   * @return document number
   */
  public int findDocument(int wordNum)
  {
    int cur = 0;

    for (int i = 0; i < documents.size(); i++)
    {
      Document doc = documents.get(i);
      
      if (cur + doc.size() - 1 < wordNum)
      {
        cur += doc.size();
      }
      else
      {
        cur = i;
        break;
      }
      
    }
    return cur;
  }

  /**
   * Generate short form display.
   * 
   * @param docNum
   *          the number of the document in the opus
   * @return short form display
   */
  public String shortForm(int docNum)
  {
    return author + " " + title + " " + docNum + " " + this.getDocument(docNum).getFirstLine();
  }
  
  /**
   * Generate the long form display.
   * 
   * @return long form display
   */
  public String longForm()
  {
    return "Opus " + opusNumber + ": " + author + " " + title + " " + getDocumentCount() 
            + " documents\n\t" + source.getAbsolutePath() + "\n";
  }

  /**
   * Add a document to the list of documents.
   * 
   * @param doc
   *          is the document to add
   */
  public void addDocument(Document doc)
  {
    documents.add(doc);
  }

  /**
   * Sets the author for the Opus.
   * 
   * @param author
   *          for setting
   */
  public void setAuthor(String author)
  {
    this.author = author;
  }
  
  /**
   * Set the file for the opus.
   * 
   * @param file for the opus
   */
  public void setFile(File file)
  {
    source = file;
  }

  /**
   * Set the title for an opus.
   * 
   * @param title
   *          for the Opus
   */
  public void setTitle(String title)
  {
    this.title = title;
  }

  /**
   * Set the filename for the opus.
   * 
   * @param filename
   *          for the opus
   */
  public void setFilename(String filename)
  {
    this.filename = filename;
  }

  /**
   * Sets the document count.
   * 
   * @param documentCount
   *          to set
   */
  public void setDocumentCount(int documentCount)
  {
    this.documentCount = documentCount;
  }

  /**
   * Set the Opus number.
   * 
   * @param opusNumber
   *          number to set.
   */
  public void setOpusNumber(int opusNumber)
  {
    this.opusNumber = opusNumber;
  }

  /**
   * Sets the Index Terms.
   * 
   * @param indexTerms
   *          index term to set
   */
  public void setIndexTerms(Map<String, List<Integer>> indexTerms)
  {
    this.indexTerms = indexTerms;
  }

  /**
   * Sets the Summary.
   * 
   * @param summary
   *          Summary to set
   */
  public void setSummary(String summary)
  {
    this.summary = summary;
  }

  /**
   * Getter for the author.
   * 
   * @return the String for author
   */
  public String getAuthor()
  {
    return author;
  }

  /**
   * Getter for the title.
   * 
   * @return return the string title.
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * Getter for the filename.
   * 
   * @return the string filename
   */
  public String getFilename()
  {
    return filename;
  }

  /**
   * Getter for the document count.
   * 
   * @return the document count
   */
  public int getDocumentCount()
  {
    return documentCount;
  }

  /**
   * Get the specified document.
   * 
   * @param num is the number docment to get
   * @return specified document
   */
  public Document getDocument(int num)
  {
    return documents.get(num);
  }
  
  /**
   * Retrieve the file of the opus.
   * 
   * @return source The file of the opus.
   */
  public File getFile()
  {
    return source;
  }

  /**
   * Getter for the opus number.
   * 
   * @return the int
   */
  public int getOpusNumber()
  {
    return opusNumber;
  }

  /**
   * Get short form of specified document.
   * 
   * @param docNum is the short form version of a document
   * @return short form string of specified document.
   */
  public String toShortForm(int docNum)
  {
    return author + " " + title + " " + docNum + " " + "Test";
  }

  /**
   * Getter for the Index terms.
   * 
   * @return the map index
   */
  public Map<String, List<Integer>> getIndexTerms()
  {
    return indexTerms;
  }

  /**
   * Returns the summary for this Opus.
   * 
   * @return summary The Opus summary
   */
  public String getSummary()
  {
    return summary;
  }
  
  /**
   * Returns the list of documents for this opus.
   * 
   * @return document the list of documents for this opus.
   */
  public List<Document> getDocuments()
  {
	  return documents;
  }

}
