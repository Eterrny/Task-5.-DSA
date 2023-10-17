import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SignatureException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DSAServiceTest {
    @Test
    public void unitTest1() {
        java.security.SignatureException thrown = assertThrows(
                java.security.SignatureException.class,
                () -> new DSAService(100, "test"),
                "Ожидалось исключение в new DSAService(), но его не было."
        );
    }

    @Test
    public void unitTest2() throws SignatureException {
        DSAService service = new DSAService(1024, "test");
        Alice alice = service.getAlice();
        Assertions.assertEquals(alice.getR(), service.getV());
    }
}