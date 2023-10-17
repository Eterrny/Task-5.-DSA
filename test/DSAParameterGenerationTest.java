import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class DSAParameterGenerationTest {

    @Test
    void generatePAndQ() {
        BigInteger[] pq = DSAParameterGeneration.generatePAndQ(512, 160);
        assertTrue(pq.length == 2
                && pq[0].isProbablePrime(50)
                && pq[1].isProbablePrime(50)
                && (pq[0].subtract(BigInteger.ONE)).mod(pq[1]).equals(BigInteger.ZERO));
    }

    @Test
    void findH() {
        BigInteger[] pq = DSAParameterGeneration.generatePAndQ(512, 160);
        BigInteger[] gh = DSAParameterGeneration.findH(pq[0], pq[1]);
        assertTrue(gh.length == 2 && gh[0].compareTo(BigInteger.ONE) > 0);
    }
}