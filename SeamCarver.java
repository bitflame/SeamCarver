/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;
    private int[][] edgeTo;
    private double[][] distTo;
    private int[] verticalSeam;
    private int[] horizontalSeam;
    private int pictureHeight, pictureWidth;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        pictureHeight = picture.height();
        pictureWidth = picture.width();
        energy = new double[pictureHeight][pictureWidth];
        edgeTo = new int[pictureHeight][pictureWidth];
        distTo = new double[pictureHeight][pictureWidth];
        verticalSeam = new int[pictureHeight];
        horizontalSeam = new int[pictureWidth];

        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {
                energy[i][j] = energy(j, i);
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        printMyTwoDimensionalArray(distTo);
    }

    // relax the pixels
    // todo - Need to update distTo for all the cells, so relax() needs modifying to stay in the matrix
    private void relax(int x, int y) {
        if (distTo[x][y] > energy[x - 1][y - 1] + energy[x][y])
            distTo[x][y] = energy[x - 1][y - 1] + energy[x][y];
        else if (distTo[x][y] > energy[x - 1][y] + energy[x][y])
            distTo[x][y] = energy[x - 1][y] + energy[x][y];
        else if (distTo[x][x] > energy[x - 1][y + 1] + energy[x][y])
            distTo[x][y] = energy[x - 1][y + 1] + energy[x][y];
    }


    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return pictureWidth;
    }

    // height of current picture
    public int height() {
        return pictureHeight;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x <= 0 || x >= pictureWidth - 1 || y <= 0 || y >= pictureHeight - 1)
            return 1000.00;
        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);
        Color top = picture.get(x, y - 1);
        Color bottom = picture.get(x, y + 1);
        double sum = getDeltaX(right, left) + getDeltaY(bottom, top);
        return Math.sqrt(sum);
    }

    // calculate x-gradient using the left and right pixelx
    private double getDeltaX(Color right, Color left) {
        double leftRed = left.getRed();
        double leftGreen = left.getGreen();
        double leftBlue = left.getBlue();
        double rightRed = right.getRed();
        double rightGreen = right.getGreen();
        double rightBlue = right.getBlue();
        double result = Math.pow(Math.abs(leftRed - rightRed), 2) + Math.pow(
                Math.abs(leftGreen - rightGreen), 2) + Math.pow(Math.abs(leftBlue - rightBlue), 2);
        return result;

    }

    // calculate y-gradient using the bottom and top pixles
    private double getDeltaY(Color bottom, Color top) {
        double bottomRed = bottom.getRed();
        double bottomGreen = bottom.getGreen();
        double bottomBlue = bottom.getBlue();
        double topRed = top.getRed();
        double topGreen = top.getGreen();
        double topBlue = top.getBlue();
        double result = Math.pow(Math.abs(topRed - bottomRed), 2) + Math.pow(
                Math.abs(topGreen - bottomGreen), 2) + Math.pow(Math.abs(topBlue - bottomBlue), 2);
        return result;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double topParentDistance, middleParentDistance, bottomParentDistance;
        this.picture = new Picture(picture);
        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {
                energy[i][j] = energy(j, i);
                if (j == 0) {
                    distTo[i][j] = energy[i][j];
                    edgeTo[i][j] = -1;
                }
                else if (i == 0) {
                    middleParentDistance = distTo[i][j - 1];
                    bottomParentDistance = distTo[i + 1][j - 1];
                    if (middleParentDistance > bottomParentDistance) {
                        distTo[i][j] = bottomParentDistance + energy[i][j];
                        edgeTo[i][j] = j - 1;
                    }
                    else {
                        distTo[i][j] = middleParentDistance + energy[i][j];
                        edgeTo[i][j] = j - 1;
                    }
                }
                else if (i == pictureHeight - 1) {
                    topParentDistance = distTo[i - 1][j - 1];
                    middleParentDistance = distTo[i][j - 1];
                    if (topParentDistance > middleParentDistance) {
                        distTo[i][j] = middleParentDistance + energy[i][j];
                        edgeTo[i][j] = i;
                    }
                    else {
                        distTo[i][j] = topParentDistance + energy[i][j];
                        edgeTo[i][j] = i - 1;
                    }
                }
                else {
                    topParentDistance = distTo[i - 1][j - 1];
                    middleParentDistance = distTo[i][j - 1];
                    bottomParentDistance = distTo[i + 1][j - 1];
                    if (topParentDistance > middleParentDistance
                            || topParentDistance > bottomParentDistance) {
                        if (middleParentDistance > bottomParentDistance) {
                            distTo[i][j] = bottomParentDistance + energy[i][j];
                            edgeTo[i][j] = i + 1;
                        }
                        distTo[i][j] = middleParentDistance + energy[i][j];
                        edgeTo[i][j] = i;
                    }
                    else {
                        distTo[i][j] = topParentDistance + energy[i][j];
                        edgeTo[i][j] = i - 1;
                    }
                }
            }
        }
        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < pictureHeight; i++) {
            if (minDistance > distTo[i][pictureWidth - 1]) {
                minDistance = distTo[i][pictureWidth - 1];
                minIndex = i;
            }
        }
        int columnCounter = pictureWidth - 1;
        while (minIndex != -1) {
            horizontalSeam[columnCounter] = minIndex;
            minIndex = edgeTo[columnCounter--][minIndex];
        }
        return horizontalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double leftParentDistance, middleParentDistance, rightParentDistance;
        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {
                if (i == 0) {
                    distTo[i][j] = energy[i][j];
                    edgeTo[i][j] = -1;
                }
                else if (j == 0) {
                    middleParentDistance = distTo[i - 1][j];
                    rightParentDistance = distTo[i - 1][j + 1];
                    if (middleParentDistance > rightParentDistance) {
                        distTo[i][j] = rightParentDistance + energy[i][j];
                        edgeTo[i][j] = j + 1;
                    }
                    else {
                        distTo[i][j] = middleParentDistance + energy[i][j];
                        edgeTo[i][j] = j;
                    }
                }
                else if (j == pictureWidth - 1) {
                    leftParentDistance = distTo[i - 1][j - 1];
                    middleParentDistance = distTo[i - 1][j];
                    if (leftParentDistance > middleParentDistance) {
                        distTo[i][j] = middleParentDistance + energy[i][j];
                        edgeTo[i][j] = j;
                    }
                    else {
                        distTo[i][j] = leftParentDistance + energy[i][j];
                        edgeTo[i][j] = j - 1;
                    }
                }
                else {
                    leftParentDistance = distTo[i - 1][j - 1];
                    middleParentDistance = distTo[i - 1][j];
                    rightParentDistance = distTo[i - 1][j + 1];
                    if (leftParentDistance > middleParentDistance
                            || leftParentDistance > rightParentDistance) {
                        if (middleParentDistance > rightParentDistance) {
                            distTo[i][j] = rightParentDistance + energy[i][j];
                            edgeTo[i][j] = j + 1;
                        }
                        distTo[i][j] = middleParentDistance + energy[i][j];
                        edgeTo[i][j] = j;
                    }
                    else {
                        distTo[i][j] = leftParentDistance + energy[i][j];
                        edgeTo[i][j] = j - 1;
                    }
                }
            }
        }
        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < pictureWidth; i++) {
            if (minDistance > distTo[pictureHeight - 1][i]) {
                minDistance = distTo[pictureHeight - 1][i];
                minIndex = i;
            }
        }
        int rowCounter = pictureHeight - 1;
        while (minIndex != -1) {
            verticalSeam[rowCounter] = minIndex;
            minIndex = edgeTo[rowCounter--][minIndex];
        }
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

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }

    private class Pixel implements Comparable<Pixel> {
        int x;
        int y;
        double energy;

        Pixel(int x, int y, double energy) {
            this.x = x;
            this.y = y;
            this.energy = energy;
        }

        public int compareTo(Pixel o) {
            if (this.energy > o.energy) return 1;
            if (this.energy < o.energy) return -1;
            return 0;
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        SeamCarver seamCarver = new SeamCarver(new Picture("3x4.png"));
        System.out.println("Here is the vertical seam:");
        for (int i : seamCarver.findVerticalSeam()) {
            System.out.printf("%d ", i);
        }
        for (int i : seamCarver.findHorizontalSeam()) {
            System.out.printf("%d ", i);
        }
    }
}
