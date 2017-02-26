package ps4;

import java.io.*;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 * Created by user on 26/2/2017.
 */
public class FactorPrimeClient {
    public static void main(String[] args) {
        try {
            String hostName = "localhost";
            int portNumber = 1234;

            Socket socket = new Socket();
            SocketAddress sockAddr = new InetSocketAddress(hostName,portNumber);
            socket.connect(sockAddr, 100);

            System.out.println("Connected");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());


            BigInteger output = BigInteger.ZERO;

            BigInteger semiPrime = (BigInteger) in.readObject();
            System.out.println(semiPrime);

            ArrayList<BigInteger> designatedPrimes = (ArrayList<BigInteger>) in.readObject();
            for (BigInteger val:designatedPrimes) {
                System.out.println(val.toString());
                output = output.add(val);
            }
            System.out.println(output.toString());

            System.out.println("Primes");
            ArrayList<BigInteger> result = findFactor(semiPrime,designatedPrimes);
            out.writeObject(result);

            for (BigInteger val:result) {
                System.out.println(val.toString());
            }

//            out.writeObject(output);
//            out.flush();

            socket.close();
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

//        // checking all prime from 2 to root of input
//        while (currentPrime.compareTo(approxRoot)<=0) {
//            if (currentPrime.pow(2).compareTo(input)>0) { break; }
//            while (tempInput.mod(currentPrime).compareTo(BigInteger.ZERO)==0) {
//                primeFactors.add(currentPrime);
//                tempInput = tempInput.divide(currentPrime);
//            }
//            currentPrime = currentPrime.nextProbablePrime();
//        }

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
