/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture pictureCopy;
    private double[][] energy;
    // private int[][] edgeTo;
    // private double[][] distTo;
    private int[] verticalSeam;
    private int[] horizontalSeam;
    private int pictureHeight, pictureWidth;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException(
                "Picture object passed to this method can not be null.");
        this.pictureCopy = new Picture(picture);
        pictureHeight = picture.height();
        pictureWidth = picture.width();
        energy = new double[pictureHeight][pictureWidth];
        verticalSeam = new int[pictureHeight];
        horizontalSeam = new int[pictureWidth];
        for (int y = 0; y < pictureHeight; y++) {
            for (int x = 0; x < pictureWidth; x++) {
                energy[y][x] = energy(x, y);
            }
        }
        picture = pictureCopy;
    }

    // current picture{
    public Picture picture() {
        return pictureCopy;
    }

    // width of current picture
    public int width() {
        return pictureWidth;
    }

    // height of current picture
    public int height() {
        return pictureHeight;
    }

    private void verifyCoordinates(int x, int y) {
        if (x < 0 || x > pictureWidth - 1 || y < 0 || y > pictureHeight - 1)
            throw new IllegalArgumentException(
                    "At least one of the Coordinates is not in a valid range.");
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        verifyCoordinates(x, y);
        if (x == 0 || x == pictureWidth - 1 || y == 0 || y == pictureHeight - 1)
            return 1000.00;
        Color left = pictureCopy.get(x - 1, y);
        Color right = pictureCopy.get(x + 1, y);
        Color top = pictureCopy.get(x, y - 1);
        Color bottom = pictureCopy.get(x, y + 1);
        double sum = getDeltaX(right, left) + getDeltaY(bottom, top);
        return Math.sqrt(sum);
    }

    // calculate x-gradient using the left and right pixelx
    private double getDeltaX(Color right, Color left) {

        // double leftRed = left.getRed();
        int leftRGB = left.getRGB();
        // int leftAlpha = (leftRGB >> 24) & 0xff;
        int leftRed = (leftRGB >> 16) & 0xff;
        int leftGreen = (leftRGB >> 8) & 0xff;
        int leftBlue = (leftRGB >> 0) & 0xff;
        int rightRGB = right.getRGB();
        // int rightAlpha = (rightRGB >> 24) & 0xff;
        int rightRed = (rightRGB >> 16) & 0xff;
        int rightGreen = (rightRGB >> 8) & 0xff;
        int rightBlue = (rightRGB >> 0) & 0xff;
        double result = Math.pow(Math.abs(leftRed - rightRed), 2) + Math.pow(
                Math.abs(leftGreen - rightGreen), 2) + Math.pow(Math.abs(leftBlue - rightBlue), 2);
        return result;

    }

    // calculate y-gradient using the bottom and top pixles
    private double getDeltaY(Color bottom, Color top) {
        int bottomRGB = bottom.getRGB();
        int bottomRed = (bottomRGB >> 16) & 0xff;
        int bottomGreen = (bottomRGB >> 8) & 0xff;
        int bottomBlue = (bottomRGB >> 0) & 0xff;
        int topRGB = top.getRGB();
        int topRed = (topRGB >> 16) & 0xff;
        int topGreen = (topRGB >> 8) & 0xff;
        int topBlue = (topRGB >> 0) & 0xff;
        double result = Math.pow(Math.abs(topRed - bottomRed), 2) + Math.pow(
                Math.abs(topGreen - bottomGreen), 2) + Math.pow(Math.abs(topBlue - bottomBlue), 2);
        return result;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // resetdistToArray();
        int[][] edgeTo = new int[pictureHeight][pictureWidth];
        double[][] distTo = new double[pictureHeight][pictureWidth];
        double topSum, middleSum, bottomSum;
        for (int j = 1; j < pictureWidth - 1; j++) {
            for (int i = 1; i < pictureHeight - 1; i++) {
                // if I am in col 1 regardless of which row I am in
                if (j == 1) {
                    distTo[i][j] = energy[i][j] + distTo[i - 1][j - 1];
                    edgeTo[i][j] = i - 1;
                }
                else if (pictureHeight == 3) {
                    // case 1 - only one row
                    distTo[i][j] = energy[i][j] + distTo[i][j - 1];
                    edgeTo[i][j] = i;
                }
                else if (pictureHeight > 3 && i == 1) {
                    // Case 2 - only middle and bottom parent/sum - The first row
                    middleSum = energy[i][j] + distTo[i][j - 1];
                    bottomSum = energy[i][j] + distTo[i + 1][j - 1];
                    if (bottomSum < middleSum) {
                        distTo[i][j] = bottomSum;
                        edgeTo[i][j] = i + 1;
                    }
                    else {
                        distTo[i][j] = middleSum;
                        edgeTo[i][j] = i;
                    }
                }
                else if (pictureHeight > 3 && i == pictureHeight - 2) {
                    // Case 3 - I am on last row and only have middle and top sum/parent
                    topSum = energy[i][j] + distTo[i - 1][j - 1];
                    middleSum = energy[i][j] + distTo[i][j - 1];
                    if (middleSum < topSum) {
                        distTo[i][j] = middleSum;
                        edgeTo[i][j] = i;
                    }
                    else {
                        distTo[i][j] = topSum;
                        edgeTo[i][j] = i - 1;
                    }
                }
                else {
                    topSum = energy[i][j] + distTo[i - 1][j - 1];
                    middleSum = energy[i][j] + distTo[i][j - 1];
                    bottomSum = energy[i][j] + distTo[i + 1][j - 1];
                    if (bottomSum < middleSum && bottomSum < topSum) {
                        distTo[i][j] = bottomSum;
                        edgeTo[i][j] = i + 1;
                    }
                    else if (middleSum < bottomSum && middleSum < topSum) {
                        distTo[i][j] = middleSum;
                        edgeTo[i][j] = i;
                    }
                    else {
                        distTo[i][j] = topSum;
                        edgeTo[i][j] = i - 1;
                    }
                }
            }
        }
        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 1; i < pictureHeight - 1; i++) {
            if (minDistance > distTo[i][pictureWidth - 2]) {
                minDistance = distTo[i][pictureWidth - 2];
                minIndex = i;
            }
        }
        int columnCounter = pictureWidth - 1;
        horizontalSeam[columnCounter--] = minIndex - 1;
        while (columnCounter >= 0) {
            horizontalSeam[columnCounter] = minIndex;
            minIndex = edgeTo[minIndex][columnCounter--];
        }
        return horizontalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] distTo = new double[pictureHeight][pictureWidth];
        int[][] edgeTo = new int[pictureHeight][pictureWidth];
        double leftSum, middleSum, rightSum;
        for (int i = 1; i < pictureHeight - 1; i++) {
            for (int j = 1; j < pictureWidth - 1; j++) {
                if (i == 1) {
                    distTo[i][j] = energy[i][j];
                    edgeTo[i][j] = j - 1;
                }
                // Case 1 - only 1 column
                else if (pictureWidth == 3) {
                    distTo[i][j] = energy[i][j] + distTo[i - 1][j];
                    edgeTo[i][j] = j;
                }
                else if (pictureWidth > 3 && j == 1) {
                    // Case 2 - The first
                    rightSum = energy[i][j] + distTo[i - 1][j + 1];
                    middleSum = energy[i][j] + distTo[i - 1][j];
                    if (rightSum < middleSum) {
                        distTo[i][j] = energy[i][j] + distTo[i - 1][j + 1];
                        edgeTo[i][j] = j + 1;
                    }
                    else {
                        distTo[i][j] = energy[i][j] + distTo[i - 1][j];
                        edgeTo[i][j] = j;
                    }
                }
                else if (pictureWidth > 3 && j == pictureWidth - 2) {
                    // Case 3 - the last column
                    middleSum = energy[i][j] + distTo[i - 1][j];
                    leftSum = energy[i][j] + distTo[i - 1][j - 1];
                    if (middleSum < leftSum) {
                        distTo[i][j] = middleSum;
                        edgeTo[i][j] = j;
                    }
                    else {
                        distTo[i][j] = leftSum;
                        edgeTo[i][j] = j - 1;
                    }
                }
                else {
                    // Every other situation
                    leftSum = energy[i][j] + distTo[i - 1][j - 1];
                    middleSum = energy[i][j] + distTo[i - 1][j];
                    rightSum = energy[i][j] + distTo[i - 1][j + 1];
                    if (leftSum <= middleSum && leftSum <= rightSum) {
                        distTo[i][j] = leftSum;
                        edgeTo[i][j] = j - 1;
                    }
                    else if (middleSum < leftSum && middleSum <= rightSum) {
                        distTo[i][j] = middleSum;
                        edgeTo[i][j] = j;
                    }
                    else if (rightSum < leftSum && rightSum < middleSum) {
                        distTo[i][j] = rightSum;
                        edgeTo[i][j] = j + 1;
                    }
                }

            }
        }
        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 1; i < pictureWidth - 1; i++) {
            if (minDistance > distTo[pictureHeight - 2][i]) {
                minDistance = distTo[pictureHeight - 2][i];
                minIndex = i;
            }
        }
        int rowCounter = pictureHeight - 1;
        verticalSeam[rowCounter--] = minIndex - 1;
        do {
            verticalSeam[rowCounter] = minIndex;
            minIndex = edgeTo[rowCounter--][minIndex];

        } while (rowCounter > 0);
        verticalSeam[rowCounter] = minIndex;
        return verticalSeam;
    }

    private void printMyTwoDimensionalArray(double[][] matrix) {
        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {
                System.out.printf("%9.0f", matrix[i][j]);
            }
            System.out.println();
        }
    }

    private void printMyTwoDimensionalArray(int[][] matrix) {
        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {
                System.out.printf("%9d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("The array passed to this method can not be null.");
        if (seam.length != pictureWidth)
            throw new IllegalArgumentException("Horizontal seam length mismatch.");
        verifySeam(seam, pictureWidth);
        if (pictureHeight <= 1)
            throw new IllegalArgumentException("The image height is not large enough.");
        Picture update = new Picture(pictureWidth, pictureHeight);
        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {

            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("The array passed to this method can not be null.");
        if (seam.length != pictureHeight)
            throw new IllegalArgumentException("Vertical seam length mismatch.");
        verifySeam(seam, pictureHeight);
        if (pictureWidth <= 1)
            throw new IllegalArgumentException("The image width is not large enough.");
        Picture updatedPicture = new Picture(pictureWidth - 1, pictureHeight);
        double[][] updatedEnergyArray = new double[pictureHeight][pictureWidth - 1];

        int seamIndex = 0;
        int leftRGB, rightRGB, topRGB, bottomRGB;
        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {
                System.arraycopy(energy[i], 0, updatedEnergyArray[i], 0, seam[seamIndex]);
                System.arraycopy(energy[i], seam[seamIndex] + 1, updatedEnergyArray[i],
                                 seam[seamIndex], pictureWidth - (seam[seamIndex] + 1));
                if (j == 0 || j == pictureWidth - 1) updatedEnergyArray[i][j] = 1000;
                else if (j < seam[seamIndex]) {
                    int argb = pictureCopy.getRGB(j, i);
                    updatedPicture.setRGB(j, i, argb);
                }
                else {
                    updatedPicture.setRGB(j - 1, i, pictureCopy.getRGB(j, i));
                }
            }
            // todo - I might be able to add code here to update the energy values for rows 1 and below once I
            // am on row 3 and beyond. I do need to add the code for row 2 when the top is a border and
            // last row and first and last columns
            if (i >= 3) {
                leftRGB = pictureCopy.getRGB(seam[seamIndex] - 1, i - 2);
                int leftAlpha = (leftRGB >> 24) & 0xff;
                int leftRed = (leftRGB >> 16) & 0xff;
                int leftGreen = (leftRGB >> 8) & 0xff;
                int leftBlue = (leftRGB >> 0) & 0xff;
                rightRGB = updatedPicture.getRGB(seam[seamIndex] + 1, i - 2);
                int rightAlpha = (rightRGB >> 24) & 0xff;
                int rightRed = (rightRGB >> 16) & 0xff;
                int rightGreen = (rightRGB >> 8) & 0xff;
                int rightBlue = (rightRGB >> 0) & 0xff;
                double deltaX = Math.pow(Math.abs(leftRed - rightRed), 2) + Math.pow(
                        Math.abs(leftGreen - rightGreen), 2) + Math.pow(
                        Math.abs(leftBlue - rightBlue), 2);
                topRGB = updatedPicture.getRGB(seam[seamIndex], i - 3);
                int topAlpha = (topRGB >> 24) & 0xff;
                int topRed = (topRGB >> 16) & 0xff;
                int topGreen = (topRGB >> 8) & 0xff;
                int topBlue = (topRGB >> 0) & 0xff;
                bottomRGB = updatedPicture.getRGB(seam[seamIndex], i - 1);
                int bottomAlpha = (bottomRGB >> 24) & 0xff;
                int bottomRed = (bottomRGB >> 16) & 0xff;
                int bottomGreen = (bottomRGB >> 8) & 0xff;
                int bottomBlue = (bottomRGB >> 0) & 0xff;
                double deltaY = Math.pow(Math.abs(topRed - bottomRed), 2) + Math.pow(
                        Math.abs(topGreen - bottomGreen), 2) + Math.pow(
                        Math.abs(topBlue - bottomBlue),
                        2);
                // int argb = (a << 24) | (r << 16) | (g << 8) | (b << 0);
                updatedEnergyArray[i][seam[seamIndex]] = Math.sqrt(deltaX + deltaY);
            }
            seamIndex++;
        }
        energy = updatedEnergyArray;
        // I left the code below here in case the strategy above did not work
        // for (int i = 0; i < pictureHeight; i++) {
        //     leftRGB = updatedPicture.getRGB(seam[seamIndex] - 1, i);
        //     int leftAlpha = (leftRGB >> 24) & 0xff;
        //     int leftRed = (leftRGB >> 16) & 0xff;
        //     int leftGreen = (leftRGB >> 8) & 0xff;
        //     int leftBlue = (leftRGB >> 0) & 0xff;
        //     rightRGB = updatedPicture.getRGB(seam[seamIndex] + 1, i);
        //     int rightAlpha = (rightRGB >> 24) & 0xff;
        //     int rightRed = (rightRGB >> 16) & 0xff;
        //     int rightGreen = (rightRGB >> 8) & 0xff;
        //     int rightBlue = (rightRGB >> 0) & 0xff;
        //     double deltaX = Math.pow(Math.abs(leftRed - rightRed), 2) + Math.pow(
        //             Math.abs(leftGreen - rightGreen), 2) + Math.pow(Math.abs(leftBlue - rightBlue),
        //                                                             2);
        //     topRGB = updatedPicture.getRGB(seam[seamIndex], i - 1);
        //     int topAlpha = (topRGB >> 24) & 0xff;
        //     int topRed = (topRGB >> 16) & 0xff;
        //     int topGreen = (topRGB >> 8) & 0xff;
        //     int topBlue = (topRGB >> 0) & 0xff;
        //     bottomRGB = updatedPicture.getRGB(seam[seamIndex], i + 1);
        //     int bottomAlpha = (bottomRGB >> 24) & 0xff;
        //     int bottomRed = (bottomRGB >> 16) & 0xff;
        //     int bottomGreen = (bottomRGB >> 8) & 0xff;
        //     int bottomBlue = (bottomRGB >> 0) & 0xff;
        //     double deltaY = Math.pow(Math.abs(topRed - bottomRed), 2) + Math.pow(
        //             Math.abs(topGreen - bottomGreen), 2) + Math.pow(Math.abs(topBlue - bottomBlue),
        //                                                             2);
        //     energy[i][seam[seamIndex]] = Math.sqrt(deltaX + deltaY);
        //     seamIndex++;
        // }
        updatedPicture.save("UpdatedFile.jpg");
        pictureCopy = updatedPicture;
    }

    private void verifySeam(int[] seam, int limit) {
        int previous = 0;
        for (int i = 1; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > limit || seam[previous] > seam[i] + 1
                    || seam[previous] < seam[i] - 1)
                throw new IllegalArgumentException("The pixel address provided is not valid.");
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        SeamCarver seamCarver = new SeamCarver(new Picture("4x6.png"));
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());

        seamCarver = new SeamCarver(new Picture("stripes.png"));
        System.out.println(
                "\n Here is the vertical seam for stripes. Expecting: 0 1 1 1 1 1 1 1 1 1 1 0 Getting:");
        for (int i : seamCarver.findVerticalSeam()) {
            System.out.printf("%d ", i);
        }

        for (int i = 0; i < seamCarver.pictureHeight; i++) {
            for (int j = 0; j < seamCarver.pictureWidth; j++) {
                System.out.printf(" %9.2f", seamCarver.energy[i][j]);
            }
        }
        seamCarver = new SeamCarver(new Picture("3x4.png"));
        System.out.println("\n Here is the horizontal seam:");
        for (int i : seamCarver.findHorizontalSeam()) {
            System.out.printf("%d ", i);
        }

        seamCarver = new SeamCarver(new Picture("7x10.png"));
        System.out.println(
                "Expecting  2 3 4 3 4 3 3 2 2 1. Here is the vertical seam for 7x10 file:");
        for (int i : seamCarver.findVerticalSeam()) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        System.out.println("Here is the distance table for 7x10 file:");
        // seamCarver.printMyTwoDimensionalArray(seamCarver.distTo);
        System.out.println();
        seamCarver = new SeamCarver(new Picture("3x4.png"));
        System.out.println("Here is the vertical seam for 3x4 file:");
        for (int i : seamCarver.findVerticalSeam()) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        System.out.println("Here is the distance table for 3x4 file:");
        // seamCarver.printMyTwoDimensionalArray(seamCarver.distTo);
        System.out.println();
        seamCarver = new SeamCarver(new Picture("3x7.png"));
        System.out.println("Here is the vertical seam for 3x7 file:");
        for (int i : seamCarver.findVerticalSeam()) {
            System.out.printf("%d ", i);
        }
        System.out.println();
        System.out.println("Here is the distance table for 3x7 file:");
        // seamCarver.printMyTwoDimensionalArray(seamCarver.distTo);
        System.out.println();
        // Vertical seam: { 1 2 1 1 2 1 }
        // 1000.00  1000.00* 1000.00  1000.00
        // 1000.00   275.66   173.21* 1000.00
        // 1000.00   173.21*  321.01  1000.00
        // 1000.00   171.80*  195.63  1000.00
        // 1000.00   270.93   188.15* 1000.00
        // 1000.00  1000.00* 1000.00  1000.00
        // Total energy = 2706.370116
        seamCarver = new SeamCarver(new Picture("4x6.png"));
        // seamCarver.printMyTwoDimensionalArray(seamCarver.energy);
        System.out.println("Here is the vertical seam for 4x6 file:");
        for (int i : seamCarver.findVerticalSeam()) {
            System.out.printf("%d ", i);
        }
        System.out.println("Here is the distance table for 4x6 file:");
        // seamCarver.printMyTwoDimensionalArray(seamCarver.distTo);
        System.out.println();
    }
}
