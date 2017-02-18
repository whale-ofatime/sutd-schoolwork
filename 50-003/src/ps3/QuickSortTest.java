package ps3;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.*;

/**
 * Created by eiros_000 on 17/2/2017.
 */
@RunWith(Parameterized.class)
public class QuickSortTest {
    public int[] inputArray, sortedArray;

    public QuickSortTest(int[] inputArray, int[] sortedArray){
        this.inputArray = inputArray;
        this.sortedArray = sortedArray;
    }

    @Parameters
    public static Collection<Object[]> parameters(){
        return Arrays.asList(new int[][][] {
                {{3,4,2},{2,3,4}},
                {{4,6,5},{4,5,6}}
        });
    }

    @Test
    public void test() {
        QuickSort quickSort = new QuickSort();
        quickSort.sort(inputArray);
        assertTrue(Arrays.equals(inputArray,sortedArray));
    }
}
