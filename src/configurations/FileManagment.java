package configurations;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import main.Main;

import calculadoras.Estat�stica;
import calculadoras.Padr�o;
import frames.FrameCalculadora;

public class FileManagment {

	static Padr�o Padr�o;
	static Estat�stica Estat�stica;
	
	public static final String open = "open";
	public static final String save = "save";
	
	static JFileChooser fc = new JFileChooser();
	
	
	// Methods referring to Calculadora Padr�o
	
	public static void padr�o(String option, Padr�o calc){
		
		Padr�o = calc;
		
		fc.setCurrentDirectory(new File(".\\Saves\\Padr�o"));
		
		fc.setFileFilter(new FileFilter() {
	        public boolean accept(File f) {
	          return f.getName().toLowerCase().endsWith(".cpd")||
	        	 f.isDirectory();
	        }

	        public String getDescription() {
	          return "CPD Files";
	        }
	      });
		
		if(option.equals(save))
			padr�oSave();
		if(option.equals(open))
			padr�oOpen();
	}
		
	public static void padr�oSave(){
		
		System.out.println("save");
		
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			
			System.out.println("approve");
			
			String selFile = fc.getSelectedFile().toString();
			
			if (!selFile.toString().contains(".cpd"))  selFile = selFile+".cpd";
				try{
					Padr�o.save(selFile);
				  }catch (Exception e){
					 e.printStackTrace();
				  }
		} else {
			System.out.println("error");
		}
		
	}
	
	public static void padr�oOpen(){
		
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File selFile = fc.getSelectedFile();
		
			try {
				Padr�o.open(selFile);
			} catch (IOException e) {}
		}
	}
	
	
	// Methods referring to Calculadora Estat�stica
	
	public static void estat�stica(String option, Estat�stica calc){
		
		Estat�stica = calc;
		
		fc.setCurrentDirectory(new File(".\\Saves\\Estat�stica"));
		
		fc.setFileFilter(new FileFilter() {
	        public boolean accept(File f) {
	          return f.getName().toLowerCase().endsWith(".ces")||
	        	 f.isDirectory();
	        }

	        public String getDescription() {
	          return "CES Files";
	        }
	      });
		
		if(option.equals(save))
			estat�sticaSave();
		if(option.equals(open))
			estat�sticaOpen();
		
	}
	
	public static void estat�sticaSave(){
		
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			
			System.out.println("ok");
			String selFile = fc.getSelectedFile().toString();
			
			if (!selFile.toString().contains(".ces"))  selFile = selFile.toString()+".ces";
				try{
					Estat�stica.save(selFile);
				  }catch (Exception e){
					  System.out.println("error");
				  }
		} else {
			System.out.println("error");
		}
		
	}
	
	public static void estat�sticaOpen(){
		
		fc.showOpenDialog(null);
		File selFile = fc.getSelectedFile();
		
		try {
			Estat�stica.open(selFile);
		} catch (IOException e) {}
		
	}
	
	public static void setCalculadoras(Estat�stica e, Padr�o p){
		
		Estat�stica = e;
		Padr�o = p;
		
	}
	
	public static void createFolders(){
		
		if ((new File(".\\Saves")).isDirectory()){
			
			if (!(new File(".\\Saves\\Padr�o")).isDirectory())
				new File(".\\Saves\\Padr�o").mkdir();
			if (!(new File(".\\Saves\\Estat�stica")).isDirectory())
				new File(".\\Saves\\Estat�stica").mkdir();
		}
			else {
				new File(".\\Saves").mkdir();
				new File(".\\Saves\\Padr�o").mkdir();
				new File(".\\Saves\\Estat�stica").mkdir();
			}
		
	}
	
}
