# Horse Racing Simulation 🐎

This project is a horse racing betting simulation built with Java and Swing.
Disclaimer not all the parts were completed but all were attempted.

## Features
- Design custom tracks (number of lanes, shape, length, and conditions)
- Create horses and customize the attributes.
- Stats panel available to show the stats. This is a very simple stats panel because I was having trouble implementing my stats section into the main class. The more complex stats Section is found in the StatsPane, TrackStats,HorseStats.
- Place bets on horses - however a sample race was used for this because I couldn't integrate the stats which affected my ability to integrate the dynamic betting. This is because BettingOddsCalculator is dependent on the horse and track stats. 
- Run the race and see live updates

## How to Run
Part1
1. Compile all the classes in part 1 (`javac *.java`) or copy past in an IDE.
2. If working in terminal run the RaceTest class
3. If working in IDE call the main method of the RaceTest class

Part2
1. Compile the project (`javac *.java`) in the terminal make sure to compile all the classes.
2. Launch the main class HorseRacingSimulation (javac HorseRacingSimulation)
3. The Track design will pop up. Fill out the panels.
4. There are multiple buttons at the bottom: press the add horses button to add the horses to the race and choose their attributes. use the place bet button to get the betting interface, close when done. Once ready press start race and the race will appear in the terminal.

## Requirements
- Java 8 or higher

## Author
Your Name Here
