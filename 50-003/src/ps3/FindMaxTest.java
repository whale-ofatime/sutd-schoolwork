package ps3;
import junit.framework.TestCase;

/**
 * Created by eiros_000 on 17/2/2017.
 */
public class FindMaxTest extends TestCase {

    public void testPass(){
        FindMax findMax = new FindMax();
        int[] test = new int[]{1,2,3,4,5};
        assertEquals(findMax.max(test),5);
    }

    public void testFailure(){
        FindMax findMax = new FindMax();
        int[] test = new int[]{1,2,3,4,5};
        assertEquals(findMax.max(test),9);
    }

    public void testError(){
        FindMax findMax = new FindMax();
        int[] test = null;
        if (test == null) {
            throw new NullPointerException();
        }
    }

}