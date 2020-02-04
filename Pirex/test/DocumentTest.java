package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nongui.Document;

public class DocumentTest
{
  private Document document;
  @Before
  public void setUp()
  {
    document = new Document();
  }

  @Test
  public void testAdd()
  {
    document.add("Hello");
    document.add("Test");
    assertTrue(document.length() == 2);
  }

  @Test
  public void testFirstLine()
  {
    document.add("Hello");
    document.add("Test");
    String test = document.getFirstLine();
    assertTrue(test.equals("Hello"));
  }
  
  @Test
  public void testGetDocument()
  {
    document.add("Hello");
    document.add("World");
    String expected = "\nHello\nWorld";
    assertEquals(expected, document.getDocument());
  }
  
  @Test
  public void testSize()
  {
    document.add("Hello");
    document.add("World");
    assertTrue(document.size() == 2);
  }
  
  @Test
  public void testClear()
  {
    document.add("Hello");
    document.add("World");
    document.clear();
    assertTrue(document.length() == 0);
  }
  
  @Test
  public void testGetDocumentAsOneLine()
  {
	  document.add("Hello");
	  document.add("World");
	  String expected = "Hello World";
	  assertEquals(expected, document.getDocumentAsOneLine());
  }
  
  @Test
  public void testDocumentNumber()
  {
	  document.setDocumentNumber(1);
	  assertEquals(1, document.getDocumentNumber());
  }
}
