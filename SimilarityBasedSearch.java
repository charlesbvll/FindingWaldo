package main;

public class SimilarityBasedSearch {

    /**
     * Computes the mean value of a gray-scale image given as a 2D array
     *
     * @param image : a 2D double array, the gray-scale Image
     * @return a double value between 0 and 255 which is the mean value
     */
    public static double mean(double[][] image) {

        assert image.length != 0 : "image contains no pixel";
        double sum = 0;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                sum += image[i][j];
            }
        }

        return sum / ((double) image.length * image[0].length);
    }

    /**
     * Computes the mean value of a part of an image
     *
     * @param matrix : a 2D array of double, the gray-scale image we want to calculate the mean value of a part
     * @param row    : a integer, the row-coordinate of the upper left corner of the pattern in the image.
     * @param column : a integer, the column-coordinate of the upper left corner of the pattern in the image.
     * @param width  : a integer, the width of the window.
     * @param height : a integer, the height of the window.
     * @return a double, the mean value of the specified part of the input matrix
     */
    static double windowMean(double[][] matrix, int row, int col, int width, int height) {

        double[][] temp = new double[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                temp[i][j] = matrix[i + row][j + col];
            }
        }

        return mean(temp);
    }

    /**
     * Computes the Normalized Cross Correlation of a gray-scale pattern if positioned
     * at the provided row, column-coordinate in a gray-scale image
     *
     * @param row     : a integer, the row-coordinate of the upper left corner of the pattern in the image.
     * @param column  : a integer, the column-coordinate of the upper left corner of the pattern in the image.
     * @param pattern : an 2D array of doubles, the gray-scale pattern to find
     * @param image   : an 2D array of double, the gray-scale image where to look for the pattern
     * @return a double, the Normalized Cross Correlation value at position (row, col) between the pattern and the part of
     * the base image that is covered by the pattern, if the pattern is shifted by x and y.
     * should return -1 if the denominator is 0
     */
    public static double normalizedCrossCorrelation(int row, int col, double[][] pattern, double[][] image) {

        assert image.length != 0 : "image contains no pixel";
        assert pattern.length != 0 : "pattern contains no pixel";
        double wmean = windowMean(image, row, col, pattern.length, pattern[0].length);
        double m = mean(pattern);
        double sum1 = 0, sum2 = 0, sum3 = 0;

        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[0].length; j++) {
                sum1 += (image[row + i][col + j] - wmean) * (pattern[i][j] - m);
                sum2 += (image[row + i][col + j] - wmean) * (image[row + i][col + j] - wmean);
                sum3 += (pattern[i][j] - m) * (pattern[i][j] - m);
            }
        }

        double result;

        if ((Math.sqrt(sum2 * sum3)) == 0)
            result = -1;
        else
            result = sum1 / (Math.sqrt(sum2 * sum3));

        return result;
    }


    /**
     * Compute the similarityMatrix between a gray-scale image and a gray-scale pattern
     *
     * @param pattern : an 2D array of doubles, the gray-scale pattern to find
     * @param image   : an 2D array of doubles, the gray-scale image where to look for the pattern
     * @return a 2D array of doubles, containing for each pixel of a original gray-scale image,
     * the similarity (normalized cross-correlation) between the image's window and the pattern
     * placed over this pixel (upper-left corner)
     */
    public static double[][] similarityMatrix(double[][] pattern, double[][] image) {

        assert pattern.length != 0 : "pattern contains no pixel";
        assert image.length != 0 : "image contains no pixel";

        int W = image[0].length;
        int w = pattern[0].length;
        int H = image.length;
        int h = pattern.length;
        double[][] matrix = new double[H - h + 1][W - w + 1];

        for (int i = 0; i < H - h + 1; i++) {
            for (int j = 0; j < W - w + 1; j++) {
                matrix[i][j] = normalizedCrossCorrelation(i, j, pattern, image);
            }
        }

        return matrix;
    }

}
