public class BettingOddsCalculator {
    // Constants for weight adjustment
    private static final double SPEED_WEIGHT = 0.4;
    private static final double WIN_RATIO_WEIGHT = 0.4;
    private static final double CONFIDENCE_WEIGHT = 0.2;

    public static double calculateOdds(HorseStats horseStats, String trackCondition) {
        // Base odds (starting point for odds calculation)
        double odds = 1.0;

        // Factor in the horse's average speed (higher speed leads to higher odds)
        odds += (horseStats.getAverageSpeed() * SPEED_WEIGHT);

        // Factor in the win ratio (higher win ratio leads to higher odds)
        odds += (horseStats.getWinRatio() * WIN_RATIO_WEIGHT);

        // Factor in the confidence (higher confidence leads to higher odds)
        odds += (horseStats.getAverageConfidence() * CONFIDENCE_WEIGHT);

        // Factor in the track condition (adjust for better performance in specific conditions)
        double trackConditionAdjustment = getTrackConditionAdjustment(horseStats, trackCondition);
        odds += trackConditionAdjustment;

        // Ensure the odds do not go below a minimum threshold
        if (odds < 1.0) {
            odds = 1.0;
        }

        return odds;
    }

    public double calculateOdds(Horse horse, String trackCondition) {
        double baseOdds = 1.0;

        // Adjust odds based on horse performance
        if (horse.getOdds() < 1.5) {
            baseOdds = 1.2; // Example for a top-performing horse
        } else if (horse.getOdds() > 3.0) {
            baseOdds = 2.5; // For an underdog horse
        }

        // Adjust odds based on track condition (wet track could decrease odds for certain horses)
        if (trackCondition.equals("Wet")) {
            baseOdds *= 1.1; // Increase odds for underperforming horses in wet conditions
        }

        return baseOdds;
    }

    private static double getTrackConditionAdjustment(HorseStats horseStats, String trackCondition) {
        // Adjustments based on the horse's past performance in specific track conditions
        if (trackCondition.equalsIgnoreCase("Dry")) {
            // Horses with good performance on dry tracks get better odds
            return 0.1;  // Higher odds for horses performing well on dry tracks
        } else if (trackCondition.equalsIgnoreCase("Wet")) {
            // If the horse performs well on wet tracks, adjust odds to reflect that
            return 0.15;
        } else if (trackCondition.equalsIgnoreCase("Icy")) {
            // Adjust for icy conditions, increasing the odds for horses that handle it well
            return 0.2;
        }
        return 0; // Default for neutral conditions
    }

    public static void updateBettingOddsBasedOnTrends(HorseStats horseStats, String trackCondition) {
        // Update betting odds dynamically after each race or event
        double newOdds = calculateOdds(horseStats, trackCondition);
        System.out.println("Updated Odds: " + newOdds);
    }
}
