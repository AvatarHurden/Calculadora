package configurations.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import configurations.Information;

public class Padrão extends JPanel{

	GridBagConstraints c = new GridBagConstraints();
	
	// JTextFields with the information to be changed
	
	JTextField digitsDisplayed = new JTextField(4);
	JTextField digitsCalculated = new JTextField(4);
	
	JLabel digitsDisplayedError = new JLabel(" ");
	JLabel digitsCalculatedError = new JLabel(" ");
	
	// Information
	Information info = new Information();
	
	public Padrão(Information inf){
		
		info = inf;
		
		setLayout(new GridBagLayout());
		
		c.anchor = GridBagConstraints.WEST;
		
		c.gridy = 0;
		digitsDisplayed();
		
		c.gridy++;
		digitsCalculated();
		
		c.gridy++;
		add(saveButton(),c);
		
	}

	public JPanel saveButton(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout());
		panel.setAlignmentY(RIGHT_ALIGNMENT);
		
		JButton save = new JButton("Save");
		
		save.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				
				// Check to see if digits displayed is valid
				try {
					info.setDigitsShown(Integer.parseInt(digitsDisplayed.getText()));
					digitsDisplayedError.setText(" ");
					
				} catch(NumberFormatException e){
					
					digitsDisplayedError.setText("Only integers are accepted");
					
				}
				
				// Check to see if digits calculated is valid
				try {
					info.setDigitsCalculated(Integer.parseInt(digitsCalculated.getText()));
					digitsCalculatedError.setText(" ");
					
				} catch(NumberFormatException e){
					
					digitsCalculatedError.setText("Only integers are accepted");
					
				}
				
			}
		});
		
		panel.add(save);
		
		return panel;
	}
	
	public void digitsCalculated(){
		
		c.gridwidth = 1;
		c.gridx = 0;
		
		add(new JLabel("Decimals to be calculated: "),c);
		
		digitsCalculated.setText(""+info.getDigitsCalculated());
		
		c.gridx = 1;
		add(digitsCalculated,c);
		
		JLabel help = new JLabel(" ? ");
		help.setBorder(BorderFactory.createLineBorder(Color.black));
		help.setToolTipText("<html>Number of decimal places calculated," +
				"<br>reduce to increase speed of calculator.</html>");
		
		c.gridx = 2;
		add(help,c);
		
		digitsCalculatedError.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		add(digitsCalculatedError,c);
		
	}
	
	public void digitsDisplayed(){
		
		c.gridx = 0;
		add(new JLabel("Digits to be displayed: "),c);
		
		digitsDisplayed.setText(""+info.getDigitsShown());
		
		c.gridx = 1;
		add(digitsDisplayed,c);
		
		digitsDisplayedError.setForeground(Color.RED);
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		add(digitsDisplayedError,c);
		
	}
	
}
