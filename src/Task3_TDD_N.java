import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.util.*;

public class Task3_TDD_N {
	
	private Parser parser;

	@Before
	public void set_up() {
		parser = new Parser();
	}

	@Test
	public void SearchOrderTest() {
		parser.add("o", Parser.STRING);
		parser.add("option", "o", Parser.STRING);
		parser.parse("--o 1,2,3,4,5");
		parser.parse("-o 6,7,8,9,10");
		List l = parser.getIntegerList("o");
		List l2 = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		assertEquals(l,l2);
	}
	
	@Test
	public void NoValueTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list");
		List l = parser.getIntegerList("o");
		List l2 = new ArrayList<Integer>();
		assertEquals(l, l2);
	}
	
	@Test
	public void NonHyphenSeparatorTest() {
		parser.add("list1", Parser.STRING);
		parser.parse("--list1=\"1,2 4\"");
		List l1 = parser.getIntegerList("list1");
		parser.parse("--list1=\"1,2.4\"");
		List l2 = parser.getIntegerList("list1");
		parser.parse("--list1=\"{}1<2>4({)\"");
		List l3 = parser.getIntegerList("list1");
		assertTrue((l1 == l2) && (l2 == l3));
	}
	
	@Test
	public void RangeTestAscendingOrder() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1,2,4-7,10");
		List l = parser.getIntegerList("list");
		List l2 = new ArrayList<Integer>(Arrays.asList(1,2,4,5,6,7,10));
		assertEquals(l, l2);
	}

	@Test
	public void RangeTestDescendingOrder() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=10,7-4,2,1");
		List l = parser.getIntegerList("list");
		List l2 = new ArrayList<Integer>(Arrays.asList(10,7,6,5,4,2,1));
		assertEquals(l, l2);
	}
	
	@Test
	public void NegativeValueTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=-1,-2,-3");
		List l = parser.getIntegerList("list");
		List l2 = new ArrayList<Integer>(Arrays.asList(-1,-2,-3));
		assertEquals(l, l2);
	}
	
	@Test
	public void NegativeValueRangeTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=-7--5");
		List l = parser.getIntegerList("list");
		List l2 = new ArrayList<Integer>(Arrays.asList(-7,-6,-5));
		assertEquals(l, l2);
	}
	
	@Test
	public void NegativePositiveRangeTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=-2-1");
		List l = parser.getIntegerList("list");
		List l2 = new ArrayList<Integer>(Arrays.asList(-2,-1,0,1));
		assertEquals(l, l2);
	}
	
	@Test
	public void EndingWithHyphenSuffixTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=3-");
		List l = parser.getIntegerList("list");
		List l2 = new ArrayList<Integer>();
		assertEquals(l, l2);
	}
	
	@Test
	public void MultipleHyphenAsSuffixTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=3-,5-,7-,8-");
		List l = parser.getIntegerList("list");
		List l2 = new ArrayList<Integer>();
		assertEquals(l, l2);
	}

	
}
