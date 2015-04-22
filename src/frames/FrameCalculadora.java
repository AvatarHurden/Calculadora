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

import calculadoras.Estatística;
import calculadoras.Padrão;
import configurations.FileManagment;
import configurations.Information;
import configurations.StartUp;


@SuppressWarnings("serial")
public class FrameCalculadora extends JFrame{
	
	// Menu
	JMenuBar menuBar;
	JMenu menuFile, menuExibir, menuTools;
	JMenuItem menuSave, menuOpen, menuPadrão, menuEstatístico, menuOptions;
	ActionListener estatísticaOpen, estatísticaSave, padrãoOpen, padrãoSave;
	JFrame frame = new JFrame();
	JFileChooser fc = new JFileChooser();
	
	// Calculadoras
	Padrão Padrão;
	Estatística Estatística;
	 
	// Coisas
	boolean padrãoFoiCriado = false;
	boolean estatísticaFoiCriado = false;
		
	// Which is chosen
	private String selected;
	final String estatística = "estatística";
	final String padrão = "padrão";
	
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
		
		padrãoSave = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {			
				
				FileManagment.padrão(FileManagment.save,Padrão);
				
			}
		};	
		estatísticaSave = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {			
				
				FileManagment.estatística(FileManagment.save,Estatística);
				
			}
		};	
		
		
		menuOpen = new JMenuItem("Open");
		menuFile.add(menuOpen);
		
		estatísticaOpen = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FileManagment.estatística(FileManagment.open,Estatística);
				
			}
		};		
		padrãoOpen = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FileManagment.padrão(FileManagment.open,Padrão);
			}
		};

		FileManagment.setCalculadoras(Estatística, Padrão);
		
		menuExibir = new JMenu("Exibir");
		menuBar.add(menuExibir);
		
		menuPadrão = new JMenuItem("Padrão");
		menuExibir.add(menuPadrão);
		
		menuPadrão.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				showPadrão();
				
			}
		});
	
		menuEstatístico = new JMenuItem("Estatístico");
		menuExibir.add(menuEstatístico);
		
		menuEstatístico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				showEstatística();
				
			}
		});
		
		try{
			if (Padrão.isVisible() == true){
				padrãoMenuOptions();
			} else if (Estatística.isVisible()){ 
				estatísticaMenuOptions();
			}
		} catch (NullPointerException e){
			estatísticaMenuOptions();
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
	
	public void showPadrão(){
		
		if (estatísticaFoiCriado) Estatística.setVisible(false);
		
		if (padrãoFoiCriado == false){
			Padrão = new Padrão(true);
			add(Padrão.getGui());
			padrãoFoiCriado = true;
		} 
		
		padrãosetConfigs();
		
		selected = padrão;
		Padrão.setVisible(true);
		Padrão.requestFocus();
		pack();
		padrãoMenuOptions();
		
	}
	
	public void showEstatística(){
		
		if (padrãoFoiCriado) Padrão.setVisible(false);
		
		if (estatísticaFoiCriado == false){
			Estatística = new Estatística();
			add(Estatística.getGUI());
			estatísticaFoiCriado = true;
		}
		
		selected = estatística;
		Estatística.setVisible(true);
		pack();
		estatísticaMenuOptions();
		
	}
	
	public void padrãoMenuOptions(){
		
		menuPadrão.setEnabled(false);
		menuEstatístico.setEnabled(true);
		
		FileManagment.createFolders();
		
		menuSave.removeActionListener(estatísticaSave);
		menuSave.addActionListener(padrãoSave);
		
		menuOpen.removeActionListener(estatísticaOpen);
		menuOpen.addActionListener(padrãoOpen);
	}
	
	public void estatísticaMenuOptions(){
		
		menuEstatístico.setEnabled(false);
		menuPadrão.setEnabled(true);
		
		menuSave.removeActionListener(padrãoSave);
		menuSave.addActionListener(estatísticaSave);
		
		menuOpen.removeActionListener(padrãoOpen);
		menuOpen.addActionListener(estatísticaOpen);
		
	}

	// Method to create the OptionDialog, inserting the Frame as the parameter
	
	public void createOptionsDialog(){
		
		options = new OptionsDialog(this);
		options.setAlwaysOnTop(true);
		
		setEnabled(false);
		
	}
	
	
	
	public void startUp(){
		
		selected = info.getCalculator();
		
		if (selected.equals(padrão)){
			showPadrão();
		} else {
			showEstatística();
		}
		
		setConfigs();
	}
	
	public void setConfigs(){
		
		setLocation(info.getxPosition(), info.getyPosition());
		
		selected = info.getCalculator();
		
		if (padrãoFoiCriado) padrãosetConfigs();
		if (estatísticaFoiCriado) ;
	}
	
	void padrãosetConfigs(){
		
		Padrão.getEngine().setDigitsShown(info.getDigitsShown());
		
		Padrão.getEngine().setDigitsCalculated(info.getDigitsCalculated());
		
	}
	
	// Getters and Setters
	
	public String getSelected() {
		return selected;
	}
	

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public Padrão getPadrão(){
		return Padrão;
	}

	public Estatística getEstatística(){
		return Estatística;
	}

	public Information getInformation(){
		return info;
	}
}