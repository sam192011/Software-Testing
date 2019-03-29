package st;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parser {
	public static final int INTEGER = 1;
	public static final int BOOLEAN = 2;
	public static final int STRING = 3;
	public static final int CHAR = 4;

	private OptionMap optionMap;

	public Parser() {
		optionMap = new OptionMap();
	}

	public void add(String option_name, String shortcut, int value_type) {
		optionMap.store(option_name, shortcut, value_type);
	}

	public void add(String option_name, int value_type) {
		optionMap.store(option_name, "", value_type);
	}

	public int getInteger(String option) {
		String value = getString(option);
		int type = getType(option);
		int result;
		switch (type) {
		case INTEGER:
			try {
				result = Integer.parseInt(value);
			} catch (Exception e) {
		        try {
		            new BigInteger(value);
		        } catch (Exception e1) {
		            result = 0;
		        }
		        result = 0;
		    }
			break;
		case BOOLEAN:
			if (getBoolean(option) == false) {
				result = 0;
			} else {
				result = 1;
			}
			break;
		case STRING:
		    int power = 1;
		    result = 0;
		    char c;
		    for (int i = value.length() - 1; i >= 0; --i){
		        c = value.charAt(i);
		        if (!Character.isDigit(c)) return 0;
		        result = result + power * (c - '0');
		        power *= 10;
		    }
		    break;
		case CHAR:
			result = (int)getChar(option);
			break;
		default:
			result = 0;
		}
		return result;
	}

	public boolean getBoolean(String option) {
		String value = getString(option);
		boolean result;
		if (value.toLowerCase().equals("false") || value.equals("0") || value.equals("")) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	public String getString(String option) {
		String result = optionMap.getValue(option);
		return result;
	}

	public char getChar(String option) {
		String value = getString(option);
		char result;
		if (value.equals("")) {
			result = '\0';
		} else {
			result = value.charAt(0);
		}
		return result;
	}

	public int parse(String command_line_options) {
		if (command_line_options == null) {
			return -1;
		}
		int length = command_line_options.length();
		if (length == 0) {
			return -2;
		}

		int char_index = 0;
		while (char_index < length) {
			char current_char = command_line_options.charAt(char_index);

			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (current_char != ' ') {
					break;
				}
				char_index++;
			}

			boolean isShortcut = true;
			String option_name = "";
			String option_value = "";
			if (current_char == '-') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
				if (current_char == '-') {
					isShortcut = false;
					char_index++;
				}
			} else {
				return -3;
			}
			current_char = command_line_options.charAt(char_index);

			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (Character.isLetterOrDigit(current_char) || current_char == '_') {
					option_name += current_char;
					char_index++;
				} else {
					break;
				}
			}

			boolean hasSpace = false;
			if (current_char == ' ') {
				hasSpace = true;
				while (char_index < length) {
					current_char = command_line_options.charAt(char_index);
					if (current_char != ' ') {
						break;
					}
					char_index++;
				}
			}

			if (current_char == '=') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
			}
			if ((current_char == '-'  && hasSpace==true ) || char_index == length) {
				if (getType(option_name) == BOOLEAN) {
					option_value = "true";
					if (isShortcut) {
						optionMap.setValueWithOptioShortcut(option_name, option_value);
					} else {
						optionMap.setValueWithOptionName(option_name, option_value);
					}
				} else {
					return -3;
				}
				continue;
			} else {
				char end_symbol = ' ';
				current_char = command_line_options.charAt(char_index);
				if (current_char == '\'' || current_char == '\"') {
					end_symbol = current_char;
					char_index++;
				}
				while (char_index < length) {
					current_char = command_line_options.charAt(char_index);
					if (current_char != end_symbol) {
						option_value = option_value + current_char;
						char_index++;
					} else {
						break;
					}
				}
			}

			if (isShortcut) {
				optionMap.setValueWithOptioShortcut(option_name, option_value);
			} else {
				optionMap.setValueWithOptionName(option_name, option_value);
			}
			char_index++;
		}
		return 0;
	}

	public List<Integer> getIntegerList(String command_line_options){
		List<Integer> Integer_List = new ArrayList<Integer> ();
		String new_string = "";
		/*Checking if the string is null*/
		String parsed_string = getString(command_line_options);
		if (command_line_options == null) {
			return Collections.emptyList();
		}
		/*Checking if the length of the string is 0*/
		int parsed_string_length = parsed_string.length();
		if(parsed_string_length == 0) {
			return Collections.emptyList();
		}
		int i = 0;
		/*Minimising the noise in parsed_string by processing the String to just include numbers and hyphens*/
		while(i<parsed_string_length) {
			if(parsed_string.charAt(i) == '-' || (int) parsed_string.charAt(i) >= 48 || (int) parsed_string.charAt(i) <=57){
				new_string+= parsed_string.charAt(i);
				i++;
			}
			else
				i++;
		}
		
		/*------------Routine to implement all the conditions for which the function returns an empty list-----------*/
		int length = new_string.length();
		String temp_string = "";
		String temp_string1 = "";
		i=0;
		while(i<length) {
			if(new_string.charAt(i) == '-' && i == length-1) {
				return Collections.emptyList();
			}
			else if((i-1)>=0 && (i+1)<length && new_string.charAt(i) == '-' && (int) new_string.charAt(i-1) >=48 && (int) new_string.charAt(i-1) <=57 &&
					(new_string.charAt(i+1) != '-' && (int) new_string.charAt(i+1) <48 || (int) new_string.charAt(i+1) >57)) {
				return Collections.emptyList();
			}
			else if(new_string.charAt(i) == '-' && i==0 && i+1 < length && new_string.charAt(i+1) == '-') {
				return Collections.emptyList();
			}
			else if((i-1)>=0 && (i+2)<length && new_string.charAt(i) == '-' && new_string.charAt(i+1) == '-'
					&& ((int) new_string.charAt(i-1) <48 || (int) new_string.charAt(i-1) >57) &&
					((int) new_string.charAt(i+2) >=48 && (int) new_string.charAt(i+2) <=57)) {
				return Collections.emptyList();
			}
			else
				i++;
		}
		/*-----------Routine to implement all the conditions for which the function returns an empty list--------------*/
		
		i=0;
		int temp_counter = 0;
		int temp = 0;
		/*------------Routine for processing Strings that contains valid ranges------------------*/
		while(i<length) {
			
			/*--------------Routing for processing double hyphen between numbers, i.e second part of the range being negative------------*/
			while(i<length && ((int) new_string.charAt(i) >= 48 && (int) new_string.charAt(i) <=57 || new_string.charAt(i) =='-')) {
				if(i+1 <length && new_string.charAt(i) =='-' && new_string.charAt(i+1) =='-') {
					Integer_List.add(Integer.parseInt(temp_string));
					temp_counter = i+2;
					temp_string1+=new_string.charAt(i+1);
					while(temp_counter <length && (int) new_string.charAt(temp_counter) >= 48 && (int) new_string.charAt(temp_counter) <=57) {
						temp_string1+= new_string.charAt(temp_counter);
						temp_counter++;
					}
					temp = 1;
					if(Integer.parseInt(temp_string) < Integer.parseInt(temp_string1)) {
					while(Integer.parseInt(temp_string)+temp < Integer.parseInt(temp_string1)) {
						Integer_List.add(Integer.parseInt(temp_string)+temp);
						temp++;
					}
					}
					if(Integer.parseInt(temp_string) == Integer.parseInt(temp_string1)) {
						i=i+temp_counter;
						temp_string = "";
						temp_string1 = "";
						break;
					}
					else {
						while(Integer.parseInt(temp_string)-temp > Integer.parseInt(temp_string1)) {
							Integer_List.add(Integer.parseInt(temp_string)-temp);
							temp++;
						}
					}
					temp_string = "";
					temp_string1 = "";
					break;
				}
				/*--------------Routing for processing double hyphen between numbers, i.e second part of the range being negative------------*/
				
				/*------------Routing for processing single hyphen between numbers, i.e second part of the range being positive----------------*/
				else if(i+1 <length &&i-1 >=0&& new_string.charAt(i) =='-' && (int) new_string.charAt(i+1) >= 48 && (int) new_string.charAt(i+1) <=57 &&
						(int) new_string.charAt(i-1) >= 48 && (int) new_string.charAt(i-1) <=57) {
					Integer_List.add(Integer.parseInt(temp_string));

					temp_counter =i+1;
					while(temp_counter <length && (int) new_string.charAt(temp_counter) >= 48 && (int) new_string.charAt(temp_counter) <=57) {
						temp_string1+= new_string.charAt(temp_counter);
						temp_counter++;
					}
					temp = 1;
					if(Integer.parseInt(temp_string) < Integer.parseInt(temp_string1)) {
					while(Integer.parseInt(temp_string)+temp < Integer.parseInt(temp_string1)) {
						Integer_List.add(Integer.parseInt(temp_string)+temp);
						temp++;
					}
					}
					else  if(Integer.parseInt(temp_string) > Integer.parseInt(temp_string1)){
						while(Integer.parseInt(temp_string)-temp > Integer.parseInt(temp_string1)) {
							Integer_List.add(Integer.parseInt(temp_string)-temp);
							temp++;
						}
					}
					else {
						i=i+temp_counter;
						temp_string = "";
						temp_string1 = "";
						break;
					}
					temp_string = "";
					temp_string1 = "";
					break;

				}
				/*------------Routing for processing single hyphen between numbers, i.e second part of the range being positive----------------*/
				
				//Stores numbers without range in a temporary string
				else{
					temp_string+=new_string.charAt(i);
					i++;
				}
			}
			//Passing the integer to the list for numbers without range
			if(temp_string != "") {
				Integer_List.add(Integer.parseInt(temp_string));
			}
			temp_string = "";
			i++;
		}
		return Integer_List;
	}
	private int getType(String option) {
		int type = optionMap.getType(option);
		return type;
	}

	@Override
	public String toString() {
		return optionMap.toString();
	}

}
