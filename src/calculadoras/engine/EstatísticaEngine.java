package calculadoras.engine;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import operations.BigOperation;
import calculadoras.gui.EstatísticaUI;
import calculadoras.gui.EstatísticaUI.ElementPanel;
import calculadoras.gui.EstatísticaUI.FunctionPanel;

public class EstatísticaEngine {

	EstatísticaUI gui;
	
	Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	// Lista dos elementos
	List<BigDecimal> elementos = new ArrayList<BigDecimal>();

	// Propriedades do conjunto
	List<String> values = new ArrayList<String>();
	BigDecimal soma, média, mediano, variância, desvio;
	
	
	public EstatísticaEngine(EstatísticaUI gui){
		
		this.gui = gui;
		
		addListeners();
	}
	
	
	// Method called once to add the listeners to all items create in GUI
	
	public void addListeners(){
		
		for (FunctionPanel i : gui.functions){
			
			i.getButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					for (FunctionPanel i : gui.functions){
						if (e.getSource() == i.getButton()) 
							clip.setContents(new StringSelection(i.getResult()), null);
					}
					
				}
			});
			
		}
		
		gui.getCalculate().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Scanner scan = new Scanner(gui.getInput());
				String number = scan.next();
				
				do {
					if (number.contains(",")) number = number.replace(",",".");
						addElement(new BigDecimal(number));
					try{
						number = scan.next();
					}catch (Exception e) {number = "erro fatal, mano";}
					} while (!number.equals("erro fatal, mano"));
				
				gui.setInput("");
				
			}
		});
	
		gui.getDeleteAll().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					
				elementos.removeAll(elementos);
				
				gui.removeAllElements();
				
				gui.revalidateScroller();
				
				calculate();
				
			}
		});
		
		gui.getSort().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				Collections.sort(elementos);
		
				int i = 0;
				
				for (BigDecimal e : elementos){
					gui.elements.get(i).setText(BigOperation.format(e, 14).toString());
					i++;
				}
				
			
				
				if (elementos.size()%2 == 0){
					gui.setMedianPanelColor(gui.elements.get(elementos.size()/2),
							gui.elements.get(elementos.size()/2-1));
				} else { 
					gui.setMedianPanelColor(gui.elements.get(elementos.size()/2));
				}
			}
		});
	}

	
	// Method to add the strings to the list and calling the GUI to display them in the right order
	
	public void calculate(){
		
		values.clear();
		
		values.add(soma());
		values.add(média());
		values.add(mediano());
		values.add(moda());
		values.add(variância());
		values.add(desvio());
		
		for (int i = 0; i< values.size();i++){
			
			gui.setFunctionValue(i,values.get(i));
			
		}
	}
	
	
	// Method to return formated String of each operation on the set of numbers
	
	public String soma(){
		
		soma = BigDecimal.ZERO;
		
		for (BigDecimal i : elementos){
			soma = soma.add(i);
		}
		
		if (elementos.size() == 0){
			return "";
		} else return BigOperation.format(soma,14).toString();
	}
	
	public String média(){
		
		if (elementos.size() == 0) return "";
		else {
			média = soma.divide(new BigDecimal(String.valueOf(elementos.size())),30,BigDecimal.ROUND_HALF_EVEN);
			return BigOperation.format(média,14).toString();
		}
	}
	
	public String mediano(){
		
		List<BigDecimal> tempArray = new ArrayList<BigDecimal>();
		
		for (BigDecimal i : elementos){
			tempArray.add(i);
		}
		
		Collections.sort(tempArray);
		
		int arraySize = tempArray.size();
		
		if (elementos.size()%2 == 0){
			try {
				mediano = new BigDecimal(String.valueOf((tempArray.get((arraySize/2)-1).add( 
											tempArray.get(arraySize/2))).divide(new BigDecimal("2"))));
			} catch (ArrayIndexOutOfBoundsException e){
				return "";
			}
		} else mediano = tempArray.get(tempArray.size()/2);
		
		return BigOperation.format(mediano,14).toString();
	}
	
	public String moda(){

		class Repetition{
			int repetition;
			BigDecimal value;
			
			public Repetition(int rep, BigDecimal val){
				repetition = rep;
				value = val;
			}
		}
		
		List<Repetition> tempArray = new ArrayList<Repetition>();
		String s = "";
		int i = -1;
		int maxRep = 0;
		int numMode = 0;
		boolean modeExist = false;
		
		for (BigDecimal element : elementos){
			for (BigDecimal match : elementos){
				if (element.compareTo(match) == 0){
					i++;
				}
			}
			tempArray.add(new Repetition(i,element));
			i = -1;
		}
		
		tempArray.add(new Repetition(0,BigDecimal.ZERO));
		
		for (Repetition e : tempArray){
			if (maxRep != e.repetition) modeExist = true;
			maxRep = Math.max(e.repetition, maxRep);
		}
		
		
		
		for (Repetition e : tempArray){
			if (e.repetition < maxRep){
				e.repetition = 0;
			}
		}
		
		for (Repetition e : tempArray){
			if (e.repetition==0 || s.contains(BigOperation.format(e.value,14).toString())){
			} else {
				if (s.length()>0){
					s= s+", "+(BigOperation.format(e.value,14).toString());
				} else s= s+(BigOperation.format(e.value,14).toString());
				
				numMode++;
			}
		}
		
		if (numMode>3 || modeExist == false){
			if (elementos.size() == 0){
				s = "";
			} else s = "No mode";
		}
		
		return s;
	}
	
	public String variância(){
		
		if (elementos.size() == 0) return "";
		
		variância = BigDecimal.ZERO;
		
		for (BigDecimal i : elementos){
			variância = variância.add(new BigDecimal(String.valueOf(Math.pow(
														i.subtract(média).doubleValue(),2))));
		}
		
		variância = variância.divide(new BigDecimal(String.valueOf(
								elementos.size())),30,BigDecimal.ROUND_HALF_EVEN);
		
		return BigOperation.format(variância,14).toString();
	}
	
	public String desvio(){
		
		desvio = BigOperation.sqrt(variância, 30);
		
		if (elementos.size() == 0){
			return "";
		} else return BigOperation.format(desvio,14).toString();
	}
	

	// Methods called each time a element is added, adding it to the list of numbers and putting the
	// necessary listeners to the corresponding panel
	
	public void addElement(BigDecimal element){
		
		elementos.add(element);
		
		addDeleteHover(gui.addElement(element));
		
        calculate();
	}

	public void addDeleteHover(ElementPanel i){
		
		// Listener to delete individual items from list
		class delete implements ActionListener{  
			public void actionPerformed(ActionEvent e){
				
				for (ElementPanel panel : gui.elements){
					if (e.getSource() == panel.getDelete()){
						
						// Remove number from the list of numbers
						elementos.remove(elementos.get(gui.elements.indexOf(panel)));
						
						// Remove panel with number from display
						gui.removeElement(panel);
						
						gui.revalidateScroller();
						
						calculate();
						break;
					}
				}
			}
		}
				
		// Listener to show or hide button based on location of mouse
		class panelHover implements MouseListener{
			
			public void mouseClicked(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {
				for(int i = 0;i<elementos.size();i++){
					if (gui.elements.get(i).isMouseOver(e.getSource())) {
						gui.elements.get(i).showDeleteButton(true);
						
						break;
					}
				}
			}
			
			public void mouseExited(MouseEvent e) {
				for(int i = 0;i<elementos.size();i++){
					if (gui.elements.get(i).isMouseOver(e.getSource())) {
						gui.elements.get(i).showDeleteButton(false);
						break;
					}
				}
			}

			public void mousePressed(MouseEvent e) {}

			public void mouseReleased(MouseEvent arg0) {}
		}
		
		i.addMouseListener(new panelHover());
		i.getDelete().addMouseListener(new panelHover());
		i.getEmptyLabel().addMouseListener(new panelHover());
		i.getDelete().addActionListener(new delete());
	}
	

	
	public EstatísticaUI getGUI(){
		return gui;
	}
	
	public List<BigDecimal> getElementos(){
		return elementos;
	}
	
}
