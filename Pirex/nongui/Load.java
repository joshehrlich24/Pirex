package nongui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Pirex.src.gui.SumTab;

/**
 * Loads in an Opus.
 * 
 * @author Mark Sizemore
 */

public class Load
{
  public static Map<String, List<Posting>> invertedIndex;
  public static List<Opus> opuses;
  private String summary;
  private int opusNumber;
  private boolean loaded;
  private List<String> lines;
  private Opus opus;
  private int numNewPostings;
  private int newTerms;
  private int numberOfPostings;

  /**
   * Initializes class variables.
   */
  public Load()
  {
    opusNumber = 0;
    numNewPostings = 0;
    loaded = false;
    opus = new Opus();
    opuses = new ArrayList<>();
    lines = new ArrayList<>();
    invertedIndex = new HashMap<>();
    newTerms = 0;
  }

  /**
   * Returns the inverted index.
   * 
   * @return invertedIndex the inverted index.
   */
  public static Map<String, List<Posting>> getStaticInvertedIndex()
  {
    return invertedIndex;
  }

  /**
   * Returns the list of opuses.
   * 
   * @return opuses The list of opuses.
   */
  public static List<Opus> getStaticOpuses()
  {
    return opuses;
  }

  /**
   * Makes a summary to display when an Opus is loaded.
   */
  public void createSummary()
  {
    opuses.add(opus);
    summary = "Opus: " + opus.getFilename() + "\n";
    summary += "Title: " + opus.getTitle() + "\n";
    summary += "Author: " + opus.getAuthor() + "\n";
    summary += "Opus size: " + opus.getDocumentCount() + " documents\n";
    summary += "Opus number: " + opusNumber + "\n";
    summary += "New index terms: " + newTerms + "\n";
    summary += "New postings: " + numNewPostings + "\n";
    summary += "Total index terms: " + invertedIndex.size() + "\n";
    numberOfPostings = 0;
    for (Map.Entry<String, List<Posting>> entry : invertedIndex.entrySet())
    {
      numberOfPostings += entry.getValue().size();
    }
    summary += "Total postings: " + numberOfPostings;
    SumTab.addSummary(opus, invertedIndex.size(), numberOfPostings);
    opusNumber++;
    opus.setOpusNumber(opusNumber);
    opus.setSummary(summary);
  }

  /**
   * Parses the file.
   * 
   * @param file the text file containing the Opus
   * @throws IOException if an exception is thrown.
   */
  public void parseFile(File file) throws IOException
  {
    Document document = new Document();
    lines.clear();
    newTerms = 0;
    numNewPostings = 0;
    Map<String, List<Integer>> indexTerms = new HashMap<>();
    boolean started = false;
    boolean ended = false;
    int documentCount = 0;
    BufferedReader in;
    int position = 0;

    opus.setFilename(file.getAbsolutePath());
    in = new BufferedReader(new FileReader(file.getAbsolutePath()));

    String line = in.readLine();
    for (int i = 0; line != null; i++)
    {
      findAuthor(line, started);
      findTitle(line, started);

      if (i > 0)
      {
        if (lines.get(i - 1).startsWith("***") && lines.get(i - 1).contains("START"))
        {
          started = true;
        }
        if (line.startsWith("End of the Project Gutenberg EBook")
            || (line.startsWith("***") && line.contains("END")))
        {
          ended = true;
          opus.addDocument(document);
          document.setDocumentNumber(opus.getDocuments().size() - 1);
          document = new Document();
        }
        if ((lines.get(i - 1).isEmpty() && !line.isEmpty()))
        {
          if (started && !ended)
          {
            opus.setDocumentCount(++documentCount);
            opus.addDocument(document);
            document.setDocumentNumber(opus.getDocuments().size() - 1);
            document = new Document();
          }
        }
      }

      position = fillIndex(started, ended, line, indexTerms, position);

      lines.add(line);
      /*
       * This now will not load in the prologue to the Opus (author, title, project gutenberg
       * ebook...etc), will only load in the actual paragraphs of the Opus
       */
      if (started && !ended && !line.isEmpty())
        document.add(line);
      line = in.readLine();
    }
    opus.setIndexTerms(indexTerms);
    opus.setFile(file);
    loaded = true;
    in.close();
  }

  /**
   * Gets the title of the Opus.
   * 
   * @param line the current line of the file
   * @param started true if the Opus has started
   */
  public void findTitle(String line, boolean started)
  {
    if (line.startsWith("Title") && !started)
    {
      String[] array = line.split(" ", 2);
      opus.setTitle(array[1]);
    }
  }

  /**
   * Gets the author of the Opus.
   * 
   * @param line the current line of the file
   * @param started true if the Opus has started
   */
  public void findAuthor(String line, boolean started)
  {
    if (line.startsWith("Author") && !started)
    {
      String[] array = line.split(" ", 2);
      opus.setAuthor(array[1]);
    }
  }

  /**
   * Populates the inverted index.
   * 
   * @param started true if the Opus has started
   * @param ended true if the Opus has ended
   * @param line the current line of the text file
   * @param indexTerms the mapping of index terms to it's positions in this Opus
   * @param position the current word in the Opus we are on
   * @return newPosition the updated word in the Opus we are on
   */
  public int fillIndex(boolean started, boolean ended, String line,
      Map<String, List<Integer>> indexTerms, int position)
  {
    int newPosition = position;
    if (started && !ended)
    {
      String[] lineSplit = line.split(" ");
      for (int j = 0; j < lineSplit.length; j++)
      {
        StringBuilder sb = new StringBuilder(lineSplit[j]);
        if (lineSplit[j].length() > 0)
        {
          Character firstChar = sb.charAt(0);
          if (!Character.isLetter(firstChar))
          {
            sb.deleteCharAt(0);
            lineSplit[j] = sb.toString();
          }
        }
        if (lineSplit[j].length() >= 1)
        {
          Character character = sb.charAt(lineSplit[j].length() - 1);
          if (!Character.isLetter(character))
          {
            sb.deleteCharAt(lineSplit[j].length() - 1);
            lineSplit[j] = sb.toString();
          }
        }
        if (!invertedIndex.containsKey(lineSplit[j].toLowerCase()) && !lineSplit[j].isEmpty())
        {
          List<Posting> postings = new ArrayList<>();
          List<Integer> positions = new ArrayList<Integer>();
          positions.add(newPosition);
          postings.add(new Posting(opusNumber, positions));
          invertedIndex.put(lineSplit[j].toLowerCase(), postings);
          indexTerms.put(lineSplit[j].toLowerCase(), positions);
          numNewPostings++;
          newTerms++;
        } else if (invertedIndex.containsKey(lineSplit[j].toLowerCase()) && !lineSplit[j].isEmpty())
        {
          List<Posting> postings = invertedIndex.get(lineSplit[j].toLowerCase());
          List<Posting> newPostings = new ArrayList<>();
          for (int i = 0; i < postings.size(); i++)
          {
            if (postings.get(i).getDocIdentifier() == opusNumber)
            {
              List<Integer> locations = postings.get(i).getLocations();
              locations.add(position);
              Posting newPost = postings.get(i);
              newPost.setLocations(locations);
              newPostings.add(newPost);
            } else
            {
              List<Integer> positions = new ArrayList<Integer>();
              positions.add(position);
              List<Posting> posts = invertedIndex.get(lineSplit[j].toLowerCase());
              posts.add(new Posting(opusNumber, positions));
              invertedIndex.put(lineSplit[j].toLowerCase(), posts);
              indexTerms.put(lineSplit[j].toLowerCase(), positions);
              numNewPostings++;
            }
          }
        }
        if (!lineSplit[j].isEmpty() && lineSplit.length > 1)
          newPosition++;
      }
    }
    return newPosition;
  }

  /**
   * Remove an Opus and any associated index terms with it from the inverted index.
   */
  public void remove()
  {

  }

  /**
   * Gets the current Opus.
   * 
   * @return opus the Opus
   */
  public Opus getOpus()
  {
    return opus;
  }

  /**
   * Gets the value of loaded.
   * 
   * @return loaded True if this Opus has been loaded, else false.
   */
  public boolean getLoaded()
  {
    return loaded;
  }

  /**
   * Returns the inverted index.
   * 
   * @return invertedIndex The invertedIndex
   */
  public Map<String, List<Posting>> getInvertedIndex()
  {
    return invertedIndex;
  }

  /**
   * Returns the list of Opuses which have been loaded in.
   * 
   * @return opuses The list of Opuses
   */
  public List<Opus> getOpuses()
  {
    return opuses;
  }

  /**
   * Returns the number of new postings after loading in this Opus.
   * 
   * @return numNewPostings The number of new postings
   */
  public int getNewPostings()
  {
    return numNewPostings;
  }

  /**
   * Returns the number of new index terms.
   * 
   * @return newTerms the number of new index terms
   */
  public int getNewTerms()
  {
    return newTerms;
  }
}
