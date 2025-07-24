/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

import java.awt.Color;

public class SeamCarver {
    Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x <= 0 || x >= picture.width() - 1 || y <= 0 || y >= picture.height() - 1)
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
        return new int[1];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energy = new double[picture.height()][picture.width()];
        int[][] edgeTo = new int[picture.height()][picture.width()];
        double[][] distTo = new double[picture.height()][picture.width()];
        int[] verticalSean = new int[picture.width()];
        Stack<Integer> path = new Stack<>();
        double leftParentDistance, middleParentDistance, rightParentDistance;
        int rowMinIndex = 0;
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                energy[i][j] = energy(j, i);
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
                else if (j == picture.width() - 1) {
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
        printMyTwoDimensionalArray(edgeTo);
        // todo - Go through the last row of the energy matrix, find the minimum's index and the follow the edgeTo...
        double minDistance = Double.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < picture.width(); i++) {
            if (minDistance > distTo[picture.height() - 1][i]) {
                minDistance = distTo[picture.height() - 1][i];
                minIndex = i;
            }
        }
        int rowCounter = picture.height() - 1;
        while (minIndex != -1) {
            path.push(minIndex);
            minIndex = edgeTo[rowCounter--][minIndex];
        }
        return verticalSean;
    }

    private void printMyTwoDimensionalArray(double[][] matrix) {
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                System.out.printf("%9.0f", matrix[i][j]);
            }
            System.out.println();
        }
    }

    private void printMyTwoDimensionalArray(int[][] matrix) {
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
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
        seamCarver.findVerticalSeam();
    }
}
