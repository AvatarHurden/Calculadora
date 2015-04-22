package configurations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import frames.FrameCalculadora;

public class StartUp {
	
	public static void saveOnClose(FrameCalculadora frame){

		try {
			BufferedWriter write = new BufferedWriter(new FileWriter("config"));
			
			write.write(frame.getSelected()); // Grava qual � mostrado
			write.newLine();
			write.write(String.valueOf(frame.getX())); // Grava a posi��o x
			write.newLine();
			write.write(String.valueOf(frame.getY())); // Grava a posi��o y
			
			// Configura��es Gerais
			
			// Configura��es da Padr�o
			write.newLine();						// Digits Displayed
			write.write(String.valueOf(frame.getInformation().getDigitsShown()));
			
			write.newLine();                        // Digits Calculated
			write.write(String.valueOf(frame.getInformation().getDigitsCalculated()));
			
			// Configura��es da Estat�stica
			
			write.close();
		} catch (IOException e) {}
	}		
	
	// Method to read the information from the config file, setting "Information" to the adequate values
			
	public static Information read(Information info){
		
		try {
			BufferedReader read = new BufferedReader(new FileReader("config"));
			
			info.calculator = read.readLine();   // aba que ser� aberta inicialmente
			info.xPosition = Integer.parseInt(read.readLine());  // Posi��o x do frame
			info.yPosition = Integer.parseInt(read.readLine());  // Posi��o y do frame
			
			// Configura��es Gerais
			
			// Configura��es da Padr�o
			
			info.digitsShown = Integer.parseInt(read.readLine()); // Numbers of digits shown on padr�o 
			info.digitsCalculated = Integer.parseInt(read.readLine());
			
			read.close();
			
			return info;
			
		} catch (Exception e){
			
			// In case of no information, use these as defaults (also these settings on "restore default")
			
		    info.setDefault();
			
			return info;
		}
	}
	
}
