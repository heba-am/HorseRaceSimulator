
/**
 * Write a description of class Horse here.
 * 
 * @author (Heba) 
 * @version (1)
 */
public class Horse
{
    //Fields of class Horse
    private String horseName;
    private char horseSymbol;
    private int distance;
    private boolean fall;	
    private double horseConfidence;
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
	this.horseName = horseName;
	this.horseSymbol = horseSymbol;
	setConfidence(horseConfidence);
    }
    
    //Other methods of class Horse
    public void fall()
    {
 	fall = true;   
    }
    
    public double getConfidence()
    {
 	return horseConfidence;       
    }
    
    public int getDistanceTravelled()
    {
 	return distance;       
    }
    
    public String getName()
    {
 	return horseName;       
    }
    
    public char getSymbol()
    {
 	return horseSymbol;       
    }
    
    public void goBackToStart()
    {
	distance = 0; 
	fall = false;       
    }
    
    public boolean hasFallen()
    {
 	return fall;       
    }

    public void moveForward()
    {
	distance += 1;        
    }

    public void setConfidence(double newConfidence)
    {
     	if(newConfidence>0 && newConfidence<1 ){
		horseConfidence = newConfidence;  
	}
	else{
		System.out.println("The confidence rating must be between 0 and 1");      
    	}
    }
    
    public void setSymbol(char newSymbol)
    {
	horseSymbol = newSymbol;        
    }
    
}
