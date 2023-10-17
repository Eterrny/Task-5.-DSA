import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class SHATest {

    @Test
    void encryptMessage() {
        BigInteger hash1 = SHA.encryptMessage("test");
        BigInteger hash2 = SHA.encryptMessage("test test");
        assertTrue(hash1.bitLength() == 160 && hash2.bitLength() == 160 && !hash1.equals(hash2));
    }
}