package ps7;

import javax.sound.midi.SysexMessage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by eiros_000 on 26/3/2017.
 */
public class ExamExample {

    private static final int noOfStudents = 20;

    public static void main(String[] args) {

        Thread[] studentArray = new Thread[noOfStudents];

        Phaser phaser = new Phaser(1);
        for (int i=0; i<noOfStudents; i++) {
            studentArray[i] = new Thread(new StudentAction(phaser,i));
            studentArray[i].start();
        }

        phaser.arriveAndAwaitAdvance();
        System.out.println("All students are ready, exam begins");
        phaser.arriveAndAwaitAdvance();

        phaser.arriveAndAwaitAdvance();
        System.out.println("Teacher leaves the room");


    }
}

class StudentAction implements Runnable {

    private Phaser phaser;
    private int studentNo;

    public StudentAction(Phaser phaser, int studentNo) {
        this.phaser = phaser;
        this.studentNo = studentNo;
        this.phaser.register();
    }

    public void run() {
        System.out.println("Student " + studentNo + " is ready");
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndAwaitAdvance();
        System.out.println("Student " + studentNo + " hands in script");
        phaser.arriveAndDeregister();
        System.out.println("Student " + studentNo + " leaves the room");
    }
}