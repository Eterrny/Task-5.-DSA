import java.security.SignatureException;

public class DSA {
    public static void main(String[] args) throws SignatureException {
        if (args.length == 0) {
            System.out.println("Входные параметры отсутсвуют");
            return;
        }
        if (args[0].equals("/help") || args[0].equals("h")) {
            System.out.println("""
                    Программе должны передаваться следующие параметры:
                    \t- длина в битах числа p от 512 до 1024 (кратно 64)
                    \t- сообщение Алисы""");
            return;
        }
        if (args.length < 2){
            System.out.println("Передано некорректное число параметров.");
            return;
        }
        int pSizeInBits;
        String message;
        try {
            pSizeInBits = Integer.parseInt(args[0]);
            if (pSizeInBits < 512 || pSizeInBits > 1024){
                System.out.println("Длина числа p в битах должна быть в промежутке от 512 до 1024.");
                throw new IllegalArgumentException();
            }
            if (pSizeInBits % 64 != 0){
                System.out.println("Длина числа p в битах должна быть кратна 64");
                throw new IllegalArgumentException();
            }
            message = args[1];
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в чтении входных параметров.");
            return;
        }
        DSAService service = new DSAService(pSizeInBits, message);
    }
}