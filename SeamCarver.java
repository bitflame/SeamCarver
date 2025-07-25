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
    private double[][] verticalDistanceTo;
    private double[][] horizontalDistanceTo;
    private int[][] horizontalEdgeTo;
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
        horizontalEdgeTo = new int[pictureHeight][pictureWidth];
        verticalDistanceTo = new double[pictureHeight][pictureWidth];
        horizontalDistanceTo = new double[pictureHeight][pictureWidth];
        verticalSeam = new int[pictureHeight];
        horizontalSeam = new int[pictureWidth];

        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {
                energy[i][j] = energy(j, i);
                verticalDistanceTo[i][j] = Double.POSITIVE_INFINITY;
                horizontalDistanceTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    private void horizontalRelax(int x, int y) {
        if (x == 0 || y == 0 || x == pictureHeight - 1 || y == pictureWidth - 1) {
            verticalDistanceTo[x][y] = energy[x][y];
            horizontalDistanceTo[x][y] = energy[x][y];
            edgeTo[x][y] = -1;
            horizontalEdgeTo[x][y] = -1;
        }
        if (horizontalDistanceTo[x][y] > horizontalDistanceTo[x - 1][y - 1] + energy[x][y]) {
            horizontalDistanceTo[x][y] = horizontalDistanceTo[x - 1][y - 1] + energy[x][y];
        }
        if (horizontalDistanceTo[x][y] > horizontalDistanceTo[x][y - 1] + energy[x][y]) {
            horizontalDistanceTo[x][y] = horizontalDistanceTo[x][y - 1] + energy[x][y];
        }
        if (horizontalDistanceTo[x][y] > horizontalDistanceTo[x + 1][y - 1] + energy[x][y]) {
            horizontalDistanceTo[x][y] = horizontalDistanceTo[x + 1][y - 1] + energy[x][y];
        }
    }

    // relax the pixels
    private void verticalRelax(int x, int y) {
        if (x == 0 || y == 0 || x == pictureHeight - 1 || y == pictureWidth - 1) {
            verticalDistanceTo[x][y] = energy[x][y];
            edgeTo[x][y] = -1;
        }
        else if (y >= 1 || y < pictureWidth) {
            if (verticalDistanceTo[x][y] > verticalDistanceTo[x - 1][y] + energy[x][y]) {
                verticalDistanceTo[x][y] = verticalDistanceTo[x - 1][y] + energy[x][y];
                edgeTo[x][y] = y;
            }
        }
        else if (y > 0 && y < pictureWidth - 1) {
            if (verticalDistanceTo[x][y] > verticalDistanceTo[x - 1][y - 1] + energy[x][y]) {
                verticalDistanceTo[x][y] = verticalDistanceTo[x - 1][y - 1] + energy[x][y];
                edgeTo[x][y] = y - 1;
            }
            if (verticalDistanceTo[x][y] > verticalDistanceTo[x - 1][y] + energy[x][y]) {
                verticalDistanceTo[x][y] = verticalDistanceTo[x - 1][y] + energy[x][y];
                edgeTo[x][y] = y;
            }
            if (verticalDistanceTo[x][y] > verticalDistanceTo[x - 1][y + 1] + energy[x][y]) {
                verticalDistanceTo[x][y] = verticalDistanceTo[x - 1][y + 1] + energy[x][y];
                edgeTo[x][y] = y + 1;
            }
        }
    }


    // current picture{
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

        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 1; i < pictureHeight - 1; i++) {
            if (minDistance > verticalDistanceTo[i][pictureWidth - 2]) {
                minDistance = verticalDistanceTo[i][pictureWidth - 2];
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
        for (int i = 0; i < pictureHeight; i++) {
            for (int j = 0; j < pictureWidth; j++) {
                verticalRelax(i, j);
            }
        }
        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 1; i < pictureWidth - 1; i++) {
            if (minDistance > verticalDistanceTo[pictureHeight - 2][i]) {
                minDistance = verticalDistanceTo[pictureHeight - 2][i];
                minIndex = i;
            }
        }
        int rowCounter = pictureHeight - 1;
        verticalSeam[rowCounter--] = minIndex - 1;
        do {
            verticalSeam[rowCounter] = minIndex;
            minIndex = edgeTo[rowCounter--][minIndex];

        } while (rowCounter > 0);
        verticalSeam[rowCounter] = minIndex - 1;
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
        System.out.println("\n Here is the horizontal seam:");
        for (int i : seamCarver.findHorizontalSeam()) {
            System.out.printf("%d ", i);
        }
        seamCarver = new SeamCarver(new Picture("3x7.png"));
        System.out.println("Here is the vertical seam:");
        for (int i : seamCarver.findVerticalSeam()) {
            System.out.printf("%d ", i);
        }
        seamCarver = new SeamCarver(new Picture("4x6.png"));
        System.out.println("Here is the vertical seam:");
        for (int i : seamCarver.findVerticalSeam()) {
            System.out.printf("%d ", i);
        }
    }
}
