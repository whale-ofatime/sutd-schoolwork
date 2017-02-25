package ps4;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by eiros_000 on 20/2/2017.
 */
public class FactorPrime {
    public static void main(String[] args) {
        BigInteger testVal = new BigInteger("94");
        ArrayList<BigInteger> primeFactors = findFactor(testVal);
        for (BigInteger factors:primeFactors) {
            System.out.println(factors.toString());
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
