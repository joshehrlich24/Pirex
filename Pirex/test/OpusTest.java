package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import nongui.Load;
import nongui.Opus;

public class OpusTest {

	@Test
	public void testOpus() {
		
		Opus op = new Opus();
		op.setAuthor("Tom");
		op.setTitle("Moby Dick");
		op.setFilename("path");
		op.setDocumentCount(15);
		op.setOpusNumber(2);
		
		
		assertEquals("Author", op.getAuthor(), "Tom");
		assertEquals("Title", op.getTitle(), "Moby Dick");
		assertEquals("Filename", op.getFilename(), "path");
		assertEquals("Document Count", op.getDocumentCount(), 15);
		assertEquals("Opus Number", op.getOpusNumber(), 2);
		
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(5);
		
		map.put("List", l);
		
		op.setIndexTerms(map);
		
		assertEquals("Index Terms", op.getIndexTerms(), map);
		
	}
	
	@Test
	public void testToShortForm() throws IOException
	{
		File file = new File("testdoc.txt");
		Load load = new Load();
		load.parseFile(file);
		assertEquals("Mark Sizemore Testing this Project part I 0 Test", load.getOpus().toShortForm(0));
	}

}
