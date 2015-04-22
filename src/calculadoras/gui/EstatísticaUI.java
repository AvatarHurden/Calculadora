package calculadoras.gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.Timer;

import operations.BigOperation;
import operations.DivideLines;

public class EstatísticaUI extends JPanel {
	
	// Items Permanentes
	JLabel text;
	JTextField input;
	JButton calculate, sort, deleteAll;
	
	JButton openWindow;
	
	public ArrayList<FunctionPanel> functions;
	public ArrayList<ElementPanel> elements = new ArrayList<ElementPanel>();
	
	// Listas do painel de items
	JPanel finalPanel; // panel para colocar no listScroller
	JPanel outerPanel; // panel que une todos os acima
	JScrollPane listScroller; // Coisa final, que tem rodinha \o/
	
	GridBagConstraints c = new GridBagConstraints();
	
	
	public EstatísticaUI(final JButton windowButton){
	 	
		openWindow = windowButton;
		
		setLayout(new GridBagLayout());
		
		c.anchor = GridBagConstraints.NORTH;
		
		addItems();
		
		setVisible(true);
		
	}
	
	
	// Create the individual components of the GUI
	
	public JScrollPane createScroller(){
		
		finalPanel = new JPanel();
		finalPanel.setLayout(new GridLayout(0,1));
		
		outerPanel = new JPanel();
		outerPanel.add(finalPanel);
		
		listScroller = new JScrollPane(outerPanel);
		listScroller.setLayout(new ScrollPaneLayout());
		listScroller.setFocusable(true);
		listScroller.setMinimumSize(new Dimension(0, 0));
		listScroller.requestFocus();
		
//		listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		int i = 15;
		
		listScroller.setPreferredSize(new Dimension(250, 19*i+10)); 
		c.insets = new Insets(0,0,0,5);
		
		return listScroller;
	}
	
	public JPanel topButtons(){
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5,5,5,5);
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		calculate = new JButton("Calcular");
		calculate.setPreferredSize(new Dimension(160,36));
		c.gridheight = 2;
		
		panel.add(calculate,c);
		
		c.gridx = 1;
		c.gridheight = 1;
		panel.add(openWindow,c);
		
		return panel;
	}
	
	public JPanel massEdit(){
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		deleteAll = new JButton("Delete"); 
		deleteAll.setFont(new Font("",1,8));					 
		deleteAll.setPreferredSize(new Dimension(57,17));	
		
		JPanel emptyLabel2 = new JPanel();                 		  // Espaços vazios para adicionar botões
		emptyLabel2.setPreferredSize(new Dimension(50,17)); 	 // no futuro
		
		JPanel emptyLabel3 = new JPanel();                   
		emptyLabel3.setPreferredSize(new Dimension(50,17));
		
		sort = new JButton("Sort");
		sort.setFont(new Font("",1,8));
		sort.setPreferredSize(new Dimension(50, 17));
		
		buttonPanel.add(deleteAll);
		buttonPanel.add(emptyLabel2);
		buttonPanel.add(emptyLabel3);
		buttonPanel.add(sort);
		
		return buttonPanel;
	}

	public JPanel functionCenter(){
		
		functions = new ArrayList<FunctionPanel>();
		
		functions.add(new FunctionPanel("Soma"));
		functions.add(new FunctionPanel("Média"));
		functions.add(new FunctionPanel("Mediano"));
		functions.add(new FunctionPanel("Moda"));
		functions.add(new FunctionPanel("Variância"));
		functions.add(new FunctionPanel("Desvio Padrão"));
		
		JPanel functionPanel = new JPanel(new GridLayout(0,1));
		
		for (FunctionPanel i : functions){
			functionPanel.add(i);
		}
		
		return functionPanel;
		
	}
	
	
	// Add all of the permanent components of the GUI
	
	public void addItems(){
		
		text = new JLabel("Insira os valores, com um espaço entre eles");
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		add(text,c);
		
		input = new JTextField();
		input.setPreferredSize(new Dimension(text.getPreferredSize().width,20));
		c.gridy = 1;
		add(input, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 2;
		add(topButtons(),c);
			
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		c.insets = new Insets(0,0,0,0);
		add(massEdit(), c);
		
		c.gridy = 3;
		add(createScroller(),c);
		
		c.gridx = 1;
		add(functionCenter(),c);
		
	}
	
	
	// Class with the panels that show functions
	
	public class FunctionPanel extends JPanel{
		
		Font fonteResult = new Font("Arial",1,13);
		Font fonteName = new Font("Arial",1,15);
		
		JTextField result;
		JLabel label;
		JButton copyButton;
		
		public FunctionPanel(String name){
			
			setName(name);
			
			setLayout(new GridBagLayout());
			setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(0,4,0,0);
			c.anchor = GridBagConstraints.EAST;
			
			String nome = DivideLines.divideLines(name,9,false);
			int numLines = 0;
			
			while (nome.indexOf("<br>") != -1){
				numLines++;
				nome = nome.replaceFirst("<br>", " ");
			}
			
			label = new JLabel(DivideLines.divideLines(name,9,false));
			label.setFont(fonteName);
			label.setPreferredSize(new Dimension(
					label.getFontMetrics(fonteName).stringWidth("L")*9, 14 + 22*numLines));
			
			c.gridx = 0;
			add(label,c);
			
			result = new JTextField();
			result.setPreferredSize(new Dimension(114,22));
			result.setFont(fonteResult);
			result.setHorizontalAlignment(JTextField.RIGHT);
			
			c.gridx = 1;
			add(result,c);
			
			copyButton = new JButton();
			copyButton.setPreferredSize(new Dimension(10,10));
			
			c.gridx = 2;
			add(copyButton,c);
			
			setPreferredSize(new Dimension(136+label.getPreferredSize().width,getPreferredSize().height));
			
		}
	
		public void setResult(String s){
			result.setText(s);
		}
		
		public String getResult(){
			return result.getText();
		}
		
		public JButton getButton(){
			return copyButton;
		}
	}
	
	// Methods related to the FunctionPanel
	
	public void setFunctionValue(int i, String s){
		
		functions.get(i).setResult(s);
		
	}
	
	public void removeElement(ElementPanel panel){
		
		finalPanel.remove(panel);
		elements.remove(panel);
		
	}
	
	public void removeAllElements(){
		
		finalPanel.removeAll();
		elements.removeAll(elements);
		
	}
	
	public void scrollDown(){
		
		int height = (int)outerPanel.getPreferredSize().getHeight();
		Rectangle rect = new Rectangle(0,height,0,0);
		
		outerPanel.scrollRectToVisible(rect);
		
	}
	

	public void revalidateScroller(){
		listScroller.revalidate();
	}
	
	
	// Class to create panel on which elements will be displayed
	
	public class ElementPanel extends JPanel{
		
		JButton delete;
		JLabel emptyLabel;
		JLabel elementoLabel;
		
		// For individual items added by user
		public ElementPanel(BigDecimal element){
			
			setLayout(new GridBagLayout());
			setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
			
			GridBagConstraints c = new GridBagConstraints();
			
			elementoLabel = new JLabel(BigOperation.format(element, 14)+"  ", JLabel.RIGHT);
			elementoLabel.setPreferredSize(new Dimension(180, 18));
			elementoLabel.setFont(new Font("",0, 18));
			
			JLabel frontLabel = new JLabel();
			frontLabel.setPreferredSize(new Dimension(30-elementoLabel.getWidth(), 18));
			
			c.gridy = 0;
			c.gridx = 0;
			add(frontLabel,c);
			
			c.gridx++;
			add(elementoLabel,c);
			
			delete = new JButton();
			delete.setPreferredSize(new Dimension(10,10));
			delete.setToolTipText("Delete");
			delete.setVisible(false);
			
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(10,18));
			
			c.gridx++;
			add(delete,c);
			add(emptyLabel,c);
		}

		// For progressions (arithmetic and geometric)
		ElementPanel(List<BigDecimal> list){
			
		}
		
		public JButton getDelete(){
			return delete;
		}
		
		public JLabel getEmptyLabel(){
			return emptyLabel;
		}
		
		public void showDeleteButton(boolean show){
			
			delete.setVisible(show);
			emptyLabel.setVisible(!show);
			
		}
	
		public boolean isMouseOver(Object o){
			
			if (o == this) return true;
			else if (o == emptyLabel) return true;
			else if (o == delete) return true;
			else return false;
		}
	
		public void setText(String s){
			elementoLabel.setText(s+"  ");
		}
		
		
		public void setSize(int height){
			
			setPreferredSize(new Dimension(getWidth(),height));
			
		}
	}
	
	// Methods related to the ElementPanel
	
	public ElementPanel addElement(BigDecimal element){
		
		ElementPanel panel = new ElementPanel(element);
		
		elements.add(panel);
		finalPanel.add(panel);
		
		scrollDown();
		
		return panel;
	}
	
	// Change the color of the panel representing the median number, to make identifying it easier.
	
	public void setMedianPanelColor(ElementPanel panel){
		
		for(ElementPanel i :elements){
			i.setBackground(new Color(238,238,238));
		}
		
		panel.setBackground(new Color(255, 200, 200));
		
		// Color(205, 177, 177));
		
	}
	
	public void setMedianPanelColor(ElementPanel panel1, ElementPanel panel2){
		
		for(ElementPanel i :elements){
			i.setBackground(new Color(238,238,238));
		}
		
		panel1.setBackground(new Color(205, 177, 177));
		panel2.setBackground(new Color(205, 177, 177));
		
	}
	
	// Getters
	
	public JButton getCalculate(){
		return calculate;
	}
	
	public String getInput(){
		return input.getText();
	}
	
	public void setInput(String s){
		input.setText(s);
	}
	
	public JButton getDeleteAll(){
		return deleteAll;
	}
	
	public JButton getSort(){
		return sort;
	}
	
}



