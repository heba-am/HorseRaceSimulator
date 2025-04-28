public class BetHistory {
    private String horseName;
    private double betAmount;
    private double resultAmount;
    private boolean isWin;

    public BetHistory(String horseName, double betAmount, double resultAmount, boolean isWin) {
        this.horseName = horseName;
        this.betAmount = betAmount;
        this.resultAmount = resultAmount;
        this.isWin = isWin;
    }

    public String getHorseName() {
        return horseName;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public double getResultAmount() {
        return resultAmount;
    }

    public boolean isWin() {
        return isWin;
    }

    @Override
    public String toString() {
        return String.format("%s: Bet %.2f, Result %.2f, %s", 
               horseName, betAmount, resultAmount, isWin ? "Win" : "Loss");
    }
}