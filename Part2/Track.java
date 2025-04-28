public class Track {
    private int lanes;
    private int length; 
    private String unit;  
    private String shape;
    private String condition;


    public Track(int lanes, int length, String unit, String shape, String condition) {
        this.lanes = lanes;
        this.length = length;
        this.unit = unit;
        this.shape = shape;
        this.condition = condition;
    }

    public int getLanes() {
        return lanes;
    }

    public int getLength() {
        return length;
    }

    public String getUnit() {
        return unit;
    }

    public String getShape() {
        return shape;
    }

    public String getCondition() {
        return condition;
    }

    public String getTrackInfo() {
        return "Track: " + shape + "\n" +
               "Lanes: " + lanes + "\n" +
               "Length: " + length + " " + unit + "\n" +
               "Condition: " + condition;
    }
}
