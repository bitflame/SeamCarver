import edu.princeton.cs.algs4.Picture;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class SeamCarverTest {
    Picture picture = new Picture("3x4.png");

    @Test
    public void testEnergy() {
        // System.out.println(picture.height());
        SeamCarver sc = new SeamCarver(picture);
        // System.out.println(sc.energy(1, 2));
        // assertEquals(228.09, sc.energy(1, 2), 0.01);
        // energy of cell 1, 2 for above file
        assertEquals(Math.sqrt(52024), sc.energy(1, 2), 0.01);
        // energy of cell 1,1 for above file
        assertEquals(Math.sqrt(52225), sc.energy(1, 1), 0.01);
    }

    @Test
    public void testFindVerticalSeam3x4() {
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 0, 1, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam3x4() {
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 1, 2, 1 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testRemoveVerticalSeam() {
        SeamCarver sc = new SeamCarver(picture);

    }

    @Test
    public void testFindVerticalSeam3x7() {
        picture = new Picture("3x7.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 0, 1, 1, 1, 1, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam3x7() {
        picture = new Picture("3x7.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 1, 2, 1 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam4x6() {
        picture = new Picture("4x6.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 1, 2, 1, 1, 2, 1 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam4x6() {
        picture = new Picture("4x6.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 1, 2, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam5x6() {
        picture = new Picture("5x6.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 1, 2, 2, 3, 2, 1 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam5x6() {
        picture = new Picture("5x6.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 2, 3, 2, 3, 2 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam6x5() {
        picture = new Picture("6x5.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 3, 4, 3, 2, 1 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam6x5() {
        picture = new Picture("6x5.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 1, 2, 1, 2, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam7x3() {
        picture = new Picture("7x3.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 2, 3, 2 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam7x3() {
        picture = new Picture("7x3.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 0, 1, 1, 1, 1, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam7x10() {
        picture = new Picture("7x10.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        // System.out.println("Here is the distance table for 7x10 file:");
        // for (int i : sc.findVerticalSeam()) {
        //     System.out.printf("%4d", i);
        // }
        // System.out.println();
        int[] expectedSeam = { 2, 3, 4, 3, 4, 3, 3, 2, 2, 1 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam7x10() {
        picture = new Picture("7x10.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 6, 7, 7, 7, 8, 8, 7 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam10x10() {
        picture = new Picture("10x10.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 6, 7, 7, 7, 7, 7, 8, 8, 7, 6 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam10x10() {
        picture = new Picture("10x10.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 0, 1, 2, 3, 3, 3, 3, 2, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam10x12() {
        picture = new Picture("10x12.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam10x12() {
        picture = new Picture("10x12.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 8, 9, 10, 10, 10, 9, 10, 10, 9, 8 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam12x10() {
        picture = new Picture("12x10.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 6, 7, 7, 6, 6, 7, 7, 7, 8, 7 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalSeam12x10() {
        picture = new Picture("12x10.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 7, 8, 7, 8, 7, 6, 5, 6, 6, 5, 4, 3 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalStripes() {
        picture = new Picture("stripes.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        // 0 1 1 1 1 1 1 1 1 1 1 0
        int[] expectedSeam = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalStripes() {
        picture = new Picture("stripes.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 0, 1, 1, 1, 1, 1, 1, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalDiagonals() {
        picture = new Picture("diagonals.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindHorizontalDiagonals() {
        picture = new Picture("diagonals.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findHorizontalSeam();
        int[] expectedSeam = { 0, 1, 1, 1, 1, 1, 1, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSean1x8() {
        picture = new Picture("1x8.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 0, 0, 0, 0, 0, 0, 0 ,0};
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

}