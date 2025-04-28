public class Horse {
    // Basic horse info
    private String name;
    private String symbol;
    private double confidence;
    
    // Racing attributes
    private int distance;
    private boolean fallen;
    
    // Betting attributes
    private double odds;
    private int betsPlaced;
    private double totalBetAmount;
    
    // Customization attributes
    private String breed;
    private String coatColor;
    private String saddle;
    private String horseshoes;
    private String accessories;
    private double speed;
    private double stamina;
    
    // Stats 
    private HorseStats stats;

    public Horse(String name, String symbol, String breed, String coatColor, 
                String saddle, String horseshoes, String accessories) {
        this.name = name;
        this.symbol = symbol;
        confidence = Math.random();
        this.breed = breed;
        this.coatColor = coatColor;
        this.saddle = saddle;
        this.horseshoes = horseshoes;
        this.accessories = accessories;
        this.distance = 0;
        this.fallen = false;
        this.odds = 1.0;
        this.betsPlaced = 0;
        this.totalBetAmount = 0.0;
        this.stats = new HorseStats(name);
        calculateAttributes();
    }

    private void calculateAttributes() {
        // Calculate speed, stamina based on breed and accessories
        switch (this.breed) {
            case "Thoroughbred":
                speed = 9.5;
                stamina = 8.5;
                confidence += 0.1;
                break;
            case "Arabian":
                speed = 8.0;
                stamina = 9.0;
                confidence += 0.15;
                break;
            case "Quarter Horse":
                speed = 8.5;
                stamina = 7.5;
                confidence += 0.05;
                break;
            default:
                speed = 7.0;
                stamina = 7.0;
        }

        if (this.horseshoes.equals("Lightweight")) {
            speed += 0.5;
        }
        if (this.saddle.equals("Comfortable")) {
            stamina += 0.5;
        }
        if (this.accessories.equals("Bridle")) {
            confidence += 0.2;
        }

        confidence = Math.max(0.0, Math.min(1.0, confidence));

    }

        // Getter methods
    public String getBreed() {
        return breed;
    }

    public String getCoatColor() {
        return coatColor;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSaddle() {
        return saddle;
    }

    public String getHorseshoes() {
        return horseshoes;
    }

    public String getAccessories() {
        return accessories;
    }

    public double getSpeed() {
        return speed;
    }

    public double getStamina() {
        return stamina;
    }

    public double getConfidence() {
        return confidence;
    }

    //Other methods of class Horse
    public void fall()
    {
 	fallen = true;   
    }
    
    public int getDistanceTravelled()
    {
 	return distance;       
    }
    
    public String getName()
    {
 	return name;       
    }
    
    public void goBackToStart()
    {
	distance = 0; 
	fallen = false;       
    }
    
    public boolean hasFallen()
    {
 	return fallen;       
    }

    public void moveForward()
    {
    	distance += 1; 
        this.stamina = Math.max(stamina - 0.1, 0.5);
    }

    public void rest() {
        // Recover stamina between races
        this.stamina = Math.min(stamina + 2.0, 10.0); 
    }

    public void setConfidence(double newConfidence)
    {
     	if(newConfidence>0 && newConfidence<1 ){
		confidence = newConfidence;  
	}
	else{
		System.out.println("The confidence rating must be between 0 and 1");      
    	}
    }

        public void placeBet(double amount) {
        this.betsPlaced++;
        this.totalBetAmount += amount;
        updateOdds();
    }
    
    private void updateOdds() {
        // Adjust odds based on betting patterns
        if (betsPlaced > 5) {
            odds *= 1.05; // Increase odds if heavily bet
        }
    }
    
    public double getOdds() {
        return odds;
    }
    
    public void setOdds(double odds) {
        this.odds = odds;
    }
    
    public int getBetsPlaced() {
        return betsPlaced;
    }
    
    public double getTotalBetAmount() {
        return totalBetAmount;
    }
}