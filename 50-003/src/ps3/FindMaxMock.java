package ps3;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

/**
 * Created by eiros_000 on 17/2/2017.
 */
public class FindMaxMock {
    @Test
    public void testFindMax() {
        Mockery context = new JUnit4Mockery();
        final Sorter sorter = context.mock(Sorter.class);

        int[] inputArray = new int[]{2,6,4};

        context.checking(new Expectations() {{
            oneOf(sorter).sort(new int[]{2,6,4});
            will(returnValue(new int[]{2,4,6}));
        }});

        FindMaxUsingSorting.findmax(inputArray,sorter);
        context.assertIsSatisfied();
    }
}
