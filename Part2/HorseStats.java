import java.util.ArrayList;
import java.util.List;

public class HorseStats {
    private String horseName;
    private List<Double> raceTimes;
    private List<Double> speeds;
    private List<Double> confidenceChanges;
    private int wins;
    private int falls;
    private int races;
    private double averageSpeed;
    private double bestTime;
    private double winRatio;
    private double averageConfidence;
    private List<String> raceHistory;

    public HorseStats(String horseName) {
        this.horseName = horseName;
        this.raceTimes = new ArrayList<>();
        this.speeds = new ArrayList<>();
        this.confidenceChanges = new ArrayList<>();
        this.raceHistory = new ArrayList<>();
        this.wins = 0;
        this.falls = 0;
        this.races = 0;
        this.bestTime = Double.MAX_VALUE;
    }

    public void updateRaceStats(double time, double speed, boolean won, boolean fell, double confidence) {
        races++;
        raceTimes.add(time);
        speeds.add(speed);
        confidenceChanges.add(confidence);
        
        if (time < bestTime) bestTime = time;
        if (won) wins++;
        if (fell) falls++;

        raceHistory.add(String.format("Race %d: %s - Time: %.2fs, Speed: %.2f, %s, %s",
            races,
            won ? "WON" : "LOST",
            time,
            speed,
            fell ? "FELL" : "COMPLETED",
            String.format("Confidence: %.2f", confidence)
        ));

        calculateAverages();
    }

    private void calculateAverages() {
        double totalSpeed = 0;
        double totalTime = 0;
        for (double speed : speeds) {
            totalSpeed += speed;
        }
        for (double time : raceTimes) {
            totalTime += time;
        }

        averageConfidence = confidenceChanges.stream().mapToDouble(Double::doubleValue).average().orElse(0.5);

        averageSpeed = totalSpeed / races;
        winRatio = (double) wins / races * 100;
    }

    public String getHorseName() {
        return horseName;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public double getWinRatio() {
        return winRatio;
    }

    public double getAverageConfidence() {
        return averageConfidence;
    }

    public List<String> getRaceHistory() {
        return raceHistory;
    }

    public String getFormattedStats() {
        return String.format(
            "Horse: %s\n" +
            "Races: %d\n" +
            "Wins: %d (%.1f%%)\n" +
            "Falls: %d\n" +
            "Best Time: %.2fs\n" +
            "Avg Speed: %.2f\n" +
            "Avg Confidence: %.2f\n\n" +
            "Race History:\n%s",
            horseName, races, wins, winRatio, falls, bestTime, 
            averageSpeed, averageConfidence, String.join("\n", raceHistory)
        );
    }
}