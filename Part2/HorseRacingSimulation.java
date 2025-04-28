import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class HorseRacingSimulation {
    private JFrame mainFrame;
    private Race race;
    private Track currentTrack;
    private List<Horse> horses;
    private BettingUI bettingUI;
    private HorseCustomizationPanel horseCustomizer;
    //private StatsPanel statsPanel;
    
    public Track getCurrentTrack(){
        return currentTrack;
    }
    
    public HorseRacingSimulation() {
        horses = new ArrayList<>();
        initializeUI();
    }
    

    private void initializeUI() {
        // First show track designer
        showTrackDesigner();
    }
    
    private void showTrackDesigner() {
        JFrame trackDesignFrame = new JFrame("Design Your Track");
        TrackDesigner trackDesigner = new TrackDesigner();
        trackDesignFrame.add(trackDesigner);
        trackDesignFrame.setSize(500, 400);
        trackDesignFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        trackDesigner.addTrackCreationListener(e -> {
            currentTrack = trackDesigner.getCreatedTrack();
            trackDesignFrame.dispose();
            showMainRacingPanel();
        });
        
        trackDesignFrame.setVisible(true);
    }
    
    private void showMainRacingPanel() {
        mainFrame = new JFrame("Horse Racing Simulation");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Title
        JLabel titleLabel = new JLabel("Horse Race", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainFrame.add(titleLabel, BorderLayout.NORTH);
        
        // Center panel for race display
        JPanel centerPanel = new JPanel(new BorderLayout());
        mainFrame.add(centerPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        // Add Horse button
        JButton addHorseButton = new JButton("Add Horse");
        addHorseButton.addActionListener(e -> showHorseCustomizer());
        buttonPanel.add(addHorseButton);
        
        // Place Bet button
        JButton betButton = new JButton("Place Bet");
        betButton.addActionListener(e -> showBettingUI());
        buttonPanel.add(betButton);
        
        //Check Stats button
        JButton statsButton = new JButton("Check Stats");
        statsButton.addActionListener(e -> showStats());
        buttonPanel.add(statsButton);
        
        // Start Race button
        JButton raceButton = new JButton("Start Race");
        raceButton.addActionListener(e -> startRace());
        buttonPanel.add(raceButton);
        
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    private void showHorseCustomizer() {

        if (horses.size() >= currentTrack.getLanes()) {
            JOptionPane.showMessageDialog(mainFrame, 
                "Cannot add more horses! Track only has " + 
                currentTrack.getLanes() + " lanes.");
            return;
        }

        JFrame customizerFrame = new JFrame("Customize Horse");
        horseCustomizer = new HorseCustomizationPanel();
        customizerFrame.add(horseCustomizer, BorderLayout.CENTER);
        
        JButton confirmButton = new JButton("Create Horse");
        confirmButton.addActionListener(e -> {
            if (horseCustomizer.getHorseName().isEmpty()) {
                JOptionPane.showMessageDialog(customizerFrame, "Please enter a horse name!");
                return;
            }
            createHorse();
            customizerFrame.dispose();
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        customizerFrame.add(buttonPanel, BorderLayout.SOUTH);
        
        customizerFrame.pack();
        customizerFrame.setLocationRelativeTo(null); // Center on screen
        customizerFrame.setVisible(true);
    }

    private void createHorse() {

        if (horses.size() >= currentTrack.getLanes()) {
            JOptionPane.showMessageDialog(mainFrame,
                "Maximum number of horses (" + currentTrack.getLanes() + 
                ") reached for this track!");
            return;
        }

        String name = horseCustomizer.getHorseName();
        String breed = (String) horseCustomizer.getBreedComboBox().getSelectedItem();
        String color = (String) horseCustomizer.getColorComboBox().getSelectedItem();
        String symbol = (String) horseCustomizer.getSymbolComboBox().getSelectedItem();
        String saddle = (String) horseCustomizer.getSaddleComboBox().getSelectedItem();
        String horseshoes = (String) horseCustomizer.getHorseshoesComboBox().getSelectedItem();
        String accessories = (String) horseCustomizer.getAccessoriesComboBox().getSelectedItem();
        
        Horse newHorse = new Horse(name, symbol, breed, color, saddle, horseshoes,accessories);
        
        horses.add(newHorse);
    
        JOptionPane.showMessageDialog(mainFrame, "Horse " + name + " created!");
    
    }

    public void returnToMainFrame() {
        mainFrame.setVisible(true);  // Show the main racing panel again
    }
    
    private void showBettingUI() {
        if (horses.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Please add horses first!");
            return;
        }
        
        bettingUI = new BettingUI(horses, this);
        JFrame bettingFrame = new JFrame("Betting");
        bettingFrame.add(bettingUI);
        bettingFrame.setSize(600, 500);
        bettingFrame.pack();
        bettingFrame.setVisible(true);

        // Close button to close the betting frame
        JButton closeBettingButton = new JButton("Close Betting");
        closeBettingButton.addActionListener(e -> bettingFrame.dispose()); // Close the betting frame
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeBettingButton);
        bettingFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        bettingFrame.setVisible(true);
    }
    
    private void showStats() {
        if (horses.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No horses to show stats for!");
            return;
        }
        
        StringBuilder statsText = new StringBuilder();
        for (Horse horse : horses) {
            statsText.append(horse.getName()).append(":\n");
            statsText.append("Speed: ").append(horse.getSpeed()).append("\n");
            statsText.append("Stamina: ").append(horse.getStamina()).append("\n");
            statsText.append("Confidence: ").append(horse.getConfidence()).append("\n\n");
        }
        
        JTextArea statsArea = new JTextArea(statsText.toString());
        statsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statsArea);
        
        JFrame statsFrame = new JFrame("Horse Statistics");
        statsFrame.add(scrollPane);
        statsFrame.setSize(400, 300);
        statsFrame.setVisible(true);
    }
    
    private void startRace() {
        if (horses.size() < 2) {
            JOptionPane.showMessageDialog(mainFrame, "You need at least 2 horses to race!");
            return;
        }
        
        race = new Race(currentTrack);
        for (int i = 0; i < Math.min(horses.size(), currentTrack.getLanes()); i++) {
            race.addHorse(horses.get(i), i+1);
        }
        
        race.startRace();

        if (bettingUI != null) {
        bettingUI.updateBettingHistory();  // Update the betting history display
    }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HorseRacingSimulation());
    }
}