package frames;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import calculadoras.Estat�stica;
import calculadoras.Padr�o;
import configurations.FileManagment;
import configurations.Information;
import configurations.StartUp;


@SuppressWarnings("serial")
public class FrameCalculadora extends JFrame{
	
	// Menu
	JMenuBar menuBar;
	JMenu menuFile, menuExibir, menuTools;
	JMenuItem menuSave, menuOpen, menuPadr�o, menuEstat�stico, menuOptions;
	ActionListener estat�sticaOpen, estat�sticaSave, padr�oOpen, padr�oSave;
	JFrame frame = new JFrame();
	JFileChooser fc = new JFileChooser();
	
	// Calculadoras
	Padr�o Padr�o;
	Estat�stica Estat�stica;
	 
	// Coisas
	boolean padr�oFoiCriado = false;
	boolean estat�sticaFoiCriado = false;
		
	// Which is chosen
	private String selected;
	final String estat�stica = "estat�stica";
	final String padr�o = "padr�o";
	
	// Information Classes
	Information info = new Information();
	OptionsDialog options;
	
	public FrameCalculadora(){
		
		setResizable(false);
		
		setLayout(new FlowLayout());
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) {
                
            	StartUp.saveOnClose((FrameCalculadora) e.getSource());
            	
            	System.exit(JFrame.EXIT_ON_CLOSE);
            }
        });
		
		addMenu();
		
		info = StartUp.read(info);
		
		startUp();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				FrameCalculadora.class.getResource("/images/Calculadora.png")));
		
		setVisible(true);
	}
	

	public void addMenu(){
	
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
	
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
	
		menuSave = new JMenuItem("Save");
		menuFile.add(menuSave);
		
		padr�oSave = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {			
				
				FileManagment.padr�o(FileManagment.save,Padr�o);
				
			}
		};	
		estat�sticaSave = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {			
				
				FileManagment.estat�stica(FileManagment.save,Estat�stica);
				
			}
		};	
		
		
		menuOpen = new JMenuItem("Open");
		menuFile.add(menuOpen);
		
		estat�sticaOpen = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FileManagment.estat�stica(FileManagment.open,Estat�stica);
				
			}
		};		
		padr�oOpen = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FileManagment.padr�o(FileManagment.open,Padr�o);
			}
		};

		FileManagment.setCalculadoras(Estat�stica, Padr�o);
		
		menuExibir = new JMenu("Exibir");
		menuBar.add(menuExibir);
		
		menuPadr�o = new JMenuItem("Padr�o");
		menuExibir.add(menuPadr�o);
		
		menuPadr�o.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				showPadr�o();
				
			}
		});
	
		menuEstat�stico = new JMenuItem("Estat�stico");
		menuExibir.add(menuEstat�stico);
		
		menuEstat�stico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				showEstat�stica();
				
			}
		});
		
		try{
			if (Padr�o.isVisible() == true){
				padr�oMenuOptions();
			} else if (Estat�stica.isVisible()){ 
				estat�sticaMenuOptions();
			}
		} catch (NullPointerException e){
			estat�sticaMenuOptions();
		}
		
		menuTools = new JMenu("Tools");
		menuBar.add(menuTools);
		
		menuOptions = new JMenuItem("Options");
		menuTools.add(menuOptions);
		
		menuOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				createOptionsDialog();
				
			}
		});
		
	}
	
	// Methods to create or make visible the calculators
	
	public void showPadr�o(){
		
		if (estat�sticaFoiCriado) Estat�stica.setVisible(false);
		
		if (padr�oFoiCriado == false){
			Padr�o = new Padr�o(true);
			add(Padr�o.getGui());
			padr�oFoiCriado = true;
		} 
		
		padr�osetConfigs();
		
		selected = padr�o;
		Padr�o.setVisible(true);
		Padr�o.requestFocus();
		pack();
		padr�oMenuOptions();
		
	}
	
	public void showEstat�stica(){
		
		if (padr�oFoiCriado) Padr�o.setVisible(false);
		
		if (estat�sticaFoiCriado == false){
			Estat�stica = new Estat�stica();
			add(Estat�stica.getGUI());
			estat�sticaFoiCriado = true;
		}
		
		selected = estat�stica;
		Estat�stica.setVisible(true);
		pack();
		estat�sticaMenuOptions();
		
	}
	
	public void padr�oMenuOptions(){
		
		menuPadr�o.setEnabled(false);
		menuEstat�stico.setEnabled(true);
		
		FileManagment.createFolders();
		
		menuSave.removeActionListener(estat�sticaSave);
		menuSave.addActionListener(padr�oSave);
		
		menuOpen.removeActionListener(estat�sticaOpen);
		menuOpen.addActionListener(padr�oOpen);
	}
	
	public void estat�sticaMenuOptions(){
		
		menuEstat�stico.setEnabled(false);
		menuPadr�o.setEnabled(true);
		
		menuSave.removeActionListener(padr�oSave);
		menuSave.addActionListener(estat�sticaSave);
		
		menuOpen.removeActionListener(padr�oOpen);
		menuOpen.addActionListener(estat�sticaOpen);
		
	}

	// Method to create the OptionDialog, inserting the Frame as the parameter
	
	public void createOptionsDialog(){
		
		options = new OptionsDialog(this);
		options.setAlwaysOnTop(true);
		
		setEnabled(false);
		
	}
	
	
	
	public void startUp(){
		
		selected = info.getCalculator();
		
		if (selected.equals(padr�o)){
			showPadr�o();
		} else {
			showEstat�stica();
		}
		
		setConfigs();
	}
	
	public void setConfigs(){
		
		setLocation(info.getxPosition(), info.getyPosition());
		
		selected = info.getCalculator();
		
		if (padr�oFoiCriado) padr�osetConfigs();
		if (estat�sticaFoiCriado) ;
	}
	
	void padr�osetConfigs(){
		
		Padr�o.getEngine().setDigitsShown(info.getDigitsShown());
		
		Padr�o.getEngine().setDigitsCalculated(info.getDigitsCalculated());
		
	}
	
	// Getters and Setters
	
	public String getSelected() {
		return selected;
	}
	

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public Padr�o getPadr�o(){
		return Padr�o;
	}

	public Estat�stica getEstat�stica(){
		return Estat�stica;
	}

	public Information getInformation(){
		return info;
	}
}