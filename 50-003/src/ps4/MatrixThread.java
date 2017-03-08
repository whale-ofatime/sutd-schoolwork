package ps4;

import java.util.Arrays;

/**
 * Created by eiros_000 on 27/2/2017.
 */
public class MatrixThread {
    static int[][] matrix1 = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
    static int[][] matrix2 = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
    static int[][] result = new int[3][3];
    public static void main(String[] args) {
        MatrixMultThread thread0 = new MatrixMultThread(0);
        MatrixMultThread thread1 = new MatrixMultThread(1);
        MatrixMultThread thread2 = new MatrixMultThread(2);
        thread0.run();
        thread1.run();
        thread2.run();
        try {
            thread0.join();
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.deepToString(result));

    }
}

class MatrixMultThread extends Thread {
    private int row;
    private int width = 3;
    public MatrixMultThread(int row) {
        this.row = row;
    }
    public void run() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                MatrixThread.result[row][i] += MatrixThread.matrix1[row][j] * MatrixThread.matrix2[j][i];
            }
        }
    }
}