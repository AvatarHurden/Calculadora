package calculadoras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import calculadoras.engine.PadrãoEngine;
import calculadoras.gui.PadrãoUI;
import configurations.Information;

public class Padrão {
	
	private PadrãoEngine engine;
	private PadrãoUI gui;
	
	public Padrão(boolean withMemory) {
	
		gui = new PadrãoUI(withMemory);
		
		engine = new PadrãoEngine(gui, withMemory);
		
		
	}
	
	// Functions to edit gui, since Padrão is no longer a JPanel
	
	public void setVisible(boolean visible){
		gui.setVisible(visible);
	}
	
	public void requestFocus(){
		gui.requestFocus();
	}
	
	public boolean isVisible(){
		return gui.isVisible();
	}
	
	
	// Methods refering to saving and opening files
	
	public void save(String file) throws IOException{
		
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		
		
		out.write(engine.getMemory().toString());
		out.close();
		
		System.out.println("tried to save");
	}
	
	public void open(File file) throws IOException{
		
		BufferedReader in = new BufferedReader(new FileReader(file));
	    String str;
	    while ((str = in.readLine()) != null) {
	      engine.setMemory(new BigDecimal(str));
	    }
	    in.close();
		
	}
	
	
	// Getters and Setters
	
	public PadrãoEngine getEngine() {
		return engine;
	}
	
	public PadrãoUI getGui(){
		return gui;
	}
}