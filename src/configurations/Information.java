package configurations;

public class Information{
	

	//group General Information
	
	// Position of Calculator on screen
	int xPosition;
	public int getxPosition() {
		return xPosition;
		}
	
	int yPosition;
	public int getyPosition() {
		return yPosition;
		}
	
	// Starting tab (padr�o, estat�stica)
	String calculator;
	public String getCalculator() {
		return calculator;
		}
	
	//endgroup General Information
	
	
	//group Padr�o Information
	
	// Number of digits shown on padr�o
	int digitsShown;
	public int getDigitsShown() {
		return digitsShown;
		}
	
	public void setDigitsShown(int i) {
		digitsShown = i;
		}
	
	// Number of decimal digits calculated on padr�o
	int digitsCalculated;
	public int getDigitsCalculated() {
		return digitsCalculated;
		}
	
	public void setDigitsCalculated(int i) {
		digitsCalculated = i;
		}
	
	//endgroup Padr�o Information

	
	//group Estat�stica Information
	
	
	
	//endgroup Estat�stica Information

	
	public void setDefault(){
		
		calculator = "padr�o";
		xPosition = 0;
		yPosition = 0;
		
		digitsShown = 18;
		digitsCalculated = 30;
	}
	
}