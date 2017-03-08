package ps4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by eiros_000 on 27/2/2017.
 */
public class MatrixThreadArray {
    static int sizeOfMatrix = 4;
    static int[][] matrix1 = new int[sizeOfMatrix][sizeOfMatrix];
    static int[][] matrix2 = new int[sizeOfMatrix][sizeOfMatrix];

    static int[][] result = new int[sizeOfMatrix][sizeOfMatrix];

    public static void main(String[] args) {
        Random random = new Random();

        for (int i=0; i<sizeOfMatrix; i++) {
            for (int j=0; j<sizeOfMatrix; j++) {
                matrix1[i][j] = random.nextInt(10);
                matrix2[i][j] = random.nextInt(10);
            }
        }

        System.out.println("Matrix 1");
        System.out.println(Arrays.deepToString(matrix1));
        System.out.println("Matrix 2");
        System.out.println(Arrays.deepToString(matrix2));

        ArrayList<Thread> threadList= new ArrayList<Thread>();
        for (int i=0; i<sizeOfMatrix; i++) {
            threadList.add(new MatrixMultArrayThread(i,sizeOfMatrix));
            threadList.get(i).run();
        }
        try {
            for (int i=0; i<sizeOfMatrix; i++) {
                threadList.get(i).join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Result");
        System.out.println(Arrays.deepToString(result));

    }

}

class MatrixMultArrayThread extends Thread {
    private int row;
    private int width;
    public MatrixMultArrayThread(int row, int width) {
        this.row = row;
        this.width = width;
    }
    public void run() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                MatrixThreadArray.result[row][i] += MatrixThreadArray.matrix1[row][j] * MatrixThreadArray.matrix2[j][i];
            }
        }
    }
}