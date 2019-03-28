import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class Task3_TDD_N {
	
	private Parser parser;

	@Before
	public void set_up() {
		parser = new Parser();
	}

	@Test
	public void BasicTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1,2,4-7,10");
		List 1 = parser.getIntegerList("list");
		assertEquals(l, {1,2,4,5,6,7,10});
	}

	@Test
	public void SearchOrderTest() {
		parser.add("o", Parser.STRING);
		parser.add("option", "o", Parser.STRING);
		parser.parse("--o 1,2,3,4,5");
		parser.parse("-o 6,7,8,9,10");
		List 1 = parser.getIntegerList("o");
		assertEquals(l, {1,2,3,4,5});
	}
	
	@Test
	public void NoValueTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list");
		List 1 = parser.getIntegerList("o");
		assertEquals(l, []);
	}

}
