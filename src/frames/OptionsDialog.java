package frames;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import configurations.Information;
import configurations.StartUp;
import configurations.panels.Padrão;


public class OptionsDialog extends JDialog {

	// JPanels containing the categories of options (left) and the options themselves (right)
	JPanel leftPanel, rightPanel;
	JPanel selectedPanel;
	ArrayList<JPanel> categories = new ArrayList<JPanel>();
	ArrayList<JPanel> associatedPanels = new ArrayList<JPanel>();
	GridBagConstraints c;
	
	public OptionsDialog(final FrameCalculadora source){
		
		setLayout(new FlowLayout());
		
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
		addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {           
            	
            	source.setConfigs();
            	source.setEnabled(true);
            	source.setVisible(true);
            	
            }
        });
		
		leftPanel = new JPanel(new GridLayout(0,1));	
		leftPanel.setPreferredSize(new Dimension(100,400));
		add(leftPanel);
		
		JPanel innerPanel = new JPanel();
		leftPanel.add(innerPanel);
		
		
		JPanel generalPanel = new JPanel();
		generalPanel.add(new JLabel("generalright"));
		associatedPanels.add(generalPanel);
		categories.add(createCategory("general", generalPanel));
		innerPanel.add(categories.get(0));
		
		
		Padrão padrãoPanel = new Padrão(source.getInformation());
		associatedPanels.add(padrãoPanel);
		categories.add(createCategory("Padrão", padrãoPanel));
		innerPanel.add(categories.get(1));
		
		rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rightPanel.setPreferredSize(new Dimension(400,400));
		rightPanel.setAlignmentY(LEFT_ALIGNMENT);
		rightPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		add(rightPanel);
		
		
		pack();
		setVisible(true);
		
	}

	// Method to create the Panel on the left, which changes color on hover and, when clicked,
	// shows the "associatedPanel" on the right
	
	public JPanel createCategory(String text, JPanel associatedPanel){
		
		JPanel panel = new JPanel();
		panel.add(new JLabel(text));
		
		panel.setPreferredSize(new Dimension(100,panel.getPreferredSize().height));
		
		panel.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {}
			
			public void mousePressed(MouseEvent e) {}
			
			public void mouseExited(MouseEvent e) {
				
				for(JPanel i : categories){
					if(e.getSource().equals(i)){
						if (!e.getSource().equals(selectedPanel)) i.setBackground(new Color(238,238,238));
					}
				}
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
				for(JPanel i : categories){
					if(e.getSource().equals(i)){
						if (!e.getSource().equals(selectedPanel))
							i.setBackground(new Color(200,200,255));
					}
				}
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
				for(JPanel i : categories){
					if(e.getSource().equals(i)){
						if (!e.getSource().equals(selectedPanel)){
							
							i.setBackground(new Color(100,100,255));
							setSelected(i,associatedPanels.get(categories.indexOf(i)));
						}
					} else	i.setBackground(new Color(238,238,238));
				}
				
			}
		});
		
		return panel;
		
	}
	
	public void setSelected(JPanel panel,JPanel associatedPanel){
		
		selectedPanel = panel;
		rightPanel.removeAll();
		rightPanel.add(associatedPanel);
		
		repaint();
		pack();
		
		
	}
	
}
