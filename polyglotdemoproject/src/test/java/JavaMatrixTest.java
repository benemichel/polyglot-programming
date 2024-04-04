import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class JavaMatrixTest {

    @Test
    public void filterMatrix() {
        int[][] matrix = new int[3][3];
        int value = 1;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = value++;
            }
        }

        ArrayList<Integer> filtered = new ArrayList<>();
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > 5) {
                    filtered.add(matrix[i][j]);
                }
            }
        }
      
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(6);
        expected.add(7);
        expected.add(8);
        expected.add(9);

        assertTrue(filtered.containsAll(expected));

    }
}
