package ps3;

/**
 * Created by eiros_000 on 17/2/2017.
 */
public class FindMax {
    public static void main (String[] arg) throws Exception {
        System.out.println(max(new int[]{5,6,17,8,2}));
    }

    public static int max (int[] list) {
        int max = list[0];
        for (int i = 1; i < list.length; i++) {
            if (max < list[i]) {
                max = list[i];
            }
        }

        return max;
    }
}