public class TestArray {
    public static void main(String[] args) {
        int[][] testArray = { { 1, 2 }, { 3, 4 } };
        for (int j= 0; j < testArray.length; j++) {
            for (int i = 0; i < testArray.length; i++) {
                System.out.println(testArray[i][j]);
            }
        }

    }
}
