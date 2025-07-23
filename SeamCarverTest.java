import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class SeamCarverTest {
    @Test
    public void testEnergy() {
        Picture picture = new Picture("3x4.png");
        // System.out.println(picture.height());
        SeamCarver sc = new SeamCarver(picture);
        System.out.println(sc.energy(1, 2));
        // assertEquals(228.09, sc.energy(1, 2), 0.01);
        // energy of cell 1, 2 for above file
        assertEquals(Math.sqrt(52024), sc.energy(1, 2), 0.01);
        // energy of cell 1,1 for above file
        assertEquals(Math.sqrt(52225), sc.energy(1, 1), 0.01);
    }
}