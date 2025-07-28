public class TestArray {
    public int[][] transposeArray(int[][] original) {
        int[][] result = new int[original[0].length][original.length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[0].length; j++) {
                result[j][i] = original[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] testArray = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        TestArray testArray1 = new TestArray();
        testArray1.transposeArray(testArray);
    }
}
