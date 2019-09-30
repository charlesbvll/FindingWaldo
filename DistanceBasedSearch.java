package main;

public class DistanceBasedSearch {

    /**
     * Computes the mean absolute error between two RGB pixels, channel by channel.
     *
     * @param patternPixel : a integer, the second RGB pixel.
     * @param imagePixel   : a integer, the first RGB pixel.
     * @return a double, the value of the error for the RGB pixel pair. (an integer in [0, 255])
     */
    public static double pixelAbsoluteError(int patternPixel, int imagePixel) {
        double sum = Math.abs(ImageProcessing.getRed(patternPixel) - ImageProcessing.getRed(imagePixel)) +
                Math.abs(ImageProcessing.getGreen(patternPixel) - ImageProcessing.getGreen(imagePixel)) +
                Math.abs(ImageProcessing.getBlue(patternPixel) - ImageProcessing.getBlue(imagePixel));
        return sum / 3.0;
    }

    /**
     * Computes the mean absolute error loss of a RGB pattern if positioned
     * at the provided row, column-coordinates in a RGB image
     *
     * @param row     : a integer, the row-coordinate of the upper left corner of the pattern in the image.
     * @param col     : a integer, the column-coordinate of the upper left corner of the pattern in the image.
     * @param pattern : an 2D array of integers, the RGB pattern to find
     * @param image   : an 2D array of integers, the RGB image where to look for the pattern
     * @return a double, mean absolute error value at position (row, col) between the pattern and the part of
     * the base image that is covered by the pattern, if the pattern is shifted by x and y.
     * should return -1 if the denominator is -1
     */
    public static double meanAbsoluteError(int row, int col, int[][] pattern, int[][] image) {
        assert pattern.length != 0 : "pattern contains no pixel";
        assert image.length != 0 : "image contains no pixel";
        assert row < image.length - pattern.length : "Motif non contenu entierement";
        assert col < image[0].length - pattern[0].length : "Motif non contenu entierement";
        double sum = 0;
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length; j++) {
                sum += pixelAbsoluteError(pattern[i][j], image[row + i][col + j]);
            }
        }
        double size = pattern.length * pattern[0].length;
        double eam = sum / size;
        return eam;
    }

    /**
     * Compute the distanceMatrix between a RGB image and a RGB pattern
     *
     * @param pattern : an 2D array of integers, the RGB pattern to find
     * @param image   : an 2D array of integers, the RGB image where to look for the pattern
     * @return a 2D array of doubles, containing for each pixel of a original RGB image,
     * the distance (meanAbsoluteError) between the image's window and the pattern
     * placed over this pixel (upper-left corner)
     */
    public static double[][] distanceMatrix(int[][] pattern, int[][] image) {
        assert pattern.length != 0 : "pattern contains no pixel";
        assert image.length != 0 : "image contains no pixel";
        int W = image[0].length;
        int w = pattern[0].length;
        int H = image.length;
        int h = pattern.length;
        double[][] matrix = new double[H - h][W - w];
        for (int i = 0; i < H - h; i++) {
            for (int j = 0; j < W - w; j++) {
                matrix[i][j] = meanAbsoluteError(i, j, pattern, image);
            }
        }
        return matrix;
    }
}
