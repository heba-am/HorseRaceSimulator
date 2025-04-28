import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatsPanel extends JPanel {
    private HorseStats horseStats;
    private JTextArea statsArea;

    public StatsPanel(HorseStats horseStats) {
        this.horseStats = horseStats;
        this.statsArea = new JTextArea(10, 30);
        this.statsArea.setEditable(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(statsArea), BorderLayout.CENTER);
        updateStatsDisplay();
    }

    public void updateStatsDisplay() {
        String stats = String.format("Horse: %s\nAverage Speed: %.2f\nWin Ratio: %.2f%%\nConfidence: %.2f\n\nRace History:\n",
                horseStats.getHorseName(), horseStats.getAverageSpeed(), horseStats.getWinRatio(), horseStats.getAverageConfidence());

        List<String> raceHistory = horseStats.getRaceHistory();
        for (String race : raceHistory) {
            stats += race + "\n";
        }

        statsArea.setText(stats);
    }
}
