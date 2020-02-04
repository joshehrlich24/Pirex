package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import nongui.Load;
import nongui.Opus;
import nongui.Search;

public class SearchTest
{
  private Load load;
  private Opus opus;
  @Before
  public void setUp() throws IOException
  {
    File file = new File("a christmas carol.txt");
    load = new Load();
    load.parseFile(file);
    opus = load.getOpus();
    Load.getStaticOpuses().add(opus);
  }

  @Test
  public void testSearchTerm() throws IOException
  {
	  File file = new File("testdoc.txt");
	  Load load = new Load();
	
		load.parseFile(file);
		load.createSummary();

    Search search = new Search();
    search.searchTerms("test^this^project");
    search.searchTerms("sample^document");
  }

  @Test
  public void testStopWords() throws IOException
  {
	  File file = new File("testdoc.txt");
	  Load load = new Load();
	  load.parseFile(file);
	  load.createSummary();
	  Search search = new Search();
	  assertEquals("search ", search.searchStopWords("this a search"));
  }
  
  @Test
  public void testSearchNoAdjacency() throws IOException
  {
	  File file = new File("testdoc.txt");
	  Load load = new Load();
	  load.parseFile(file);
	  load.createSummary();
	  Search search = new Search();
	  Set<String> set = new HashSet<>();
	  set.add("Mark Sizemore Testing this Project part I 1 Here, \"my friends I\" have gone ahead and created");
	  assertEquals(set, search.searchTerms("gone ahead"));
  }
}
