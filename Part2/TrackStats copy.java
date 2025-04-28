import java.util.HashMap;
import java.util.Map;

public class TrackStats {
    private Map<String, Double> bestTimes; // Store best times for each track
    private Map<String, String> trackConditions; // Store conditions for each track

    public TrackStats() {
        this.bestTimes = new HashMap<>();
        this.trackConditions = new HashMap<>();
    }

    // Update best time for a track
    public void updateTrackStats(String trackName, double time, String condition) {
        bestTimes.put(trackName, Math.min(bestTimes.getOrDefault(trackName, Double.MAX_VALUE), time));
        trackConditions.put(trackName, condition);
    }

    public double getBestTimeForTrack(String trackName) {
        return bestTimes.getOrDefault(trackName, Double.MAX_VALUE);
    }

    public String getTrackCondition(String trackName) {
        return trackConditions.getOrDefault(trackName, "Unknown");
    }
}
