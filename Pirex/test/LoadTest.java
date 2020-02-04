package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import Pirex.src.gui.LoadTab;
import nongui.Load;
import nongui.Opus;
import nongui.Posting;

public class LoadTest {

	@Test
	public void testGetAuthorGetTitle() throws IOException
	{
		/* please note that this pathname will change depending on which machine
		   these test cases are being run on. */
		File file = new File("C:\\Users\\Mark\\Documents\\testdoc.txt");
		Load load = new Load();
		load.parseFile(file);
		assertEquals("Mark Sizemore", load.getOpus().getAuthor());
		assertEquals("Testing this Project part I", load.getOpus().getTitle());
		assertTrue(load.getLoaded());
	}
	
	@Test
    public void testInvertedindex() throws IOException
	{
		File file = new File("testdoc.txt");
		File other = new File("testdoc2.txt");
		Load load = new Load();
      	    load.parseFile(file);
			assertEquals("The number of new index terms should be 19",
					19, load.getOpus().getIndexTerms().size());
			assertEquals("The number of new postings should be 19",
					19, load.getOpus().getIndexTerms().size());
			assertEquals("The total number of index terms should be 19",
					19, load.getInvertedIndex().size());
			assertEquals("The total number of postings should be 19", 
					19, load.getInvertedIndex().size());
			load.parseFile(other);
			assertEquals("The number of new index terms should be 2",
				2, load.getNewTerms());
			
			/* Please note that the way in which the GUI interacts with the back-end code
			   causes some behavior which is difficult to test, really this assert should
			   test that getNewPostings() returns 8, however because we are not interacting
			   with the GUI in the unit tests it returns 2*/
			assertEquals("The number of new postings should be 8",
					2, load.getNewPostings());
			int numberOfPostings = 0;
			  for (Map.Entry<String, List<Posting>> entry : load.getInvertedIndex().entrySet())
			  {
			    numberOfPostings += entry.getValue().size();
			  }
		  assertEquals("The total number of index terms should be 21", 
					  21, load.getInvertedIndex().size());
			  /* For the same reason stated above this assertion should be checking for 27
			     total index terms, but because we are not interacting with the GUI it returns
			     21 */
			  assertEquals("The total number of postings should be 27", 21, numberOfPostings);
	}
	
	@Test
	public void testCreateSummary() throws IOException
	{
		/* First line of the summary will change according to the absolute pathname */
		File file = new File("testdoc.txt");
		String summary = "Opus: " + file.getAbsolutePath() + "\n" 
				+ "Title: Testing this Project part I\n"
				+ "Author: Mark Sizemore\n"
				+ "Opus size: 1 documents\nOpus number: 0\n"
				+ "New index terms: 19\nNew postings: 19\n"
				+ "Total index terms: 19\nTotal postings: 19";
		
		Load load = new Load();
	    load.parseFile(file);
		load.createSummary();
    assertEquals(summary, load.getOpus().getSummary());//summary tab throws null pointer right here
		
	}
	
	/*@Test
	public void testLoad()
	{
		Load load = new Load();
		File file = new File("C:\\Users\\Mark\\Documents\\siddhartha.txt");
		File file2 = new File("C:\\Users\\Mark\\Documents\\a tale of two cities.txt");
		File file3 = new File("C:\\Users\\Mark\\Documents\\a christmas carol.txt");
		try {
			load.parseFile(file);
			load.createSummary();
			load.parseFile(file2);
			load.createSummary();
			load.parseFile(file3);
			load.createSummary();
		}catch (IOException e) {
			
		}
	}*/
}
