import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BettingUI extends JPanel{

    private JFrame frame;
    private JComboBox<String> horseComboBox;
    private JTextField betAmountField;
    private JButton confirmBetButton;
    private JButton showHistoryButton;
    private JTable oddsTable;
    private JLabel balanceLabel;
    private JTextArea betHistoryArea;

    private List<Horse> horses;
    private List<BetHistory> betHistory;
    private double userBalance = 100.0;  // Sample user balance
    private BettingOddsCalculator oddsCalculator;
    private String trackCondition = "Dry"; // Track condition can change dynamically

    public BettingUI(List<Horse> horses) {
        this.horses = horses;  // Use the passed-in horses
        this.betHistory = new ArrayList<>();
        this.oddsCalculator = new BettingOddsCalculator();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Betting System");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Top panel for horse selection
        JPanel topPanel = new JPanel(new FlowLayout());
        String[] horseNames = horses.stream().map(Horse::getName).toArray(String[]::new);
        horseComboBox = new JComboBox<>(horseNames);
        topPanel.add(new JLabel("Select a Horse:"));
        topPanel.add(horseComboBox);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        // Center panel with bet amount and history
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // Bet amount panel
        JPanel betAmountPanel = new JPanel(new FlowLayout());
        betAmountField = new JTextField(10);
        betAmountPanel.add(new JLabel("Enter Bet Amount: "));
        betAmountPanel.add(betAmountField);
        centerPanel.add(betAmountPanel, BorderLayout.NORTH);
        
        // Bet history area
        betHistoryArea = new JTextArea();
        betHistoryArea.setEditable(false);
        centerPanel.add(new JScrollPane(betHistoryArea), BorderLayout.CENTER);
        
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

        // Balance display
        balanceLabel = new JLabel("Balance: " + userBalance + " units");
        frame.getContentPane().add(balanceLabel, BorderLayout.WEST);

        // Odds Table (East)
        String[] columnNames = {"Horse", "Odds"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        oddsTable = new JTable(tableModel);
        frame.getContentPane().add(new JScrollPane(oddsTable), BorderLayout.EAST);

        // Button panel (South)
        JPanel buttonPanel = new JPanel();
        confirmBetButton = new JButton("Place Bet");
        confirmBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeBet();
            }
        });

        showHistoryButton = new JButton("Show Betting History");
        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBettingHistory();
            }
        });

        buttonPanel.add(confirmBetButton);
        buttonPanel.add(showHistoryButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Update odds dynamically
        updateOddsTable();

        // Finalize frame setup
        frame.pack();
        frame.setVisible(true);
    }

    private void placeBet() {
        String selectedHorseName = (String) horseComboBox.getSelectedItem();
        try {
            double betAmount = Double.parseDouble(betAmountField.getText());

            if (betAmount <= 0) {
                JOptionPane.showMessageDialog(frame, "Bet amount must be positive.");
                return;
            }

            if (betAmount > userBalance) {
                JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                return;
            }

            userBalance -= betAmount;

            // Find selected horse
            Horse selectedHorse = horses.stream()
                    .filter(horse -> horse.getName().equals(selectedHorseName))
                    .findFirst()
                    .orElse(null);

            if (selectedHorse != null) {
                selectedHorse.placeBet(betAmount);

                // Simulate win/loss (for demonstration)
                boolean isWin = Math.random() < 0.5;  // 50% chance of winning
                double resultAmount = isWin ? betAmount * selectedHorse.getOdds() : -betAmount;

                // Record the bet history with horse name
                BetHistory betHistoryEntry = new BetHistory(selectedHorseName, betAmount, resultAmount, isWin);
                this.betHistory.add(betHistoryEntry);

                // Update odds
                double updatedOdds = oddsCalculator.calculateOdds(selectedHorse, trackCondition);
                selectedHorse.setOdds(updatedOdds);

                // Update UI
                balanceLabel.setText("Balance: " + userBalance + " units");
                updateOddsTable();
                
                // Show result in history area with horse name
                betHistoryArea.append(String.format("%s: Bet %.2f - %s %.2f units\n", 
                    selectedHorseName,
                    betAmount,
                    isWin ? "WON" : "LOST", 
                    Math.abs(resultAmount)));
                
                // Clear the bet amount field for next bet
                betAmountField.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid bet amount.");
        }
    }

    private void updateOddsTable() {
        DefaultTableModel tableModel = (DefaultTableModel) oddsTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (Horse horse : horses) {
            tableModel.addRow(new Object[]{horse.getName(), String.format("%.2f", horse.getOdds())});
        }
    }

    public void updateBettingHistory() {
        betHistoryArea.setText(""); // Clear previous history
        for (BetHistory bet : betHistory) {
            betHistoryArea.append(String.format("%s: Bet %.2f - %s %.2f units\n", 
                bet.getHorseName(),
                bet.getBetAmount(),
                bet.isWin() ? "WON" : "LOST", 
                Math.abs(bet.getResultAmount())));
        }
    }

    private void showBettingHistory() {
        // Create a new frame to show betting history in a table
        JFrame historyFrame = new JFrame("Betting History");
        historyFrame.setBounds(100, 100, 700, 400);

        // Table model to display history - now includes horse name
        String[] columnNames = {"Horse", "Amount Bet", "Result", "Amount Won/Lost", "Cumulative Winnings"};
        DefaultTableModel historyTableModel = new DefaultTableModel(columnNames, 0);

        // Calculate cumulative winnings
        double cumulativeWinnings = 0;
        for (BetHistory bet : betHistory) {
            cumulativeWinnings += bet.getResultAmount();
            
            historyTableModel.addRow(new Object[]{
                bet.getHorseName(),
                String.format("%.2f", bet.getBetAmount()),
                bet.isWin() ? "Win" : "Loss",
                String.format("%.2f", bet.getResultAmount()),
                String.format("%.2f", cumulativeWinnings)
            });
        }

        JTable historyTable = new JTable(historyTableModel);
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyFrame.add(historyScrollPane);
        historyFrame.setVisible(true);

            // Close button to close the history frame
        JButton closeHistoryButton = new JButton("Close History");
        closeHistoryButton.addActionListener(e -> historyFrame.dispose());

        JPanel closePanel = new JPanel();
        closePanel.add(closeHistoryButton);
        historyFrame.add(closePanel, BorderLayout.SOUTH);
    
        historyFrame.setVisible(true);
    }
}