package frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import main.Main;
import calculadoras.Padrão;
import calculadoras.engine.EstatísticaEngine;

public abstract class ExtraWindow extends JDialog {

	Padrão calculadora;
	JButton addToList, openExtras;
	
	protected BigDecimal numberToAdd;
	
	ImageIcon arrowLeft, arrowRight;
	
	GridBagConstraints c = new GridBagConstraints();
	
	public ExtraWindow(){
		
		setLayout(new GridBagLayout());
		
		c.anchor = GridBagConstraints.EAST;
		
		calculadora = new Padrão(false);
		c.gridx = 0;
		c.gridy = 0;
		add(calculadora.getGui(),c);
		
		addToList = new JButton("Add to List");
		addToList.setPreferredSize(new Dimension(114,26));
		addToList.setFocusable(false);
		c.insets = new Insets(5,5,5,5);
		c.gridy = 1;
		add(addToList,c);
		
		addToList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				numberToAdd = calculadora.getEngine().getDisplayedNumber();
				
				addToList();

			}
		});
		
		addWindowListener(new WindowListener() {
			
			public void windowOpened(WindowEvent arg0) {}
			
			public void windowIconified(WindowEvent arg0) {}
			
			public void windowDeiconified(WindowEvent arg0) {}

			public void windowDeactivated(WindowEvent arg0) {}
			
			public void windowClosing(WindowEvent arg0) {
				calculadora.getEngine().resetAll();
				openExtras.doClick();
			}
			
			public void windowClosed(WindowEvent arg0) {}
			
			public void windowActivated(WindowEvent arg0) {}
		});
		
		pack();
		
	}
	
	public void showWindow(boolean show){
		
		setVisible(show);
		
		calculadora.requestFocus();
		
	}
	
	public JButton addButton(){
		
		arrowLeft = new ImageIcon(ExtraWindow.class.getResource("/images/Arrow Left.png"));
		arrowRight = new ImageIcon(ExtraWindow.class.getResource("/images/Arrow Right.png"));
		
		openExtras = new JButton(arrowRight);
		openExtras.setFocusable(false);
		openExtras.setPreferredSize(new Dimension(arrowLeft.getIconWidth()+10,arrowLeft.getIconHeight()+10));
		
		openExtras.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				toggle();
			}
		});
		
		return openExtras;
		
	}
	
	public void toggle(){
		
		if (openExtras.getIcon() == arrowRight) {
			
			openExtras.setIcon(arrowLeft);
			
			showWindow(true);
			
			setLocation(Main.getFrame().getLocation().x+Main.getFrame().getWidth(),Main.getFrame().getY());
			
		} else {
			openExtras.setIcon(arrowRight);
			showWindow(false);
		}
		
		
	}
	
	public abstract void addToList();
	
}
