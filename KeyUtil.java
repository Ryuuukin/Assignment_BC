import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {

    static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating a key pair", e);
        }
    }

    static String getBase64PublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    static String getBase64KeyPrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    static KeyPair getPairKeyFromBase64Strings(String base64PublicKey, String base64PrivateKey) {
        PublicKey publicKey = getPublicKeyFromBase64(base64PublicKey);
        PrivateKey privateKey = getPrivateKeyFromBase64(base64PrivateKey);
        return new KeyPair(publicKey, privateKey);
    }

    static PublicKey getPublicKeyFromBase64(String base64PublicKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            System.err.println("Error converting public key from Base64:");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    static PrivateKey getPrivateKeyFromBase64(String base64PrivateKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            System.err.println("Error converting private key from Base64:");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
