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
	
	// Starting tab (padrão, estatística)
	String calculator;
	public String getCalculator() {
		return calculator;
		}
	
	//endgroup General Information
	
	
	//group Padrão Information
	
	// Number of digits shown on padrão
	int digitsShown;
	public int getDigitsShown() {
		return digitsShown;
		}
	
	public void setDigitsShown(int i) {
		digitsShown = i;
		}
	
	// Number of decimal digits calculated on padrão
	int digitsCalculated;
	public int getDigitsCalculated() {
		return digitsCalculated;
		}
	
	public void setDigitsCalculated(int i) {
		digitsCalculated = i;
		}
	
	//endgroup Padrão Information

	
	//group Estatística Information
	
	
	
	//endgroup Estatística Information

	
	public void setDefault(){
		
		calculator = "padrão";
		xPosition = 0;
		yPosition = 0;
		
		digitsShown = 18;
		digitsCalculated = 30;
	}
	
}