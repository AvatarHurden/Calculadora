package calculadoras.engine;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import operations.BigOperation;
import calculadoras.gui.PadrãoUI;

public class PadrãoEngine {
	
	Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	// Numbers and basic types
	
	int digitsDisplayed = 18; // Number of digits to be displayed and addable by user
	int digitsCalculated = 30; // Number of decimal places calculated on division and sqrt
									 // Preferably 5 more than digitsDisplayed for more precision
	
	private BigDecimal memory = BigDecimal.ZERO;
	
	private final BigDecimal HUNDRED = new BigDecimal(100);
	
	PadrãoUI gui;
	
	Expression ex = new Expression("0");
	
	List<Function> functions = new ArrayList<Function>();
	
	boolean definingNumber, equalUsed, operatorUsed;
	
	String lastNumber = "0";
	char lastOperator = ' ';
	
	public PadrãoEngine(PadrãoUI gui, boolean withMemory){
		
		this.gui = gui;
	
		addDefaultFunctions();
		
		addNumpad(gui.getNumpadArray(),gui.getDecimal());
		
		addStandarFunctions(gui.getFunctionsArray(),gui.getReset(),gui.getLeftParentheses(),
				gui.getRightParentheses(),gui.getBackspace());
		
		if (withMemory)	addMemoryFunctions(gui.getMemoryArray());
		
		addKeyListener();
		
		
	}
	
	public void addDefaultFunctions(){
		
		functions.add(new Function("sqrt") {

			public BigDecimal calculate(BigDecimal b) {

				return BigOperation.sqrt(b, 30);
			}
		});

		functions.add(new Function("reciproc") {

			public BigDecimal calculate(BigDecimal b) {

				return BigDecimal.ONE.divide(b, 30, BigDecimal.ROUND_HALF_EVEN);
			}
		});

		functions.add(new Function("negate") {

			public BigDecimal calculate(BigDecimal b) {

				return b.negate();
			}
		});
		
	}
	
	public void resetAll(){
		
		ex = new Expression("0");
		
		memory = null;
		
	}

	// Function to add the KeyListener (turn keyboard useful)
	
 	public void addKeyListener(){
 		
 		gui.addKeyListener(new KeyListener() {
			
 			public void keyPressed(KeyEvent e) {
 				
 				if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
 					clip.setContents(new StringSelection(gui.getScreenLabel().getText()), null);
 					gui.changeColor(gui.getScreenLabel());
 				}
 				
 			}
			public void keyReleased(KeyEvent e) {}
		
			
			public void keyTyped(KeyEvent key) {
				
				if ((int) key.getKeyChar()-48 <= 9 && (int) key.getKeyChar()-48 >= 0){
					
					int i = (int) key.getKeyChar()-48;	
			
					gui.getNumpadArray()[i].doClick();
					
				} else {
					
					if (key.getKeyChar() == '+'){	
		
						gui.getFunctionsArray()[4].doClick();
						
					} else
					
						if (key.getKeyChar() == '-'){
						
						gui.getFunctionsArray()[3].doClick();
						
					} else
					
					if (key.getKeyChar() == '/'){
						
						gui.getFunctionsArray()[1].doClick();
						
					} else
					
					if (key.getKeyChar() == '*'){
						
						gui.getFunctionsArray()[2].doClick();
						
					} else
						
					if (key.getKeyChar() == KeyEvent.VK_ENTER){
						
						gui.getEquationLabel().setText("");
						
						gui.getFunctionsArray()[8].doClick();
						
					} else
					
					if (key.getKeyChar() == KeyEvent.VK_PERIOD || key.getKeyChar() == KeyEvent.VK_COMMA){
						
						gui.getDecimal().doClick();
						
					} else
					
					if (key.getKeyChar() == 'c'){
						
						gui.getReset().doClick();
						
					} else
						
					if (key.getKeyChar() == '('){
						
						gui.getLeftParentheses().doClick();
						
					} else
						
					if (key.getKeyChar() == ')'){
						
						gui.getRightParentheses().doClick();
						
					} else
					
					if (key.getKeyChar() == KeyEvent.VK_BACK_SPACE){
						
						gui.getBackspace().doClick();				
						
					}
				}
			}
		});
 	}

 	
 	public void addNumpad(JButton[] array, JButton decimal){
 		
 		class numpadClicked implements ActionListener{
 			public void actionPerformed(ActionEvent e) {
 				
 				if (gui.getScreenLabel().getText().length() <= digitsDisplayed || !definingNumber) {
 				
	 				ex.addCharacter(e.getActionCommand().toCharArray()[0]);	
	 				
	 				if (definingNumber) {
	 					lastNumber += e.getActionCommand();
	 					gui.addDigitToScreen(e.getActionCommand().toCharArray()[0]);
	 				} else {
	 					lastNumber = e.getActionCommand();
	 					gui.setScreen(e.getActionCommand());
	 				}
	 				
	 				definingNumber = true;
	 				operatorUsed = false;
	 				equalUsed = false;
	 				
	 				gui.getBackspace().setEnabled(true);
	 				
	 				gui.requestFocus();
 				}
 			}
 		}
 		
 		for (JButton i : array) {
 			i.addActionListener(new numpadClicked());
 		}
 		
 		decimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (ex.number.isEmpty())
					ex.addCharacter('0');
				
				if (Double.parseDouble(ex.number) == 0)
					ex.addString("0.");
				
				else if (!ex.number.contains(".")) 
					ex.addCharacter('.');
				
				
				if (definingNumber) {
 					lastNumber += e.getActionCommand();
 					gui.addDigitToScreen(e.getActionCommand().toCharArray()[0]);
 				} else {
 					lastNumber = e.getActionCommand();
 					gui.setScreen("0"+e.getActionCommand());
 				}
 				
 				definingNumber = true;
 				operatorUsed = false;
 				equalUsed = false;
				
			}
		});
 		
 	}
 	
 	public void addStandarFunctions(JButton[] array, JButton reset, 
 			JButton leftP, JButton rightP, JButton backspace){
 	
 		class standardFunctionClicked implements ActionListener{
 			public void actionPerformed(ActionEvent e) {
 				
 				String src = e.getActionCommand();
 				
 				if (src == "=") {
 					
 					if (equalUsed) 
 						ex.addString(lastOperator+lastNumber);
 					else if (operatorUsed) {
 						ex.addString(lastNumber);
 						lastNumber = BigOperation.format(ex.calculateLastValue(ex.priorityOf(lastOperator)),
 								digitsDisplayed).toString();
 					} else if (definingNumber)
 						lastNumber = BigOperation.format(ex.calculateLastValue(ex.priorityOf(lastOperator)),
 								digitsDisplayed).toString();
 					
 					gui.changeDisplay(gui.getScreenLabel(),
 							BigOperation.format(ex.calculateAll(),digitsCalculated),digitsDisplayed);
 					
 					equalUsed = true;
 					operatorUsed = false;
 					
 				} else if (src == "%") {
 					
 					ex.addString(src.toString());
 					
 					lastNumber = BigOperation.format(ex.getLastNumber(), digitsDisplayed).toString();
 			
 					gui.changeDisplay(gui.getScreenLabel(),
 							new BigDecimal(lastNumber), digitsDisplayed);
 					
 				} else if (src == "+" || src == "-"|| src == "*"|| src == "/"){
 					
 					if (lastOperator == ' ')
 						lastOperator = e.getActionCommand().toCharArray()[0];
 					
 					if (!ex.hasGreaterPriority(src.toCharArray()[0])) {
 						
 						lastNumber = BigOperation.format(ex.calculateLastValue(ex.priorityOf(lastOperator)),
 								digitsDisplayed).toString();
 						gui.changeDisplay(gui.getScreenLabel(), 
 								ex.calculate(ex.priorityOf(src.toCharArray()[0])), digitsDisplayed);
 						
 						if (ex.priority == ex.priorityOf(src.toCharArray()[0]))
 	 						lastOperator = e.getActionCommand().toCharArray()[0];
 						
 					} else {
 						lastOperator = e.getActionCommand().toCharArray()[0];
 					}
 					if (src == "+" || src == "-")
 						lastOperator = e.getActionCommand().toCharArray()[0];
 					
 					ex.addString(src.toString());
 					
 					operatorUsed = true;
 					equalUsed = false;

 					ex.setPriority(src.toCharArray()[0]);	
 					
 				} else {
 					
 					ex.addFunction(src.toString());
 	
 				}
 				
 				definingNumber = false;
 				
 				gui.setExpression(ex.expression);
 				gui.requestFocus();
 				
 				if (src == "=")
 					ex.addToExpression(gui.getScreenLabel().getText());
 			}
 		}
 	
 		for (JButton i : array) {
 			i.addActionListener(new standardFunctionClicked());
 		}
 		
 		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				gui.setExpression("");
				
				ex = new Expression("0");
				
				gui.changeDisplay(gui.getScreenLabel(), BigDecimal.ZERO, digitsDisplayed);
				gui.enableButtons(true);
				
				lastOperator = ' ';
				lastNumber = "0";
			}
		});
 	
 		leftP.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			
				ex.addCharacter('(');
				gui.getRightParentheses().setEnabled(true);
				
				gui.setExpression(ex.expression);
			}
		});
 		
		rightP.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {

				ex.addCharacter(')');
				
				gui.setExpression(ex.expression);
			}
		});
 		
		backspace.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				ex.removeCharacter();
				
				gui.removeDigitFromScreen();
			}
		});
		
 	}
		
	public void addMemoryFunctions(JButton[] array){
		
		class memoryFunctionClicked implements ActionListener{
			public void actionPerformed(ActionEvent e) {
			
				Object src = new Object();
				src = e.getActionCommand();
				
				if (src.equals("MS")){
				
					memory = ex.getLastNumber();
					
				} else if (src.equals("MC")){
					
					memory = BigDecimal.ZERO;
					
				} else if (src.equals("MR")){
					
					if (memory != BigDecimal.ZERO){
						
						ex.addString(memory.toString());
						ex.consolidateNumber();
						
					}
					
				} else if (src.equals("M+")){
					
					memory = memory.add(ex.getLastNumber());
					
				} else if(src.equals("M-")){
					
					ex.consolidateNumber();
					memory = memory.subtract(ex.getLastNumber());
					
				}
				
				gui.changeDisplay(gui.getMemoryLabel(), memory, 5);
				
				gui.requestFocus();
				
			}
		}
		
		for (JButton i : array) {
			i.addActionListener(new memoryFunctionClicked());
		}
	}

	
	public class Expression {

		List<BigDecimal> numbers = new ArrayList<BigDecimal>();

		List<Character> operations = new ArrayList<Character>();

		Expression inside;
		Expression parent;

		boolean hasInside;

		boolean mustNegate;

		int priority;

		String number = "0";

		String function = "";
		
		String expression = "";

		//Builders
		
		public Expression() {}
		
		public Expression(String s) {

			addString(s);

		}

		public Expression(String s, Expression parent) {

			addString(s);
			this.parent = parent;

		}

		
		// Adding terms to the string
		
		public void addString(String s) {

			StringBuilder builder = new StringBuilder(s);

			for (int i = 0; i < builder.length(); i++)
				addCharacter(builder.charAt(i));

		}

		public void addCharacter(char c) {

			if (hasInside) {
				
				inside.addCharacter(c);

			} else if (c == '(') {
				
				consolidateNumber();
				inside = new Expression("",this);
				hasInside = true;
				
			} else if (c == ')') {
								
				consolidateNumber();
				
				if (parent.function.equals(""))
					parent.numbers.add(calculate(0));
				else
					parent.numbers.add(doFunction(parent.function, calculate(0)));
				
				parent.inside = null;
				parent.hasInside = false;

			} else if (Character.isDigit(c) || c == '.') {

				if (mustNegate) {
					number = "-0";
					mustNegate = false;
				}
				
				if (Double.parseDouble(number) != 0 || c != '0')
					number += c;

			} else if (Character.isLetter(c)) {

				function += c;

			} else if (c == '%') {
		
				consolidateNumber();

				number = getLastNumber().toString();
				
				BigDecimal temp = findPercentage(numbers.get(numbers.size()-2),
						numbers.get(numbers.size()-1),operations.get(operations.size()-1));
				
				numbers.set(numbers.size()-1,temp);
				
//				addToExpression(BigOperation.format(numbers.get(numbers.size()-1),digitsDisplayed).toString());
				
			} else {

				consolidateNumber();

				if (numbers.size() == operations.size() && c == '-')
					mustNegate = true;

				else if (numbers.size() == operations.size())
					operations.set(operations.size()-1,c);
				else
					operations.add(c);

			}
		
			addToExpression(c);
			
		}
		
		public void removeCharacter() {
			
			if (number.length() > 1) {
			
			StringBuilder string = new StringBuilder(number);
			string.deleteCharAt(string.length()-1);
			number = string.toString();
			
			StringBuilder ex = new StringBuilder(expression);
			ex.deleteCharAt(ex.length()-1);
			expression = ex.toString();
			
			}
		}
		
		public void addFunction(String s) {
		
			// Functional part
			consolidateNumber();
			
			if (hasInside)
				addString(s);
			else 
				numbers.set(numbers.size()-1, doFunction(s, numbers.get(numbers.size()-1)));
			
			
			// Aesthetic part
			if (expression.endsWith(")")) {
				
				StringBuilder builder = new StringBuilder(expression);
				
				int insides = 0;
				
				for (int i = expression.lastIndexOf(")")-1; i >= 0; i--) {
					
					if (builder.charAt(i) == ')')
						insides++;
					
					if (builder.charAt(i) == '(') {
						
						if (insides > 0)
							insides--;
						else {
							
							if (Character.isLetter(builder.charAt(i-1))) {
								i = i-2;
								builder.append(")");
								for (int x = i; x > 0;x--){
									if (Character.isLetter(builder.charAt(x)))
										i--;
								}
								if (i>0) i++;
								builder.insert(i,s+"(");
								
							}
							
							else builder.insert(i,s);}
						
					}
					
				}
				
				
				expression = builder.toString();
				
			} else {
				
				StringBuilder builder = new StringBuilder(expression);
				
				builder.append(')');
				
				int i;
				for (i = expression.length()-1; i > 0; i--) {
				
					if (builder.charAt(i) == ' ') {
						i++;
						break;
					}
				
				}
				
				builder.insert(i,s+'(');
				expression = builder.toString();
			}
				
		}
		
		public void addToExpression(char c) {
			
			if (c == '.' && Double.parseDouble(number) == 0)
				addToExpression('0');
			
			if (c == '0' && Double.parseDouble(number) == 0 && !number.contains("."))
				c = ' ';
			
			
			if (expression.endsWith(" ") && !Character.isDigit(c) && !Character.isLetter(c) && c != '('){
				
				StringBuilder builder = new StringBuilder(expression);
		
				builder.delete(builder.length()-3,builder.length());
	
				expression = builder.toString();
			}
			
			if (Character.isDigit(c)|| c == '.' || c == '(' || c == ')')
				expression += c;
			else if (c == '%') {
				
				System.out.println(numbers.get(numbers.size()-1));
				
				StringBuilder builder = new StringBuilder(expression);
				
				if (expression.endsWith(")"))	
					builder.delete(expression.indexOf('('),builder.length());
				else
					builder.delete(builder.length()-
							numbers.get(numbers.size()-2).toString().length(),builder.length());
	
				builder.append(BigOperation.format(getLastNumber(), digitsDisplayed).toString());
				
				number = "0";
				
				expression = builder.toString();
				
			} else if (c != ' ')
				expression += " "+c+" ";
			
		}

		// Used to add the result after pressing enter on the keyboard;
		public void addToExpression(String s) {
			
			StringBuilder builder = new StringBuilder(s);

			number = s;
			
			for (int i = 0; i < builder.length(); i++)
				addToExpression(builder.charAt(i));
			
			number = "0";
		}
		
		
		// Turn the 'number' or 'function' string into a real object
		public void consolidateNumber(){
			
			if (hasInside) 
				inside.consolidateNumber();
			
			else if (!function.equals("") && !number.equals("0")) {
				
				numbers.add(doFunction(function, new BigDecimal(number)));

				number = "0";
				function = "";

				if (mustNegate)
					numbers.set(numbers.size() - 1,
							numbers.get(numbers.size() - 1).negate());

			} else if (!number.equals("0")) {
				
				numbers.add(new BigDecimal(number));
				number = "0";
				
			}
			
		}

		
		public void returnSequence() {

			for (BigDecimal i : numbers) {

				System.out.println("term number " + numbers.indexOf(i) + ": "
						+ i.toString());

			}

			for (char i : operations) {

				System.out.println("operation number " + operations.indexOf(i)
						+ ": " + i);

			}

		}

		public void printExpression() {

			for (int i = 0; i < numbers.size(); i++) {

				System.out.print(numbers.get(i));
				if (i < operations.size())
					System.out.print(operations.get(i));

			}

		}

	
		public BigDecimal findPercentage(BigDecimal number, BigDecimal percentage, char operator) {
			
			BigDecimal result = percentage.divide(HUNDRED);
			
			if (operator == '+' || operator == '-')
				result = result.multiply(number);
			
			return result;
		}
		
		public BigDecimal doFunction(String function, BigDecimal number) {

			for (Function i : functions) {

				if (function.equals(i.name)) {
					return i.calculate(number);
				}

			}
			return null;
		}

		public BigDecimal calculate(int priority) {

			consolidateNumber();
			
			if (inside != null) {
				
				if (inside.numbers.size() > 1) {
					BigDecimal result = inside.calculate(priority);
				
				if (inside.numbers.size() == 0 && inside.operations.size() == 0) {
				
					numbers.add(result);
				
					inside = null;
					hasInside = false;
				}
					return result;
					
				} else return inside.numbers.get(0);
				
			} else {

				// Exponentiation

				for (char c : operations) {

					BigDecimal temp = null;

					if (c == '^') {

						temp = BigOperation.pow(
								numbers.get(operations.indexOf(c)),
								numbers.get(operations.indexOf(c) + 1));

						numbers.set(operations.indexOf(c), null);
						numbers.set(operations.indexOf(c) + 1, temp);
						operations.set(operations.indexOf(c), ' ');
					}

				}

				while (operations.contains(' ')) {
					operations.remove(operations.indexOf(' '));
				}

				while (numbers.contains(null)) {
					numbers.remove(numbers.indexOf(null));
				}

				// Multiplication and division
				if (priority <= 2) {

					for (char c : operations) {

						BigDecimal temp = null;

						if (c == '*' || c == '/') {

							if (c == '*') {
								temp = numbers.get(operations.indexOf(c))
										.multiply(
												numbers.get(operations
														.indexOf(c) + 1));

							} else if (c == '/') {

								temp = numbers.get(operations.indexOf(c))
										.divide(numbers.get(operations
												.indexOf(c) + 1), 30,
												BigDecimal.ROUND_HALF_EVEN);

							}

							numbers.set(operations.indexOf(c), null);
							numbers.set(operations.indexOf(c) + 1, temp);
							operations.set(operations.indexOf(c), ' ');
						}

					}

					while (operations.contains(' '))
						operations.remove(operations.indexOf(' '));

					while (numbers.contains(null))
						numbers.remove(numbers.indexOf(null));

				}

				// Addtion and subtraction
				if (priority <= 1) {

					for (char c : operations) {

						BigDecimal temp = null;

						if (c == '+' || c == '-') {

							if (c == '+') {
								temp = numbers.get(operations.indexOf(c)).add(
										numbers.get(operations.indexOf(c) + 1));

							} else if (c == '-') {

								temp = numbers.get(operations.indexOf(c))
										.subtract(
												numbers.get(operations
														.indexOf(c) + 1));

							}

							numbers.set(operations.indexOf(c), null);
							numbers.set(operations.indexOf(c) + 1, temp);
							operations.set(operations.indexOf(c), ' ');

						}

					}

					while (operations.contains(' '))
						operations.remove(operations.indexOf(' '));

					while (numbers.contains(null))
						numbers.remove(numbers.indexOf(null));

				}

				return numbers.get(numbers.size() - 1);

			}
		}

		public BigDecimal calculateLastValue(int priority) {
			
			consolidateNumber();
			
			for (int i = operations.size()-1; i > 0; i--) {
				
				char c = operations.get(i);
				
				if (priorityOf(c) > priority) {
					
					BigDecimal temp = null;
					
					if (c == '^') {

						temp = BigOperation.pow(
									numbers.get(operations.indexOf(c)),
									numbers.get(operations.indexOf(c) + 1));

							numbers.set(operations.indexOf(c), null);
							numbers.set(operations.indexOf(c) + 1, temp);
							operations.set(operations.indexOf(c), ' ');
							
					} else  if (c == '*' || c == '/') {

						if (c == '*') {
							
								temp = numbers.get(operations.indexOf(c))
										.multiply(numbers.get(operations.indexOf(c) + 1));

						} else if (c == '/') {

							temp = numbers.get(operations.indexOf(c))
											.divide(numbers.get(operations.indexOf(c) + 1), 30,
													BigDecimal.ROUND_HALF_EVEN);

						}

							numbers.set(operations.indexOf(c), null);
							numbers.set(operations.indexOf(c) + 1, temp);
							operations.set(operations.indexOf(c), ' ');

					} else if (c == '+' || c == '-') {

							if (c == '+') {
								temp = numbers.get(operations.indexOf(c)).add(
											numbers.get(operations.indexOf(c) + 1));

							} else if (c == '-') {

								temp = numbers.get(operations.indexOf(c))
											.subtract(numbers.get(operations.indexOf(c) + 1));

							}

								numbers.set(operations.indexOf(c), null);
								numbers.set(operations.indexOf(c) + 1, temp);
								operations.set(operations.indexOf(c), ' ');

						}

						while (operations.contains(' '))
							operations.remove(operations.indexOf(' '));

						while (numbers.contains(null))
							numbers.remove(numbers.indexOf(null));

					} else break;
			}
			
			return numbers.get(numbers.size()-1);
			
		}
		
		public BigDecimal calculateAll() {
			
			expression = "";
			
			do {
				calculate(0);
			} while (hasInside || numbers.size() > 1);
			
			return numbers.get(numbers.size()-1);
			
		}
		
		
		public BigDecimal getLastNumber() {
			
			if (numbers.size() == functions.size())
				consolidateNumber();
			
			return numbers.get(numbers.size()-1);
			
		}
		
		public String getLastExpressionElement() {
			
			StringBuilder builder = new StringBuilder(expression);
			
			return null;
		}
		
		
		// Methods to interact with the priority system ( exponentiation, multiplication and addition)
		
		public boolean hasGreaterPriority(char c) {

			System.out.println(priority);
			
			if (priority == 0)
				return false;

			if (priority == 3)
				return true;

			else if (priority == 2) {
				if (c != '^')
					return true;
				else
					return false;

			} else if (c == '+' || c == '-') {
				return true;
			} else
				return false;

		}

		public int priorityOf(char c) {

			if (c == '^')
				return 3;

			else if (c == '*' || c == '/')
				return 2;
			else if (c == '+' || c == '-')
				return 1;
			else
				return 0;

		}

		public void setPriority(char c) {

			if (c == '^')
				priority = 3;

			else if (c == '*' || c == '/')
				priority = 2;
			else if (c == '+' || c == '-')
				priority = 1;
			else
				priority = 0;

		}
		
	}

	public abstract class Function {

		String name;

		public Function() {
		}

		public Function(String s) {
			name = s;
		}

		public abstract BigDecimal calculate(BigDecimal b);

	}

	public class privateFunction extends Function {

		String expression;

		public privateFunction(String name, String exp) {
			this.name = name;
			expression = exp;
		}

		public BigDecimal calculate(BigDecimal b) {

			String test = expression;

			while (test.contains("x")) {
				test = expression.replace("x", b.toString());
			}

			Expression ex = new Expression(test);

			return ex.calculate(0);
		}
	}

	
	// Set config stats on startup
	
	public void setDigitsShown(int i){
		
		digitsDisplayed = i-1; // -1 because testing told me so (see "being a noob at programming")
		
	}

	public void setDigitsCalculated(int i){
		
		digitsCalculated = i;
		
	}
	
	// Getters and Setters
	
 	public void setMemory(BigDecimal memory) {
		this.memory = memory;
		gui.changeDisplay(gui.getMemoryLabel(), memory, 5);
	}
 	
	public BigDecimal getMemory() {
		return memory;
	}
	
	public BigDecimal getDisplayedNumber(){
		
		ex.consolidateNumber();
		return ex.getLastNumber();
		
	}
}
