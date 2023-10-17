import java.math.BigInteger;
import java.security.SignatureException;

public class DSAService {
    private final BigInteger p, q, g, h, y;
    private BigInteger v;
    private final String message;
    Alice alice;

    public DSAService(int pSizeInBits, String message) throws SignatureException {
        this.message = message;
        BigInteger[] pq = DSAParameterGeneration.generatePAndQ(pSizeInBits, 160);
        this.p = pq[0];
        this.q = pq[1];
        BigInteger[] gh = DSAParameterGeneration.findH(p, q);
        this.g = gh[0];
        this.h = gh[1];
        alice = new Alice(this);
        this.y = g.modPow(alice.getX(), p);
        System.out.println(this);
        this.step1();
    }

    @Override
    public String toString() {
        return String.format("""
                Параметры алгоритма DSA:
                \tp = %d
                \tq = %d
                \th = %d
                \tg = %d
                \tx = %d
                \ty = %d\n""", p, q, h, g, alice.getX(), y);
    }

    private void step1() throws SignatureException {
        System.out.printf("""
                \n--- Шаг 1 ---
                Алиса хочет подписать следующее сообщение: %s
                Она генерирует число k = %d\n""", message, alice.getK());
        this.step2();
    }

    private void step2() throws SignatureException {
        System.out.printf("""
                \n--- Шаг 2 ---
                Алиса генерирует подпись:
                   r = %d
                   s = %d
                Затем посылает ее Бобу\n""", alice.getR(), alice.getS());
        this.step3(alice.getR(), alice.getS());
    }

    private void step3(BigInteger r, BigInteger s) throws SignatureException {
        BigInteger w = s.modInverse(q);
        BigInteger u1 = (SHA.encryptMessage(message).multiply(w)).mod(q);
        BigInteger u2 = r.multiply(w).mod(q);
        v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);
        System.out.printf("""
                \n--- Шаг 3 ---
                Боб проверяет подпись, вычисляя:
                   w = %d
                   u1 = %d
                   u2 = %d
                   v = %d\n""", w, u1, u2, v);
        if (!v.equals(r)) {
            throw new SignatureException("Подпись не прошла проверку!\nv = " + v + "\nr = " + r);
        }
        System.out.println("Подпись прошла проверку.");
    }


    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getG() {
        return g;
    }

    public String getMessage() {
        return message;
    }

    public Alice getAlice() {
        return alice;
    }

    public BigInteger getV() {
        return v;
    }
}
