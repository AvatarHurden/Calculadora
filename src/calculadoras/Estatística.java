package calculadoras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import calculadoras.engine.Estat�sticaEngine;
import calculadoras.gui.Estat�sticaUI;
import frames.ExtraWindow;

public class Estat�stica{

	public ExtraWindow window;
	
	private Estat�sticaEngine engine;
	private Estat�sticaUI gui;
	
	public Estat�stica(){
		
		window = new ExtraWindow() {
			public void addToList() {
				engine.addElement(numberToAdd);
			}
		};
		
			gui = new Estat�sticaUI(window.addButton());
			
			engine = new Estat�sticaEngine(gui);
	
	}
	
	// Methods refering to saving and opening files
	
	public void save(String file) throws IOException{
		
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for (BigDecimal i : engine.getElementos()){
			out.write(i.toString());
			out.newLine();
		}
		out.close();
		
	}
	
	public void open(File file) throws IOException{
		
		engine.getElementos().clear();
		
		BufferedReader in = new BufferedReader(new FileReader(file));
	   
		String str;
	    while ((str = in.readLine()) != null) {
	        engine.addElement(new BigDecimal(str));
	    }
	    in.close();
		
	}
	
	
	// Visibility "getter" and "setter"
	
	public void setVisible(boolean visible){
		
		gui.setVisible(visible);
		
		try{
			if (!visible && window.isVisible()) window.toggle();
		} catch (NullPointerException e){}
		
	}
	
	public boolean isVisible(){
		return gui.isVisible();
	}
	
	
	// Getters
	
	public Estat�sticaEngine getEngine(){
		return engine;
	}
	
	public Estat�sticaUI getGUI(){
		return gui;
	}
	
}