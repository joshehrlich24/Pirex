
package nongui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * Searches the opuses using the inverted index to get documents.
 * 
 * @author Jake and Kylie and Mark
 *
 */
public class Search
{
  private Map<String, List<Posting>> invertedIndex;
  private List<Opus> opuses;
  private int opusNumber;

  /**
   * Constructor for a search.
   */
  public Search()
  {
    invertedIndex = Load.getStaticInvertedIndex();
    opuses = Load.getStaticOpuses();
  }

  /**
   * Using the search terms, looks through the inverted index for our search.
   * 
   * @param terms
   *          the search
   * @return a set of strings with the search term in them
   */
  public Set<String> searchTerms(String terms)
  {
    Set<String> shortForms = new HashSet<String>();
    String[] searchTerms = terms.split(" ");
    List<Posting> postings = null;
    for (String searchTerm : searchTerms)
    {
    	
    	if(searchTerm.contains("^"))
    	{
    		String[] splitTerm = searchTerm.split("\\^");
    		if(invertedIndex.containsKey(splitTerm[0].toLowerCase()))
    		{
    			List<Posting> posts = invertedIndex.get(splitTerm[0].toLowerCase());
    			for(Posting post : posts)
    			{
    					  
    				Opus opus = Load.opuses.get(post.getDocIdentifier());
    			  List<Integer> locations = post.getLocations();
    				List<Document> documents = new ArrayList<>();
    				for(Integer location : locations)
    				{  
    				  documents.add(opus.getDocuments().get(opus.findDocument(location)));
    				}
    				for(Document document : documents)
    		  	{
    				  String doc = document.getDocumentAsOneLine().toLowerCase();
    				  String checkTerm = "";
    				  for(String term : splitTerm)
    				  {
    					  checkTerm += term + " ";
    				  }
    				  checkTerm = checkTerm.trim();
    				  if(doc.contains(checkTerm)) 
    				  {
    					  shortForms.add(opus.shortForm(document.getDocumentNumber()));
    				  }
    						
    		    }
    		  }
    		}
    	}  
    	  
    	else if (invertedIndex.containsKey(searchTerm.toLowerCase()))
    	  postings = invertedIndex.get(searchTerm.toLowerCase());
      
      if(postings != null)
      {
        for (Posting post : postings)
        {
          Opus opus = opuses.get(post.getDocIdentifier());
          opusNumber = opus.getOpusNumber();
          for (int location : post.getLocations())
          {
            shortForms.add(opus.shortForm(opus.findDocument(location)));
          }
        }
      }
    }

    return shortForms;
  }

  /**
   * Gets the number of the opus.
   * 
   * @return an integer
   */
  public int getNumber()
  {
    return opusNumber;
  }

  /**
   * Gets the list of opuses.
   * 
   * @return a list
   */
  public List<Opus> getOpuses()
  {
    return opuses;
  }
  
  /**
   * Takes the list of stop words and removes then from a query. Allowing for less results.
   * 
   * @param text
   *          the original search query
   * @return a string without stop words
   */
  public String searchStopWords(String text)
  {
    String str = "";

    ArrayList<String> stopWordList = new ArrayList<>(
        Arrays.asList("a", "an", "and", "are", "but", "did", "do", "does", "for", "had", "has",
            "is", "it", "its", "of", "or", "that", "the", "this", "to", "were", "which", "with"));

    ArrayList<String> searchList = new ArrayList<>(Arrays.asList(text.split(" ")));

    for (int i = 0; i < searchList.size(); i++)
    {

      for (int j = 0; j < stopWordList.size(); j++)
      {
        if (stopWordList.get(j).toLowerCase().equals(searchList.get(i).toLowerCase()))
        {
          searchList.set(i, "");
        }
      }
    }

    if (searchList.size() > 100)
    {
      JOptionPane.showMessageDialog(null, "Search too large, try again",
          "Search too large, try again", JOptionPane.ERROR_MESSAGE);
      str = "";
    }
    else
    {
      for (int i = 0; i < searchList.size(); i++)
      {
        if (!searchList.get(i).equals(""))
          str += searchList.get(i) + " ";
      }
    }
    return str;
  }
}
