package operations;

import java.awt.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;


public class DivideLines {
	
	/**
	 * Returns a String divided into lines.
	 * 
	 * The string is formated in HTML, usable in JButton, JLabel and JPanel. If a word is longer than the
	 * remaining digits in a line, it will be divided with a <code>"-"</code> on the end of the line and the
	 * beggining of the next line. 
	 * 
	 * @param s	- String to be formated
	 * @param digits - Maximum number of digits in each line
	 * @return HTML formated string divided into lines
	 * 
	 * 
	 */
	
	public static String divideLines(String s, int digits){
		
		StringBuilder format = new StringBuilder();
		
		format.append("<html>");
		
		if (s.length() < digits) digits = s.length();
		
		int start = 0;
		int end = digits;
		
		double size = Math.ceil(s.length()/digits);
		
		if (size == Math.ceil(size) ){
			size++;
			size++;
		} else size++;
		
		System.out.println(size);
		
		for (int i = 0; i < size ;i++){
			
			if (s.charAt(start) == ' ') {
				start++;
				end++;
			}
			
			try {
				
				if (s.charAt(end-1) != ' '  && s.charAt(end) != ' ' && s.charAt(end-2) == ' '){
					
					format.append(s.substring(start,end-2));
					format.append("<br>");
					
					start += digits-2;
					end += digits-2;
					
				} else
				
				if (s.charAt(end-1) != ' '  && s.charAt(end) != ' '){
					format.append(s.substring(start,end-1));
					format.append("-<br>-");
					start += digits-1;
					end += digits-1;
				} else {
					format.append(s.substring(start,end));
					start += digits;
					end += digits;
					format.append("<br>");
				}
				
			} catch (Exception e){
				format.append(s.substring(start,end));
				start += digits;
				end += digits;
				format.append("<br>");
			}
			
			if (end > s.length()) end = s.length()-1;
			if (start >= s.length()) break;
			
		}
		
		format.append("</html>");
		
		String finale = format.toString();
		
		return finale;
	}
	
	
	/**
	 * Returns a String divided into lines.
	 * 
	 * The string is formated in HTML, usable in JButton, JLabel and JPanel.
	 * 
	 * 
	 * @param s	- The String to be formated
	 * @param digits - Maximum number of digits in each line
	 * @param separateWords - If <code>true</code>, has same behavior as {@link  #divideLines(String s, int digits)}.
	 * If <code>false</code>, the largest word, if larger than <code>digits</code>, will be the maximum number 
	 * of digits in each line.
	 * @return HTML formated string divided into lines
	 * 
	 * 
	 */
	
	public static String divideLines(String s, int digits, boolean separateWords){
		
		if (separateWords) return divideLines(s, digits);
		
		else {
			
			ArrayList<String> words = new ArrayList<String>();
			Scanner scanner = new Scanner(s);
			
			int maxDigits = digits;
			
			while (scanner.hasNext()){
				words.add(scanner.next());
				if (words.get(words.size()-1).length() > maxDigits) maxDigits = words.get(words.size()-1).length();
			}
			
			StringBuilder format = new StringBuilder();
			format.append("<html>");
			
			int digitsInLine = 0;
			
			for (String word : words){
				if (digitsInLine+word.length() <= maxDigits){
				
					if (digitsInLine != 0) format.append(" ");
				
					format.append(word);
					
					digitsInLine+=word.length();
				
				} else {
					format.append("<br>");
					format.append(word);
					digitsInLine = word.length();
				}
			}
			
			format.append("</html>");
			
			return format.toString();
			
		}
	}

}

