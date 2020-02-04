package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nongui.Posting;

public class PostingTest {

	@Test
	public void testPosting() {
		
		List<Integer> l = new ArrayList<Integer>();
		l.add(10);
		l.add(20);
		
		Posting p = new Posting(5, l);
		
		assertEquals("Doc Identifier", p.getDocIdentifier(), 5);
		assertEquals("Locations", p.getLocations(), l);
		
		List<Integer> x = new ArrayList<Integer>();
		x.add(15);
		x.add(11);
		
		p.setLocations(x);
		
		assertEquals("Locations", p.getLocations(), x);
	}

}
