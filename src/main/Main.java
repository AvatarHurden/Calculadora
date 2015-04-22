package main;
import calculadoras.gui.UnitConverterUI;
import frames.FrameCalculadora;

public class Main {
	
	private static FrameCalculadora frame;
	private static UnitConverterUI test;
	
	public static void main (String args[]) {
		
		frame = new FrameCalculadora();
		frame.pack();
		frame.setTitle("Calculadora");
		
	}

	public static FrameCalculadora getFrame() {
		return frame;
	}

	public static void setFrame(FrameCalculadora frame) {
		Main.frame = frame;
	}
	
}