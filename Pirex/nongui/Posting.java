package nongui;

import java.util.List;

/**
 * Class which has the list of integers which indicate the index of each occurrence of that word.
 * 
 * @author Mark Sizemore
 *
 */
public class Posting
{
  private int docIdentifier;
  private List<Integer> locations;

  /**
   * Constructor for the posting.
   * 
   * @param docIdentifier
   *          the id number
   * @param locations
   *          where they are located
   */
  public Posting(int docIdentifier, List<Integer> locations)
  {
    this.docIdentifier = docIdentifier;
    this.locations = locations;
  }

  /**
   * Getter for the Document Id.
   * 
   * @return an integer
   */
  public int getDocIdentifier()
  {
    return docIdentifier;
  }

  /**
   * Getter for the locations.
   * 
   * @return an integer
   */
  public List<Integer> getLocations()
  {
    return locations;
  }

  /**
   * Setter for the locations.
   * 
   * @param locations
   *          of the words
   */
  public void setLocations(List<Integer> locations)
  {
    this.locations = locations;
  }
}
