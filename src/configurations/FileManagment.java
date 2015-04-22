package configurations;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import main.Main;

import calculadoras.Estatística;
import calculadoras.Padrão;
import frames.FrameCalculadora;

public class FileManagment {

	static Padrão Padrão;
	static Estatística Estatística;
	
	public static final String open = "open";
	public static final String save = "save";
	
	static JFileChooser fc = new JFileChooser();
	
	
	// Methods referring to Calculadora Padrão
	
	public static void padrão(String option, Padrão calc){
		
		Padrão = calc;
		
		fc.setCurrentDirectory(new File(".\\Saves\\Padrão"));
		
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
			padrãoSave();
		if(option.equals(open))
			padrãoOpen();
	}
		
	public static void padrãoSave(){
		
		System.out.println("save");
		
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			
			System.out.println("approve");
			
			String selFile = fc.getSelectedFile().toString();
			
			if (!selFile.toString().contains(".cpd"))  selFile = selFile+".cpd";
				try{
					Padrão.save(selFile);
				  }catch (Exception e){
					 e.printStackTrace();
				  }
		} else {
			System.out.println("error");
		}
		
	}
	
	public static void padrãoOpen(){
		
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File selFile = fc.getSelectedFile();
		
			try {
				Padrão.open(selFile);
			} catch (IOException e) {}
		}
	}
	
	
	// Methods referring to Calculadora Estatística
	
	public static void estatística(String option, Estatística calc){
		
		Estatística = calc;
		
		fc.setCurrentDirectory(new File(".\\Saves\\Estatística"));
		
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
			estatísticaSave();
		if(option.equals(open))
			estatísticaOpen();
		
	}
	
	public static void estatísticaSave(){
		
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			
			System.out.println("ok");
			String selFile = fc.getSelectedFile().toString();
			
			if (!selFile.toString().contains(".ces"))  selFile = selFile.toString()+".ces";
				try{
					Estatística.save(selFile);
				  }catch (Exception e){
					  System.out.println("error");
				  }
		} else {
			System.out.println("error");
		}
		
	}
	
	public static void estatísticaOpen(){
		
		fc.showOpenDialog(null);
		File selFile = fc.getSelectedFile();
		
		try {
			Estatística.open(selFile);
		} catch (IOException e) {}
		
	}
	
	public static void setCalculadoras(Estatística e, Padrão p){
		
		Estatística = e;
		Padrão = p;
		
	}
	
	public static void createFolders(){
		
		if ((new File(".\\Saves")).isDirectory()){
			
			if (!(new File(".\\Saves\\Padrão")).isDirectory())
				new File(".\\Saves\\Padrão").mkdir();
			if (!(new File(".\\Saves\\Estatística")).isDirectory())
				new File(".\\Saves\\Estatística").mkdir();
		}
			else {
				new File(".\\Saves").mkdir();
				new File(".\\Saves\\Padrão").mkdir();
				new File(".\\Saves\\Estatística").mkdir();
			}
		
	}
	
}
