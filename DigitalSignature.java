import java.security.*;

public class DigitalSignature {
    static byte[] sign(byte[] data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data);

            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
