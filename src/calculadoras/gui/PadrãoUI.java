package calculadoras.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import operations.BigOperation;

import calculadoras.Padrão;

public class PadrãoUI extends JPanel{

	// Basic Buttons and Labels
	private JLabel screenLabel, memoryLabel, equationLabel;
	private JButton reset, decimal, leftParentheses, rightParentheses, backspace;
	private ImageIcon sqrtIcon;
	private JButton[] numpadArray, functionsArray, memoryArray;
		
	// Strings to determine which label will be edited
	public String memory; 
	public String screen;
		
	GridBagConstraints c = new GridBagConstraints();
		
		
	public PadrãoUI(boolean withMemory){
		   
		setLayout(new GridBagLayout());
		
		addLabels(withMemory);
		
		addPanels(withMemory);
		
		setFocusable(true);
		requestFocusInWindow();
		
	}
	
	
	// Functions to create the panels with numbers and buttons
	
	public JPanel memoryFunctions(JButton[] memoryArray){
		
		JPanel memoryFunctions = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5,5,0,5);
		
		memoryArray[0] = new JButton("MS");
		memoryArray[0].setToolTipText("Save to Memory");
		
		memoryArray[1] = new JButton("MC");
		memoryArray[1].setToolTipText("Clear Memory");
		
		memoryArray[2] = new JButton("MR");
		memoryArray[2].setToolTipText("Recall Memory");
		
		memoryArray[3] = new JButton("M+");
		memoryArray[3].setToolTipText("Add to Memory");
		
		memoryArray[4] = new JButton("M-");
		memoryArray[4].setToolTipText("Subtract from Memory");
		
		for (int i = 0; i< memoryArray.length;i++){
			
			memoryArray[i].setActionCommand(memoryArray[i].getText());
			memoryArray[i].setPreferredSize(new Dimension(52, 26));
			memoryArray[i].setFocusable(false);
			
			c.gridx++;
			
			memoryFunctions.add(memoryArray[i],c);
		}
		
		return memoryFunctions;
	}

	public JPanel standardFunctions(JButton[] functions){
	
		JPanel standardFunctions = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5,5,0,5);
		
		functions[0] = new JButton("+-");
		functions[0].setActionCommand("negate");
		
		functions[1] = new JButton("/");
		functions[1].setActionCommand(functions[1].getText());
		
		functions[2] = new JButton("*");
		functions[2].setActionCommand(functions[2].getText());
		
		functions[3] = new JButton("-");
		functions[3].setActionCommand(functions[3].getText());
		
		functions[4] = new JButton("+");
		functions[4].setActionCommand(functions[4].getText());
		
		functions[5] = new JButton("%");
		functions[5].setActionCommand(functions[5].getText());
		functions[5].setActionCommand("%");
		
		sqrtIcon = new ImageIcon(Padrão.class.getResource("/images/square root.png"));
		functions[6] = new JButton(sqrtIcon);
		functions[6].setActionCommand("sqrt");
		
		functions[7] = new JButton("1/x");
		functions[7].setActionCommand("reciproc");
		
		functions[8] = new JButton("=");
		functions[8].setPreferredSize(new Dimension(41,57));
		functions[8].setActionCommand(functions[8].getText());
		
		for (int i = 0; i<functions.length;i++){
			
			if (i<5)		   c.gridx = 0;
			else    		   c.gridx = 1;
			
			if (i%5 == 0)      c.gridy = 0;
			else if (i%5 == 1) c.gridy = 1;
			else if (i%5 == 2) c.gridy = 2;
			else if (i%5 == 3) c.gridy = 3;
			else if (i%5 == 4) c.gridy = 4;
			
			if (i != 8) functions[i].setPreferredSize(new Dimension(52,26));
			else c.gridheight = 2;
			
			functions[i].setFocusable(false);
			
			standardFunctions.add(functions[i],c);
		}
		
		return standardFunctions;
	}

	public JPanel numpad(JButton[] buttons, JButton decimal){
 		
		JPanel numpad = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5,5,0,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		
		buttons[0] = new JButton("0");
		buttons[0].setFocusable(false);
		buttons[0].setBackground(Color.white);
		c.gridwidth = 2;
		c.gridy = 3;
		numpad.add(buttons[0],c);
		
		// Add decimal point
		decimal.setFocusable(false);
		decimal.setBackground(buttons[0].getBackground());
		c.gridwidth = 1;
		c.gridx = 2;
		numpad.add(decimal,c);
		
		for (int i = 1; i<buttons.length;i++){
			
			buttons[i] = new JButton(""+i);
			buttons[i].setPreferredSize(new Dimension(52, 26));
			buttons[i].setFocusable(false);
			buttons[i].setBackground(decimal.getBackground());
			
			if (i<4) 		c.gridy = 2;
			else if (i<7)   c.gridy = 1;
			else 			c.gridy = 0;

			if (i%3 == 1)   c.gridx = 0;
			else if(i%3==2) c.gridx = 1;
			else			c.gridx = 2;
			
			buttons[i].setActionCommand(String.valueOf(i));
			numpad.add(buttons[i],c);
		}
		
		return numpad;
	}
	
	
	// Function to add the labels with numbers (standard and memory) and the panels created above
	
	public void addLabels(boolean withMemory){
 		
 		c.gridx = -1; //In case of no Memory, numberLabel will start at 0
 		
 		c.gridy = 0;
		c.insets = new Insets(5,5,10,5);
		c.fill = GridBagConstraints.HORIZONTAL;
 		
 		if (withMemory) {
	 		memoryLabel = new JLabel("0");
			memoryLabel.setPreferredSize(new Dimension(52, 17));
			memoryLabel.setToolTipText("Memory");
			memoryLabel.setHorizontalAlignment(JTextField.CENTER);
			memoryLabel.setBorder(BorderFactory.createLineBorder(Color.gray));
			c.gridx = 0;
			c.gridheight = 1;
			add(memoryLabel, c);
			
			memoryLabel.setName("memory");
			memory = memoryLabel.getName();
			
			c.gridwidth = 4;   // Width of the screenLabel
 		} else c.gridwidth = 5;
		
 		equationLabel = new JLabel("");
 		equationLabel.setHorizontalAlignment(JTextField.RIGHT);
 		equationLabel.setPreferredSize(new Dimension(equationLabel.getWidth(),29));
 		equationLabel.setBackground(Color.white);
 		equationLabel.setOpaque(true);
 		equationLabel.setFont(new Font("Dialog",1, 14));
		c.gridheight = 1;
		c.gridx++;
		add(equationLabel, c);
 		
 		
		screenLabel = new JLabel("0");
		screenLabel.setHorizontalAlignment(JTextField.RIGHT);
		screenLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		screenLabel.setPreferredSize(new Dimension(screenLabel.getWidth(),29));
		screenLabel.setBackground(Color.white);
		screenLabel.setOpaque(true);
		screenLabel.setFont(new Font("Dialog",1, 20));
		c.gridy++;
		add(screenLabel, c);
		
		screenLabel.setName("screen");
		screen = screenLabel.getName();
 	}

	public void addPanels(boolean withMemory){
 		
		reset = new JButton("C");
		reset.setFocusable(false);
		reset.setPreferredSize(new Dimension(52, 29));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,5,5,5);
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		add(reset, c);
		
		if (withMemory) {
			memoryArray = new JButton[5];
			c.gridx = 0;
			c.gridy++;
			c.gridwidth = 5;
			c.insets = new Insets(0,0,0,0);
			add(memoryFunctions(memoryArray),c);
		}
		
		backspace = new JButton();
		backspace.setIcon(new ImageIcon(Padrão.class.getResource("/images/Backspace.png")));
		backspace.setFocusable(false);
		backspace.setEnabled(false);
		backspace.setPreferredSize(new Dimension(52, 26));
		c.insets = new Insets(5,5,0,5);
		c.gridy++;
		c.gridx = 0;
		c.gridwidth = 1;
		add(backspace, c);
		
		leftParentheses = new JButton("(");
		leftParentheses.setFocusable(false);
		leftParentheses.setPreferredSize(new Dimension(52, 26));
		c.gridx = 1;
		add(leftParentheses, c);
		
		rightParentheses = new JButton(")");
		rightParentheses.setFocusable(false);
		rightParentheses.setPreferredSize(new Dimension(52, 26));
		rightParentheses.setEnabled(false);
		c.gridx++;
		add(rightParentheses, c);
		
		numpadArray = new JButton[10];
		decimal = new JButton(".");
		c.insets = new Insets(0,0,5,0);
		c.gridx = 0;
		c.gridy++;
		c.gridheight = 4;
		c.gridwidth = 3;
		add(numpad(numpadArray,decimal),c);
		
		functionsArray = new JButton[9];
		c.gridx = 3;
		c.gridy--;
		c.gridheight = 5;
		c.gridwidth = 2;
		add(standardFunctions(functionsArray),c);
 		
 	}
	
	
	// Function to change color of label when ctrl + c
 	
	public void changeColor(final JLabel label){
	 		
	 		final Color original = label.getBackground();
	 		final Color grey = new Color(210,210,210);
	 		
	 		label.setBackground(grey);
	 		  
	 		final Timer timer = new Timer(100,new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					label.setBackground(original);
			 		((Timer)e.getSource()).stop();
				}
			});
	 		
	 		timer.start();
	 		
	 	}
		
	
	// Function to enable or disable all buttons (except "reset")
	
	public void enableButtons(boolean enable) {
		
		for (JButton i : numpadArray){
			i.setEnabled(enable);
		}
		for (JButton i : functionsArray){
			i.setEnabled(enable);
		}
		for (JButton i : memoryArray){
			i.setEnabled(enable);
		}
		decimal.setEnabled(enable);
		backspace.setEnabled(enable);
		rightParentheses.setEnabled(enable);
		leftParentheses.setEnabled(enable);
		
	}
	
	// Function to edit number on labels
	 
	public void changeDisplay(JLabel label, BigDecimal number, int digits){
		
		if (number.compareTo(new BigDecimal(Math.pow(10,-16)))>0 && 
				number.compareTo(new BigDecimal(Math.pow(10, 16)))<0) {
			label.setText(BigOperation.format(number,digits).toPlainString());
		} else {
			label.setText(BigOperation.format(number,digits).toString());
		}
		
	}
	
	public void addDigitToScreen(char c) {
		
		if (Double.parseDouble(screenLabel.getText()) != 0 || c != '0')
			if (Double.parseDouble(screenLabel.getText()) == 0 && !screenLabel.getText().contains(".") && c != '.')
				screenLabel.setText(String.valueOf(c));
			else screenLabel.setText(screenLabel.getText() + c);
		
	}
	
	public void removeDigitFromScreen() {
		
		if (screenLabel.getText().length() > 0) {
		
		StringBuilder string = new StringBuilder(screenLabel.getText());
		string.deleteCharAt(string.length()-1);
		screenLabel.setText(string.toString());
		}
		
		if (screenLabel.getText().isEmpty())
			screenLabel.setText("0");
		
	}
	
	public void setScreen(String s) {
		
		screenLabel.setText(s);
		
	}
	 
	
	// Function to get the last term in EquationLabel
	
	public void setExpression(String s){
		
		equationLabel.setText(s);
		
	}
	
	public String getLastTerm(){
		
		StringBuilder equation = new StringBuilder(equationLabel.getText());
		StringBuilder lastTerm = new StringBuilder();
		
		if (equation.length() != 0) {
			if (equation.charAt(equation.length()-1) == ' ') {
				
				lastTerm.append(equation.length()-1);
				
				for (int i = equation.length()-2; i >= 0; i--){
					
					if (equation.charAt(i) != ' ') {
						lastTerm.append(equation.charAt(i));
					} else break;
						
				}
				
			} else {
					
				for (int i = equation.length()-1; i >= 0; i--){
				
					if (equation.length() == 1 && i == equation.length()-2) i++;
				
					if (equation.charAt(i) != ' ') {
						lastTerm.append(equation.charAt(i));
					} else break;
					
				}
			}
		}
		
		lastTerm = lastTerm.reverse();
		
		if (lastTerm.length() > 1 && (lastTerm.indexOf("(") != -1 && lastTerm.indexOf(")") == -1) ) {
			System.out.println("contains left but not right");
			lastTerm.deleteCharAt(lastTerm.indexOf("("));
		}
		
		String s = lastTerm.toString();
		
		return s;
	}
	
	public void removeLastTerm(){
		
		StringBuilder equation = new StringBuilder(equationLabel.getText());
		
		equation.delete(equation.length()-getLastTerm().length(), equation.length());
		
//		equation.delete(equation.indexOf(getLastTerm()), equation.length());
		
		equationLabel.setText(equation.toString());
		
	}
	
	// Getters and Setters
	
	public JButton[] getMemoryArray() {
		return memoryArray;
	}

	public JButton[] getNumpadArray() {
		return numpadArray;
	}

	public JButton[] getFunctionsArray() {
		return functionsArray;
	}

	public JButton getDecimal() {
		return decimal;
	}

	public JButton getReset() {
		return reset;
	}
	
	public JButton getBackspace() {
		return backspace;
	}
	
	public JButton getLeftParentheses() {
		return leftParentheses;
	}
	
	public JButton getRightParentheses() {
		return rightParentheses;
	}

	public JLabel getScreenLabel() {
		return screenLabel;
	}
	
	public JLabel getEquationLabel() {
		return equationLabel;
	}
	
	public JLabel getMemoryLabel() {
		return memoryLabel;
	}	
}