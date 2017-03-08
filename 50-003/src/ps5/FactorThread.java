package ps5;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by eiros_000 on 8/3/2017.
 */
public class FactorThread {
    static BigInteger semiPrime = new BigInteger("11");
    static ArrayList<BigInteger> listOfPrimes = new ArrayList<>();
    private static int numberOfThreads = 2;
    private static ArrayList<Thread> threadList = new ArrayList<>();
    static Object lock = new Object();
    static boolean notified = false;
    static ArrayList<BigInteger> finalFactors = new ArrayList<>();


    public static void main(String[] args) {

        // Generate list of primes from 2 to root of input
        BigInteger root = sqrt(semiPrime);
        BigInteger nextPrime = new BigInteger("2");
        while (nextPrime.compareTo(root)<=0) {
            listOfPrimes.add(nextPrime);
            nextPrime = nextPrime.nextProbablePrime();
        }

        if (numberOfThreads > listOfPrimes.size()) {
            for (int i=0; i<listOfPrimes.size(); i++) {
                threadList.add(new FindFactor(i,i+1));
            }
        } else {
            double aveSize = (double) listOfPrimes.size() / (double) numberOfThreads;
            int size = (int) Math.ceil(aveSize);
            int remainingSize = listOfPrimes.size();
            int remainingThread = numberOfThreads;
            for (int i=0; i<numberOfThreads; i++) {
                if (remainingSize > remainingThread) {
                    threadList.add(new FindFactor(i,i+size));
                    remainingSize = remainingSize - size;
                } else {
                    threadList.add(new FindFactor(i,i+1));
                    remainingSize--;
                }
                remainingThread--;
            }
        }

        for (Thread thread:threadList) {
            thread.start();
        }

        finalFactors.add(BigInteger.ONE);
        finalFactors.add(semiPrime);
        boolean allThreadsFinished;

        synchronized (lock) {
            while (!notified) {
                try {
                    lock.wait(100);
                    System.out.println("Waiting");
                } catch (InterruptedException e) {
                    System.out.println("Process is interrupted");
                }
                allThreadsFinished = true;
                for (Thread thread:threadList) {
                    if (thread.isAlive()){
                        allThreadsFinished = false;
                    }
                }
                if (allThreadsFinished == true) {
                    notified = true;
                }
            }
            for (Thread thread:threadList) {
                thread.interrupt();
            }
        }

        System.out.print("Factors of the semiprime are " + finalFactors.get(0) + " and " + finalFactors.get(1));

    }

    static BigInteger sqrt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }

    public static ArrayList<BigInteger> findFactor(BigInteger input, ArrayList<BigInteger> designatedPrimes) {
        ArrayList<BigInteger> primeFactors = new ArrayList<>();
        BigInteger tempInput = input;

        // if input is less than 2
        if (tempInput.compareTo(BigInteger.valueOf(2))<0) {
            return primeFactors;
        }

        for (int i=0; i<designatedPrimes.size(); i++) {
            BigInteger currentPrime = designatedPrimes.get(i);
            while (tempInput.mod(currentPrime).compareTo(BigInteger.ZERO)==0) {
                primeFactors.add(currentPrime);
                tempInput = tempInput.divide(currentPrime);
            }
        }

        if (tempInput.compareTo(BigInteger.ONE)>0) {
            primeFactors.add(tempInput);
        }
        return primeFactors;
    }

    public static void announce(ArrayList<BigInteger> factorList) {
        synchronized (lock) {
            finalFactors = factorList;
            notified = true;
            lock.notify();
        }
    }
}

class FindFactor extends Thread {
    private int startIndex;
    private int endIndex;
    private ArrayList<BigInteger> factorList;
    public FindFactor(int start, int end) {
        this.startIndex = start;
        this.endIndex = end;
    }
    public void run() {
        ArrayList<BigInteger> designatedPrimes = new ArrayList<>(FactorThread.listOfPrimes.subList(startIndex,endIndex));
        factorList = FactorThread.findFactor(FactorThread.semiPrime,designatedPrimes);
        if (factorList.size()!=1) {
            FactorThread.announce(factorList);
        }
    }
    public ArrayList<BigInteger> getFactorList() {
        return factorList;
    }
}

