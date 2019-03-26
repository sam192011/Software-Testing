import static org.junit.Assert.*;

import org.junit.Test;

import st.OptionMap;
import st.Parser;

import org.junit.Before;

public class Task2_Coverage {
	private Parser parser;
	private OptionMap optionMap;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	// EXISITING TESTS COPIED FROM TASK 1//
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
		parser.parse("-o 2.txt");
		assertNotEquals(parser.getString("O"), "2.txt");
	}
	
	@Test
	public void CaseSensitiveNameTest () {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output 2.txt");
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
		parser.parse("-o 2.txt");
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
	
	// ----------NEW TESTS FOR TASK 2------------//
	
	@Test
	public void ParseNullTest() {
		assertNotEquals(parser.parse(null), 0);
	}
	
	@Test
	public void ParseLengthZeroTest() {
		assertNotEquals(parser.parse(""), 0);
	}
	
	@Test
	public void StoreNullNameTest() {
		try {
			parser.add(null, Parser.STRING);
			fail("Expected add to fail due to null being invalid option name");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Testing the OptionMap.isOptionValid() function using Parser.add() which uses OptionMap.store() 
	
	@Test
	public void StoreEmptyNameTest() {
		try {
			parser.add("", Parser.STRING);
			fail("Expected add to fail due to empty being invalid option name");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void StoreNullShortcutTest() {
		try {
			parser.add("filename", null, Parser.STRING);
			fail("Expected add to fail due to empty being invalid shortcut");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void StoreNonAlnumShortcutTest() {
		try {
			parser.add("filename", "!", Parser.STRING);
			fail("Expected add to fail due to non-alnum shortcut");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Testing OptionMap.getType() function
	
	@Test
	public void GetTypeOfExistingNameTest() {
		optionMap = new OptionMap();
		optionMap.store("filename", "", OptionMap.STRING);
		assertEquals(optionMap.getType("filename"), OptionMap.STRING);
	}
	
	@Test
	public void GetTypeOfNonexistingNameTest() {
		optionMap = new OptionMap();
		optionMap.store("optimize", "", OptionMap.BOOLEAN);
		assertEquals(optionMap.getType("filename"), 0);
	}
	
	@Test
	public void GetTypeOfExistingShortcutTest() {
		optionMap = new OptionMap();
		optionMap.store("filename", "f", OptionMap.STRING);
		assertEquals(optionMap.getType("f"), OptionMap.STRING);
	}
	
	// Testing OptionMap.isOptionValid() function
	
	@Test
	public void InvalidOptionSmallerThan1Test() {
		try {
			parser.add("invalid", 0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void InvalidOptionBiggerThan4Test() {
		try {
			parser.add("invalid", 9);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Testing OptionMap.toString()
	
	@Test
	public void OptionMapToStringTest() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o 2.txt");
		assertEquals(parser.toString(), "OptionMap [options=\n\t{name=output, shortcut=o, type=3, value=2.txt}\n]");
	}
	
}
