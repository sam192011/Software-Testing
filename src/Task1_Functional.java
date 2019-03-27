import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

//import org.junit.Assert;

public class Task1_Functional {
	private Parser parser;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	//-----------------Test Cases for adding options with shortcut-----//
	@Test
	public void StartWithNumbersTest () {
		int i = 0;
		while(i<=9) {
		try {
			parser.add(i+"utput", "o", Parser.STRING);
			i++;
		}catch(Exception e) {
			e.printStackTrace();
			i++;
			}
		
		}
	}
	@Test
	public void NoSpecialCharactersTest () {
		int i = 32;
		while(i<=126) {
			if((i>=32 && i<=47) || (i>=58 && i<=64) || (i>=91 && i<=94) || i==96 || (i>=123 && i<=126)) {
				try {
					parser.add("out"+(char)i+"put", "ab"+(char)i+"123", Parser.STRING);
				}catch(Exception e) {
					e.printStackTrace();
					}
			}
			i++;
		}
	}
	
	@Test
	public void CaseSensitiveShortCutTest () {
		parser.add("output", "o", Parser.STRING);
		assertNotEquals(parser.getString("O"), "2.txt");
	}
	
	@Test
	public void CaseSensitiveNameTest () {
		parser.add("output", "o", Parser.STRING);
		assertNotEquals(parser.getString("OUTPUT"), "2.txt");
	}
	
	@Test
	public void NameOverrideTest () {
		parser.add("output", "o", Parser.STRING);
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output 2.txt");
		assertEquals(parser.getBoolean("o"), true);
	}
	
	@Test
	public void SameNameAsShortCutTest () {
		parser.add("output", "o", Parser.STRING);
		parser.add("o", "x", Parser.INTEGER);
		parser.parse("--o 123.txt");
		assertEquals(parser.getString("o"), "123.txt");
	}
	@Test
	public void BooleanValueTest_1 () {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("-o 0");
		assertNotEquals(parser.getBoolean("o"), true);
	}
	
	@Test
	public void BooleanValueTest_2 () {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("-o FALSE");
		assertNotEquals(parser.getBoolean("o"), true);
	}
	
	@Test
	public void BooleanValueTest_3 () {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("-o FalSe");
		assertNotEquals(parser.getBoolean("o"), true);
	}
	
	@Test
	public void BooleanValueTest_4 () {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("-o false");
		assertNotEquals(parser.getBoolean("o"), true);
	}
	
//-----------Test cases for adding options without shortcut-------------//
	@Test
	public void StartWithNumbersTest_2 () {
		int i = 0;
		while(i<=9) {
		try {
			parser.add(i+"utput", Parser.STRING);
			i++;
		}catch(Exception e) {
			e.printStackTrace();
			i++;
			}
		
		}
	}
	@Test
	public void NoSpecialCharactersTest_2 () {
		int i = 32;
		while(i<=126) {
			if((i>=32 && i<=47) || (i>=58 && i<=64) || (i>=91 && i<=94) || i==96 || (i>=123 && i<=126)) {
				try {
					parser.add("out"+(char)i+"put", Parser.STRING);
				}catch(Exception e) {
					e.printStackTrace();
					}
			}
			i++;
		}
	}
	
	@Test
	public void CaseSensitiveShortCutTest_2 () {
		parser.add("output",Parser.STRING);
		assertNotEquals(parser.getString("O"), "2.txt");
	}
	
	@Test
	public void CaseSensitiveNameTest_2 () {
		parser.add("output",Parser.STRING);
		parser.parse("--output 2.txt");
		assertNotEquals(parser.getString("OUTPUT"), "2.txt");
	}
	
	@Test
	public void NameOverrideTest_2 () {
		parser.add("output",Parser.STRING);
		parser.add("output",Parser.INTEGER);
		parser.parse("--output 2.txt");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void SameNameAsShortCutTest_2 () {
		parser.add("output","o",Parser.STRING);
		parser.add("o",Parser.INTEGER);
		parser.parse("--o 123.txt");
		assertEquals(parser.getString("o"), "123.txt");
	}
	@Test
	public void BooleanValueTest_11 () {
		parser.add("output", Parser.BOOLEAN);
		parser.parse("--output 0");
		assertNotEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void BooleanValueTest_22 () {
		parser.add("output",Parser.BOOLEAN);
		parser.parse("--output FALSE");
		assertNotEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void BooleanValueTest_33 () {
		parser.add("output",Parser.BOOLEAN);
		parser.parse("--output FalSe");
		assertNotEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void BooleanValueTest_44 () {
		parser.add("output",Parser.BOOLEAN);
		parser.parse("--output false");
		assertNotEquals(parser.getBoolean("output"), true);
	}
	
	//-----------Test cases for parse function-------------//
	@Test
	public void NamePrefixValueTest () {
		parser.add("output","o",Parser.BOOLEAN);
		parser.parse("-output=2.txt");
		assertNotEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void ShortCutPrefixValueTest () {
		parser.add("output","o",Parser.BOOLEAN);
		parser.parse("--o=2.txt");
		assertNotEquals(parser.getBoolean("o"), true);
	}
	
	@Test
	public void ValueAssingmetTest () {
		parser.add("o",Parser.STRING);
		parser.parse("--o=2.txt");
		assertEquals(parser.getString("o"), "2.txt");
	}
	
	@Test
	public void ValueAssingmetTest_2 () {
		parser.add("o",Parser.STRING);
		parser.parse("--o 2.txt");
		assertEquals(parser.getString("o"), "2.txt");
	}
	@Test
	public void ValueAssingmetTest_3 () {
		parser.add("o",Parser.STRING);
		parser.parse("--o='2.txt'");
		assertEquals(parser.getString("o"), "2.txt");
	}
	
	@Test
	public void ValueAssingmetTest_4 () {
		parser.add("o",Parser.STRING);
		parser.parse("--o=\"2.txt\"");
		assertEquals(parser.getString("o"), "2.txt");
	}
	
	@Test
	public void ValueAssingmetTest_5 () {
		parser.add("o",Parser.STRING);
		parser.parse("--o='value=\"2.txt\"'");
		assertEquals(parser.getString("o"), "value=\"2.txt\"");
	}
	
	@Test
	public void ValueAssingmetTest_6 () {
		parser.add("o",Parser.STRING);
		parser.parse("--o='value=\"2.txt\"'");
		parser.parse("--o='value=\"3.txt\"'");
		parser.parse("--o='value=\"Jay&SamSTHustle\"'");
		assertEquals(parser.getString("o"), "value=\"Jay&SamSTHustle\"");
	}
	
	@Test
	public void DefaultAsingmentTest () {
		parser.parse("--o");
		assertEquals(parser.getInteger("o"), 0);
	}
	
	@Test
	public void DefaultAsingmentTest_2 () {
		parser.parse("--o");
		assertEquals(parser.getBoolean("o"), false);
	}
	
	@Test
	public void DefaultAsingmentTest_3 () {
		parser.parse("--o");
		assertEquals(parser.getString("o"), "");
	}
	@Test
	public void DefaultAsingmentTest_4 () {
		parser.parse("--o --a");
		assertEquals(parser.getChar("o"), '\0');
	}
//--------------Test cases for Retrieval functions-------------------//
	@Test
	public void RetrievalFunctionTest () {
		parser.add("output","o",Parser.STRING);
		parser.add("out","output",Parser.STRING);
		parser.parse("--output=random");
		parser.parse("-output=veryrandom");
		assertEquals(parser.getString("o"), "random");
	}
	
	@Test
	public void DefaultRetrievalAsingmentTest () {
		assertEquals(parser.getInteger("o"), 0);
	}
	
	@Test
	public void DefaultRetrievalAsingmentTest_2 () {
		assertEquals(parser.getBoolean("o"), false);
	}
	
	@Test
	public void DefaultRetrievalAsingmentTest_3 () {
		assertEquals(parser.getString("o"), "");
	}
	@Test
	public void DefaultRetrievalAsingmentTest_4 () {
		assertEquals(parser.getChar("o"), '\0');
	}
}
