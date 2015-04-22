package calculadoras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import calculadoras.engine.EstatísticaEngine;
import calculadoras.gui.EstatísticaUI;
import frames.ExtraWindow;

public class Estatística{

	public ExtraWindow window;
	
	private EstatísticaEngine engine;
	private EstatísticaUI gui;
	
	public Estatística(){
		
		window = new ExtraWindow() {
			public void addToList() {
				engine.addElement(numberToAdd);
			}
		};
		
			gui = new EstatísticaUI(window.addButton());
			
			engine = new EstatísticaEngine(gui);
	
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
	
	public EstatísticaEngine getEngine(){
		return engine;
	}
	
	public EstatísticaUI getGUI(){
		return gui;
	}
	
}