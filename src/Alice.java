import java.math.BigInteger;
import java.security.SecureRandom;

public class Alice {
    private final BigInteger k, r, s;
    private BigInteger x;

    public Alice(DSAService dsa) {
        SecureRandom rnd = new SecureRandom();
        this.x = new BigInteger(dsa.getQ().bitLength(), rnd).mod(dsa.getQ());
        this.x = x.multiply(BigInteger.ONE.shiftLeft(160 - x.bitLength()));
        this.k = new BigInteger(dsa.getQ().bitLength(), rnd).mod(dsa.getQ());
        this.r = dsa.getG().modPow(k, dsa.getP()).mod(dsa.getQ());
        this.s = (k.modInverse(dsa.getQ())
                .multiply(SHA.encryptMessage(dsa.getMessage()).add(x.multiply(r))))
                .mod(dsa.getQ());
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getK() {
        return k;
    }

    public BigInteger getR() {
        return r;
    }

    public BigInteger getS() {
        return s;
    }
}
