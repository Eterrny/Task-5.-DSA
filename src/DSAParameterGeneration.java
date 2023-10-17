import java.math.BigInteger;
import java.security.SecureRandom;


public class DSAParameterGeneration {
    public static BigInteger[] generatePAndQ(int pSizeInBits, int qSizeInBits) {
        SecureRandom random = new SecureRandom();
        BigInteger q = BigInteger.probablePrime(qSizeInBits, random);
        BigInteger k = BigInteger.ONE;
        int diff = pSizeInBits - qSizeInBits;
        if (diff > 0) {
            k = BigInteger.ONE.shiftLeft(pSizeInBits - qSizeInBits);
        }
        BigInteger p = q.multiply(k).add(BigInteger.ONE);
        if (diff > 0) {
            while (!p.isProbablePrime(100)) {
                q = BigInteger.probablePrime(qSizeInBits, random);
                p = q.multiply(k).add(BigInteger.ONE);
            }
        }
        return new BigInteger[]{p, q};
    }

    static BigInteger[] findH(BigInteger p, BigInteger q) {
        SecureRandom rnd = new SecureRandom();
        BigInteger h = new BigInteger("2");
        BigInteger g = h.modPow((p.subtract(BigInteger.ONE)).divide(q), p);
        while (g.compareTo(BigInteger.ONE) <= 0) {
            h = h.add(BigInteger.ONE).mod(p);
            g = h.modPow((p.subtract(BigInteger.ONE)).divide(q), p);
        }
        return new BigInteger[]{g, h};
    }
}
