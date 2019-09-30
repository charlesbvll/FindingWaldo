package main;

/**
 * @author Charles BEAUVILLE and Mike Sinsoillier
 * <p>
 * Where is Charlie Project
 */
public final class Main {

    public static void main(String[] args) {
    	testGetRed();
    	testGrayscale();
    	testGetGreen();
    	testGetBlue();
    	testGetGray();
    	testGetRGB();
    	testFindBest();
        testFindNBest();
    	pixelAbsoluteError();
        testToGray();
        testToRGB();
        testDistanceBasedSearch();
    	testSimilarityBasedSearch();
        testNCCPatternEqualImage();
        testSimilarityPatternEqualImage();
        testSimilaritySimple();
    	findCharlie();
    }

    /*
     * Tests for Class ImageProcessing
     */
    public static void testGetRed() {
        int color = 0b11110000_00001111_01010101;
        int ref = 0b11110000;
        int red = ImageProcessing.getRed(color);
        if (red == ref) {
            System.out.println("Test red passed");
        } else {
            System.out.println("Test red failed. Returned value = " + red + " Expected value = " + ref);
        }
    }

    public static void testGrayscale() {
        System.out.println("Test Grayscale");
        int[][] image = Helper.read("images/food.png");
        double[][] gray = ImageProcessing.toGray(image);
        Helper.show(ImageProcessing.toRGB(gray), "test bw");
    }

    public static void testGetGreen() {
        int color = 0b11110000_00001111_01010101;
        int ref = 0b00001111;
        int green = ImageProcessing.getGreen(color);
        if (green == ref) {
            System.out.println("Test green passed");
        } else {
            System.out.println("Test green failed. Returned value = " + green + " Expected value = " + ref);
        }
    }

    public static void testGetBlue() {
        int color = 0b11110000_00001111_01010101;
        int ref = 0b01010101;
        int blue = ImageProcessing.getBlue(color);
        if (blue == ref) {
            System.out.println("Test blue passed");
        } else {
            System.out.println("Test blue failed. Returned value = " + blue + " Expected value = " + ref);
        }
    }

    public static void testGetGray() {
        int color = 0b11110000_00001111_01010101;
        int ref = 0b01110001;
        double gray = ImageProcessing.getGray(color);
        if (Math.round(gray) == ref) {
            System.out.println("Test gray passed");
        } else {
            System.out.println("Test gray failed. Returned value = " + gray + " Expected value = " + ref);
        }
    }

    public static void testGetRGB() {
        int ref = 0b11110000_00001111_01010101;
        int red = 0b11110000;
        int green = 0b00001111;
        int blue = 0b01010101;
        int RGB = ImageProcessing.getRGB(red, green, blue);
        if (RGB == ref) {
            System.out.println("Test RGB 1 passed");
        } else {
            System.out.println("Test RGB 1 failed. Returned value = " + RGB + " Expected value = " + ref);
        }
        int rgb = ImageProcessing.getRGB(127.0);
        int ref2 = 0x7f7f7f;
        if (rgb == ref2) {
            System.out.println("Test RGB 2 passed");
        } else {
            System.out.println("Test RGB 2 failed. Returned value = " + rgb + " Expected value = " + ref2);
        }
    }
    public static void testToGray() {
        System.out.println("Test toGray");
        double[][] ref = new double[][] {{0b01110001}, {0b01110001},{0b01110001}};
        int [][] image = new int[][] {{0b01110001_01110001_01110001}, {0b01110001_01110001_01110001},{0b01110001_01110001_01110001}};
        double[][] toGray = ImageProcessing.toGray(image);
        for(int i=0 ; i<toGray.length; i++) {
            for(int j =0 ; j<toGray[0].length ; j++) {
                if ((ref[i][j]==toGray[i][j])) {
                    System.out.println("Test passed");
                } else {
                    System.out.println("Test failed.");
                }
            }
        }
    }

    public static void testToRGB() {
        System.out.println("Test toRGB");
        int[][] ref = new int[][] {{0b01110001_01110001_01110001}, {0b01110001_01110001_01110001},{0b01110001_01110001_01110001}};
        double [][] image = new double[][] {{0b01110001}, {0b01110001},{0b01110001}};
        int[][] toRGB = ImageProcessing.toRGB(image);
        for(int i=0 ; i<toRGB.length; i++) {
            for(int j =0 ; j<toRGB[0].length ; j++) {
                if ((ref[i][j]==toRGB[i][j])) {
                    System.out.println("Test passed");
                } else {
                    System.out.println("Test  failed.");
                }
            }
        }
    }

    /*
     * Tests for Class Collector
     */
    public static void testFindBest() {
        System.out.println("Test findBest");
        double[][] t = new double[][]{{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
        int[] coords = Collector.findBest(t, false);
        System.out.println("Coordonnes : " + coords);
    }

    public static void testFindNBest() {
        System.out.println("Test findNBest");
        double[][] t = new double[][]{{20, 30, 10, 50, 32}, {28, 39, 51, 78, 91}};
        int[][] coords = Collector.findNBest(10, t, true);
        for (int[] a : coords) {
            int r = a[0];
            int c = a[1];
            System.out.println("Row=" + r + " Col=" + c + " Val=" + t[r][c]);
        }
    }

    /*
     * Tests for Class DistanceBasedSearch
     */

    public static void pixelAbsoluteError() {
        int color = 0b11110000_00001111_01010101;
        int color2 = 0b11010000_00101011_01010001;
        int ref = 21;
        int AE = (int) DistanceBasedSearch.pixelAbsoluteError(color, color2);
        if (AE == ref) {
            System.out.println("Test AE passed");
        } else {
            System.out.println("Test AE failed. Returned value = " + AE + " Expected value = " + ref);
        }
    }

    public static void testDistanceBasedSearch() {
        System.out.println("Test DistanceBasedSearch");
        int[][] food = Helper.read("images/food.png");
        int[][] onions = Helper.read("images/onions.png");
        double[][] distance = DistanceBasedSearch.distanceMatrix(onions, food);
        int[] p = Collector.findBest(distance, true);
        Helper.drawBox(p[0], p[1], onions[0].length, onions.length, food);
        Helper.show(food, "Found!");
    }

    /*
     * Tests for Class SimilarityBasedSearch
     */

    public static void testSimilarityBasedSearch() {
        System.out.println("Test SimilarityBasedSearch");
        int[][] food = Helper.read("images/food.png");
        int[][] onions = Helper.read("images/onions.png");
        double[][] foodGray = ImageProcessing.toGray(food);
        double[][] onionsGray = ImageProcessing.toGray(onions);
        double[][] similarity = SimilarityBasedSearch.similarityMatrix(onionsGray, foodGray);
        int[][] best = Collector.findNBest(8, similarity, false);
        for (int[] a : best) {
            int r = a[0];
            int c = a[1];
            Helper.drawBox(r, c, onions[0].length, onions.length, food);
        }
        Helper.show(food, "Found again!");
    }

    public static void findCharlie() {
        System.out.println("Find Charlie");
        int[][] beach = Helper.read("images/charlie_beach.png");
        int[][] charlie = Helper.read("images/charlie.png");
        double[][] beachGray = ImageProcessing.toGray(beach);
        double[][] charlieGray = ImageProcessing.toGray(charlie);

        System.out.println("Compute Similarity Matrix: expected time about 2 min");
        double[][] similarity = SimilarityBasedSearch.similarityMatrix(charlieGray, beachGray);

        System.out.println("Find N Best");
        int[] best = Collector.findBest(similarity, false);
        double max = similarity[best[0]][best[1]];

        Helper.show(ImageProcessing.matrixToRGBImage(similarity, -1, max), "Similarity");

        Helper.drawBox(best[0], best[1], charlie[0].length, charlie.length, beach);
        System.out.println("drawBox at (" + best[0] + "," + best[1] + ")");
        Helper.show(beach, "Found again!");
    }

    /*
     * Tests for extreme cases
     */

    public static void testNCCPatternEqualImage() {
        double[][] pattern = {{0, 0, 0},
                {0, 255, 0},
                {0, 0, 0}};
        double similarity = SimilarityBasedSearch.normalizedCrossCorrelation(0, 0, pattern, pattern);
        if (similarity == 1.0) {
            System.out.println("PASSED");
        } else {
            System.out.println("ERROR: expected value 1.0 but was " + similarity);
        }
    }

    public static void testSimilarityPatternEqualImage() {
        double[][] pattern = {{0, 255}};
        double[][] similarity = SimilarityBasedSearch.similarityMatrix(pattern, pattern);
        if (similarity.length == 1) {
            if (similarity[0][0] == 1.0) {
                System.out.println("PASSED");
            } else {
                System.out.println("ERROR: expected value 1.0 but was " + similarity[0][0]);
            }
        } else {
            System.out.println("ERROR: expected length 1 but was " + similarity.length);
        }
    }

    public static void testSimilaritySimple() {
        double[][] image = {{3, 2, 2, 2},
                {0, 3, 0, 0}};
        double[][] pattern = {{0, 3, 0}};
        double[][] similarity = SimilarityBasedSearch.similarityMatrix(pattern, image);

        if (similarity.length == 2 && similarity[0].length == 2) {
            if (similarity[0][0] == -0.5 && similarity[0][1] == -1.0 &&
                    similarity[1][0] == 1.0 && similarity[1][1] == -0.5) {
                System.out.println("PASSED");
            } else {
                System.out.println("ERROR: wrong values");
            }
        } else {
            System.out.println("ERROR: incorrect size");
        }
    }

}
