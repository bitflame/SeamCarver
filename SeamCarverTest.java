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
    public void testFindVerticalSeam3x7() {
        picture = new Picture("3x7.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 0, 1, 1, 1, 1, 1, 0 };
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }

    @Test
    public void testFindVerticalSeam4x6() {
        picture = new Picture("3x7.png");
        SeamCarver sc = new SeamCarver(picture);
        int[] actualSeam = sc.findVerticalSeam();
        int[] expectedSeam = { 1, 2, 1, 1, 2, 1 };
        for (int i = 0; i < actualSeam.length; i++) {
            System.out.print(" " + actualSeam[i] + " ");
        }
        Assert.assertArrayEquals(expectedSeam, actualSeam);
    }
}