package calculadoras.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import calculadoras.Estatística;

public class UnitConverterUI extends JFrame{

	JComboBox measurements;
	JList leftList, rightList;
	JButton swapNumbers, openWindow;
	JTextField numberInput, numberOutput;
	
	public UnitConverterUI(String[] measurements, String[] units){
		
		setLayout(new GridBagLayout());
		
		Font f = new Font("Dialog",1, 14);
		
		numberInput = new JTextField(10);
		numberInput.setFont(f);
		numberInput.setHorizontalAlignment(JTextField.RIGHT);
		
		numberOutput = new JTextField(10);
		numberOutput.setFont(f);
		numberOutput.setHorizontalAlignment(JTextField.RIGHT);
		
		setMeasurements(measurements);
		setUnits(units);
		setSwap();
		
		addItems();
	}
	
	public void createItems(){
		
		measurements = new JComboBox();
		leftList = new JList();
		rightList = new JList();
		
	}
	
	public void setMeasurements(String[] measures){
		
		measurements = new JComboBox(measures);
		
	}
	
	
	public void setUnits(String[] units){
		
		leftList = new JList(units);
		leftList.setPreferredSize(numberInput.getPreferredSize());
		
		rightList = new JList(units);
		rightList.setPreferredSize(numberInput.getPreferredSize());
	}
	
	public void setSwap(){
		
		ImageIcon image = new ImageIcon(Estatística.class.getResource("/images/Arrow Black.png"));
		System.out.println(image.getIconHeight()+"  "+image.getIconWidth());
		swapNumbers = new JButton(image);
		swapNumbers.setPreferredSize(new Dimension(20,25));
		
		System.out.println(swapNumbers.getPreferredSize());
	}
	
	public void addItems(){
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5,5,5,5);
		c.anchor = GridBagConstraints.WEST;
		
		add(measurements,c);
		
		c.gridy = 1;
		add(numberInput,c);
		
		c.gridy = 2;
		add(new JScrollPane(leftList),c);
		
		c.gridx = 1;
		
		c.gridy = 1;
		add(swapNumbers,c);
		
		c.gridx = 2;
		
		c.gridy = 1;
		add(numberOutput,c);
		
		c.gridy = 2;
		add(new JScrollPane(rightList),c);
	}
}