/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

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
        if (x == 0 || x == picture.width() - 1 || y == 0 || y == picture.height() - 1)
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
        double[][] matrix = new double[picture.height()][picture.width()];
        int[][] edgeTo = new int[picture.height()][picture.width()];
        double[] distTo = new double[picture.width()];
        int[] verticalSean = new int[picture.width()];
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                matrix[i][j] = energy(j, i);
            }
        }
        printMatrix(matrix);
        // Go through each row update edgeTo and distTo
        double leftParentCost, middleParentCost, rightParentCost, currentNodeValue;
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                if (i == 0) {
                    distTo[j] = matrix[i][j];
                    edgeTo[i][j] = -1;
                }
                else if (j == 0) {
                    // there is not left parent - just middle and right
                    middleParentCost = distTo[j];
                    rightParentCost = distTo[j + 1];
                    if (middleParentCost < rightParentCost) {
                        distTo[j] = middleParentCost + matrix[i][j];
                        edgeTo[i][j] = j;
                    }
                    else {
                        distTo[j] = rightParentCost + matrix[i][j];
                        edgeTo[i][j] = j + 1;
                    }
                }
                else if (j == picture.width() - 1) {
                    // there is no right parent - just middle and left
                    middleParentCost = distTo[j];
                    leftParentCost = distTo[j - 1];
                    if (middleParentCost < leftParentCost) {
                        distTo[j] = distTo[j] + matrix[i][j];
                        edgeTo[i][j] = j;
                    }
                    else {
                        distTo[j] = distTo[j - 1] + matrix[i][j];
                        edgeTo[i][j] = j - 1;
                    }
                }
                else {
                    leftParentCost = distTo[j - 1];
                    middleParentCost = distTo[j];
                    rightParentCost = distTo[j + 1];
                    currentNodeValue = matrix[i][j];
                    double leftSum = currentNodeValue + leftParentCost;
                    double middleSum = currentNodeValue + middleParentCost;
                    double rightSum = currentNodeValue + rightParentCost;
                    if (leftSum < middleSum && leftSum < rightSum) {
                        distTo[j] = leftSum;
                        edgeTo[i][j] = j - 1;
                    }
                    else if (middleSum < leftSum && middleSum < rightSum) {
                        distTo[j] = middleSum;
                        edgeTo[i][j] = j;
                    }
                    else if (rightSum < middleSum && rightSum < leftSum) {
                        distTo[j] = rightSum;
                        edgeTo[i][j] = j + 1;
                    }
                    else {
                        throw new RuntimeException(
                                "There is a matrix cost that you did not account for at matrix location"
                                        + i + j + "LeftSum: " + leftSum + "Middle Sum: " + middleSum
                                        + "RightSum: " + rightSum);
                    }
                }
                matrix[i][j] = energy(j, i);
            }
        }
        // when done find the cell with minimal cost, and trace edgeTo to find the path - return the indices
        return verticalSean;
    }

    private void printMatrix(double[][] matrix) {
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                matrix[i][j] = energy(j, i);
                System.out.printf("%9.0f", matrix[i][j]);
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

    //  unit testing (optional)
    public static void main(String[] args) {
        SeamCarver seamCarver = new SeamCarver(new Picture("3x4.png"));
        seamCarver.findVerticalSeam();
    }
}
