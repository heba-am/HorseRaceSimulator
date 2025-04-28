class RaceTest{
    public static void main(String[] args){
        Race r = new Race(10);
        Horse h1 = new Horse('\u2658', "quenny", 0.4);
        Horse h2 = new Horse('\u2658', "messi", 0.7);
        Horse h3 = new Horse('\u2658', "anne", 0.5);
        r.addHorse(h1, 1);
        r.addHorse(h2, 2);
        r.startRace();
    }
}