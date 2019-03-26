import st.Parser;

public class RunTest {
	
	private Parser parser = new Parser();
	
	public static void main(String[] args) {
		
		RunTest rt = new RunTest();
		rt.parser.add("output", "o", Parser.STRING);
		rt.parser.parse("-o 2.txt");
		System.out.println(rt.parser.toString());
	}
	
}