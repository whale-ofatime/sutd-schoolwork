package ps3;

/**
 * Created by eiros_000 on 17/2/2017.
 */
public class FindMaxUsingSorting {
    public static int findmax (int[] inputArr, Sorter sorter) {
        int[] result = sorter.sort(inputArr);
        return result[result.length-1];
    }
}