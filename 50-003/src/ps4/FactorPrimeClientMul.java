package ps4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 * Created by eiros_000 on 27/2/2017.
 */
public class FactorPrimeClientMul {
    public static void main(String[] args) {
        try {
            String hostName = "localhost";
            int portNumber = 1212;

            Socket socket = new Socket();
            SocketAddress sockAddr = new InetSocketAddress(hostName,portNumber);
            socket.connect(sockAddr, 100);

            System.out.println("Connected");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            BigInteger semiPrime = (BigInteger) in.readObject();
            ArrayList<BigInteger> designatedPrimes = (ArrayList<BigInteger>) in.readObject();

            ArrayList<BigInteger> result = findFactor(semiPrime,designatedPrimes);
            out.writeObject(result);

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