package main;

public final class ImageProcessing {

    /**
     * Checks wether or not a RGB component is between 0 and 255 and returns the right value.
     *
     * @param value : an integer.
     * @return an integer,  between 0 and 255
     */
    public static int checkInt(int value) {
        if (value < 0)
            value = 0;
        if (value > 255)
            value = 255;
        return value;
    }

    /**
     * Returns red component from given packed color.
     *
     * @param rgb : a 32-bits RGB color
     * @return an integer,  between 0 and 255
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xff;
    }

    /**
     * Returns green component from given packed color.
     *
     * @param rgb : a 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(int, int, int)
     */
    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xff;
    }

    /**
     * Returns blue component from given packed color.
     *
     * @param rgb : a 32-bits RGB color
     * @return an integer between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(int, int, int)
     */
    public static int getBlue(int rgb) {
        return rgb & 0xff;
    }


    /**
     * Returns the average of red, green and blue components from given packed color.
     *
     * @param rgb : 32-bits RGB color
     * @return a double between 0 and 255
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public static double getGray(int rgb) {
        return ((getRed(rgb) + getBlue(rgb) + getGreen(rgb)) / 3.0);
    }

    /**
     * Returns packed RGB components from given red, green and blue components.
     *
     * @param red   : an integer
     * @param green : an integer
     * @param blue  : an integer
     * @return a 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
    public static int getRGB(int red, int green, int blue) {
        return checkInt(red) << 16 | checkInt(green) << 8 | checkInt(blue);
    }

    /**
     * Returns packed RGB components from given gray-scale value.
     *
     * @param gray : an integer
     * @return a 32-bits RGB color
     * @see #getGray
     */
    public static int getRGB(double gray) {
        int g = (int) Math.round(gray);
        return checkInt(g) << 16 | checkInt(g) << 8 | checkInt(g);
    }

    /**
     * Converts packed RGB image to gray-scale image.
     *
     * @param image : a HxW integer array
     * @return a HxW double array
     * @see #encode
     * @see #getGray
     */
    public static double[][] toGray(int[][] image) {
        double[][] img = new double[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                img[i][j] = getGray(image[i][j]);
            }
        }
        return img;
    }

    /**
     * Converts gray-scale image to packed RGB image.
     *
     * @param channels : a HxW double array
     * @return a HxW integer array
     * @see #decode
     * @see #getRGB(double)
     */
    public static int[][] toRGB(double[][] gray) {

        int[][] img = new int[gray.length][gray[0].length];
        for (int i = 0; i < gray.length; i++) {
            for (int j = 0; j < gray[0].length; j++) {
                img[i][j] = getRGB(gray[i][j]);
            }
        }
        return img;
    }

    /**
     * Convert an arbitrary 2D double matrix into a 2D integer matrix
     * which can be used as RGB image
     *
     * @param matrix : the arbitrary 2D double array to convert into integer
     * @param min    : a double, the minimum value the matrix could theoretically contains
     * @param max    : a double, the maximum value the matrix could theoretically contains
     * @return an 2D integer array, containing a RGB mapping of the matrix
     */
    public static int[][] matrixToRGBImage(double[][] matrix, double min, double max) {
        int[][] imageRGB = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                imageRGB[i][j] = getRGB(255.0 * ((matrix[i][j] - min) / (max - min)));
            }
        }
        return imageRGB;
    }
}
