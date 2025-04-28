import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

public class Race {
    private int raceLength;
    private List<Horse> horsesInRace; 
    private Track track;

    public Race(Track track) {
        this.track = track;
        this.raceLength = track.getLength();
        this.horsesInRace = new ArrayList<>(track.getLanes());
    }

    public void addHorse(Horse horse, int laneNumber) {
        if (laneNumber < 1 || laneNumber > track.getLanes()) {
            System.out.println("Invalid lane: " + laneNumber + ". Track has " + track.getLanes() + " lanes.");
            return;
        }
        // Ensure we have enough slots (e.g., for lane 3 in a 2-lane track)
        while (horsesInRace.size() < laneNumber) {
            horsesInRace.add(null);
        }
        horsesInRace.set(laneNumber - 1, horse); // Lanes are 1-indexed
    }

    public void startRace() {
        // Reset all horses
        horsesInRace.forEach(horse -> {
            if (horse != null) horse.goBackToStart();
        });

        boolean finished = false;
        Horse winner = null;
        int fallenHorses = 0;

        while (!finished) {
            fallenHorses = 0; // Reset fallen count each iteration

            // Move all horses
            for (Horse horse : horsesInRace) {
                if (horse == null) {
                    fallenHorses++;
                    continue;
                }
                
                if (horse.hasFallen()) {
                    fallenHorses++;
                } else {
                    moveHorse(horse);
                    if (raceWonBy(horse)) {
                        winner = horse;
                        finished = true;
                    }
                }
            }

            printRace();

            if (fallenHorses >= horsesInRace.size()) {
                System.out.println("All horses have fallen! Race ended with no winner.");
                return;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (winner != null) {
            System.out.println("Winner: " + winner.getName());
            //winner.getStats().updateRaceStats(/*...*/); // Update stats
        }
    }
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse horse) {
        if (horse == null || horse.hasFallen()) {
            return;
        }
    
        // Calculate combined move probability (confidence + speed bonus)
        double baseMoveProbability = horse.getConfidence();
        double speedBonus = horse.getSpeed() * 0.05; // 5% per speed point
        double totalMoveProbability = Math.min(baseMoveProbability + speedBonus, 0.95); // Cap at 95%
    
        // Stamina affects consistency (higher stamina = less fall chance)
        double baseFallProbability = 0.1;
        double staminaReduction = horse.getStamina() * 0.03; // 3% reduction per stamina point
        double totalFallProbability = Math.max(
            baseFallProbability * horse.getConfidence() - staminaReduction, 
            0.01 // Minimum 1% fall chance
        );
    
        // Attempt move
        if (Math.random() < totalMoveProbability) {
            horse.moveForward();
            
            // Extra movement chance for high-speed horses
            if (horse.getSpeed() > 8.0 && Math.random() < 0.3) {
                horse.moveForward(); // Double step!
            }
        }
    
        // Check for fall
        if (Math.random() < totalFallProbability) {
            horse.fall();
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {

	//terminate method if theHorse is null
	if(theHorse == null){
		return false;
	}

        if (theHorse.getDistanceTravelled() >= raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print('\u000C');  // Clear the terminal
        
        // Top edge of track (width based on raceLength)
        multiplePrint('=', raceLength + 3); 
        System.out.println();
        
        // Print all active lanes dynamically
        for (Horse horse : horsesInRace) {
            printLane(horse);
            System.out.println();
        }
        
        // Bottom edge of track
        multiplePrint('=', raceLength + 3);
        System.out.println();
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse horse) {
        // Calculate spaces
        int spacesBefore = (horse != null) ? horse.getDistanceTravelled() : 0;
        int spacesAfter = raceLength - spacesBefore;
        
        // Lane left boundary
        System.out.print('|');
        
        // Spaces before horse
        multiplePrint(' ', spacesBefore);
        
        // Horse symbol (or empty)
        if (horse == null) {
            System.out.print(' '); // Empty lane
        } else if (horse.hasFallen()) {
            System.out.print('\u2322'); // Fallen symbol
        } else {
            System.out.print(horse.getSymbol());
        }
        
        // Spaces after horse
        multiplePrint(' ', spacesAfter);
        
        // Lane right boundary and horse info
        System.out.print('|');
        
        // Display horse name if exists
        if (horse != null && !horse.hasFallen()) {
            System.out.print(" " + horse.getName() + 
                           " (Confidence: " + String.format("%.1f", horse.getConfidence()) + ")");
        }
    	else if(horse.hasFallen()){
    	     System.out.print("(Fallen)");
    	}
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}
