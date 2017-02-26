package ps4;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by eiros_000 on 23/2/2017.
 */
public class FactorPrimeServer {
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

        for (BigInteger prime:listOfPrimes) {
            System.out.println(prime.toString());
        }

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            int noOfClients = 2;
//
            ArrayList<Socket> socketList = new ArrayList<>();

            // Start
            ArrayList<ObjectOutputStream> outList = new ArrayList<>();
            ArrayList<ObjectInputStream> inList = new ArrayList<>();

            System.out.println("... Currently connecting ...");
            for (int i=0; i<noOfClients; i++) {
                Socket newClient = serverSocket.accept();
                System.out.println("... Connected ...");

                socketList.add(newClient);
                outList.add(new ObjectOutputStream(newClient.getOutputStream()));
                inList.add(new ObjectInputStream(newClient.getInputStream()));
            }


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


//            ArrayList<BigInteger> test = new ArrayList<>();
//            test.add(new BigInteger("5"));
//            test.add(new BigInteger("10"));
//
//            for (int i=0; i<noOfClients; i++) {
//                outList.get(i).writeObject(test);
//            }

            ArrayList<BigInteger> finalFactors = new ArrayList<>();
            finalFactors.add(BigInteger.ONE);
            finalFactors.add(semiPrime);

            for (int i=0; i<noOfClients; i++) {
                ArrayList<BigInteger> result = (ArrayList<BigInteger>) inList.get(i).readObject();
                if (result.size()!=1) {
                    finalFactors = result;
                }
//                System.out.println(i);
//                for (BigInteger val:result) {
//                    System.out.println(val);
//                }
            }

            System.out.print("Factors of the semiprime are " + finalFactors.get(0) + " and " + finalFactors.get(1));

            for (int i=0; i<noOfClients; i++) {
                inList.get(i).close();
                outList.get(i).close();
                socketList.get(i).close();
            }



            serverSocket.close();
            // End


            /*
            // Start of code, don't delete this yet
            Socket newSocket = serverSocket.accept();
            ObjectOutputStream out = new ObjectOutputStream(newSocket.getOutputStream());

            ArrayList<String> test = new ArrayList<>();
            test.add("hello1");
            test.add("bye2");

            out.writeObject(test);
            out.close();
            newSocket.close();
            serverSocket.close();
            // End
            */

//            for (int i=0; i<noOfClients; i++) {
//                outList.get(i).writeObject(test);
//            }

//            for (int i=0; i<noOfClients; i++) {
//                socketList.get(i).close();
//                outList.get(i).close();
//                inList.get(i).close();
//            }

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
