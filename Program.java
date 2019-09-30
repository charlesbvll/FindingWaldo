package main;

public class Program {

    public static void main(String[] args) {
        System.out.println("Loading files...");
        int[][] image = Helper.read("images/charlie_beach.png");
        int[][] pattern = Helper.read("images/charlie.png");
        System.out.println("Searching...");
        double[][] distance = DistanceBasedSearch.distanceMatrix(pattern, image);
        System.out.println("Processing...");
        int[] p = Collector.findBest(distance, true);
        Helper.drawBox(p[0], p[1], pattern[0].length, pattern.length, image);
        Helper.show(image, "Found!");
    }
}
