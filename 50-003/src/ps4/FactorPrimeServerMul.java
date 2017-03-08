package ps4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by eiros_000 on 27/2/2017.
 */
public class FactorPrimeServerMul {
    public static void main(String[] args) {

        BigInteger semiPrime = new BigInteger("93");

        // Generate list of primes from 2 to root of input
        ArrayList<BigInteger> listOfPrimes = new ArrayList<>();
        BigInteger root = sqrt(semiPrime);
        BigInteger nextPrime = new BigInteger("2");
        while (nextPrime.compareTo(root)<=0) {
            listOfPrimes.add(nextPrime);
            nextPrime = nextPrime.nextProbablePrime();
        }

        try {
            ServerSocket serverSocket = new ServerSocket(1212);
            serverSocket.setSoTimeout(5000);

            ArrayList<Socket> socketList = new ArrayList<>();
            ArrayList<ObjectOutputStream> outList = new ArrayList<>();
            ArrayList<ObjectInputStream> inList = new ArrayList<>();

            System.out.println("... Currently connecting ...");

            int noOfClients = 0;
            while (true){
                try {
                    Socket newClient = serverSocket.accept();
                    noOfClients++;
                    socketList.add(newClient);
                    outList.add(new ObjectOutputStream(newClient.getOutputStream()));
                    inList.add(new ObjectInputStream(newClient.getInputStream()));
                    System.out.println("... Client " + (noOfClients) + " has connected ...");
                } catch (SocketTimeoutException e) {
                    System.out.println("Timed out");
                    break;
                }
            }

            System.out.println("Finding the prime factors of " + semiPrime.toString() + " using " + noOfClients + " clients");


            int testSize = (int) Math.ceil(((double) listOfPrimes.size())/noOfClients);

            for (int i=0; i<noOfClients; i++) {
                int startIndex = i*testSize;
                int endIndex = startIndex + testSize;
                if (endIndex > listOfPrimes.size()) {
                    endIndex = listOfPrimes.size();
                }
                ArrayList<BigInteger> designatedPrimes = new ArrayList<>(listOfPrimes.subList(startIndex,endIndex));
                outList.get(i).writeObject(semiPrime);
                outList.get(i).writeObject(designatedPrimes);
            }

            ArrayList<BigInteger> finalFactors = new ArrayList<>();
            finalFactors.add(BigInteger.ONE);
            finalFactors.add(semiPrime);

            for (int i=0; i<noOfClients; i++) {
                ArrayList<BigInteger> result = (ArrayList<BigInteger>) inList.get(i).readObject();
                if (result.size()!=1) {
                    finalFactors = result;
                }
            }

            System.out.print("Factors of the semiprime are " + finalFactors.get(0) + " and " + finalFactors.get(1));

            for (int i=0; i<noOfClients; i++) {
                inList.get(i).close();
                outList.get(i).close();
                socketList.get(i).close();
            }

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<BigInteger> findFactor(BigInteger input) {
        ArrayList<BigInteger> primeFactors = new ArrayList<>();
        BigInteger tempInput = input;

        // if input is less than 2
        if (tempInput.compareTo(BigInteger.valueOf(2))<0) {
            return primeFactors;
        }
        BigInteger approxRoot = sqrt(tempInput);
        BigInteger currentPrime = BigInteger.valueOf(2);

        // checking all prime from 2 to root of input
        while (currentPrime.compareTo(approxRoot)<=0) {
            if (currentPrime.pow(2).compareTo(input)>0) { break; }
            while (tempInput.mod(currentPrime).compareTo(BigInteger.ZERO)==0) {
                primeFactors.add(currentPrime);
                tempInput = tempInput.divide(currentPrime);
            }
            currentPrime = currentPrime.nextProbablePrime();
        }

        if (tempInput.compareTo(BigInteger.ONE)>0) {
            primeFactors.add(tempInput);
        }
        return primeFactors;
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

}