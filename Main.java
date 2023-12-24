import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Blockchain blockchain = new Blockchain();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBLOCKCHAIN APP MENU:");
            System.out.println("1. Perform Transaction");
            System.out.println("2. Show Blockchain");
            System.out.println("3. Show Merkle Root");
            System.out.println("4. Mine Block");
            System.out.println("5. Generate Key Pairs");
            System.out.println("6. EXIT");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewBlockchain();
                    break;
                case 3:
                    viewMerkleRoot();
                    break;
                case 4:
                    mineBlock();
                    break;
                case 5:
                    generateKeys();
                    break;
                case 6:
                    System.out.println("Exiting. . . ");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void generateKeys() {
        KeyPair senderKeyPair = KeyUtil.generateKeyPair();
        PublicKey senderGeneratedPublicKey = senderKeyPair.getPublic();
        PrivateKey senderGeneratedPrivateKey = senderKeyPair.getPrivate();

        KeyPair recipientKeyPair = KeyUtil.generateKeyPair();
        PublicKey recipientGeneratedPublicKey = recipientKeyPair.getPublic();
        PrivateKey recipientGeneratedPrivateKey = recipientKeyPair.getPrivate();

        String senderGeneratedPublicKeyBase64 = KeyUtil.getBase64PublicKey(senderGeneratedPublicKey);
        String senderGeneratedPrivateKeyBase64 = KeyUtil.getBase64KeyPrivateKey(senderGeneratedPrivateKey);

        String recipientGeneratedPublicKeyBase64 = KeyUtil.getBase64PublicKey(recipientGeneratedPublicKey);
        String recipientGeneratedPrivateKeyBase64 = KeyUtil.getBase64KeyPrivateKey(recipientGeneratedPrivateKey);

        System.out.println("Sender's generated Public Key: " + senderGeneratedPublicKeyBase64);
        System.out.println("Sender's generated Private Key: " + senderGeneratedPrivateKeyBase64);
        System.out.println("Recipient's generated Public Key: " + recipientGeneratedPublicKeyBase64);
        System.out.println("Recipient's generated Private Key: " + recipientGeneratedPrivateKeyBase64);
    }

    private static void mineBlock() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter miner's public key: ");
        String minerPublicKey = scanner.nextLine();

        System.out.println("Mining process. . .");

        blockchain.mineBlock(minerPublicKey);

        System.out.println("Mining successful! Block added with reward for the miner!");
    }

    private static void viewMerkleRoot() {
        System.out.println("\nMerkle Root: " + blockchain.getMerkleRoot());
    }

    private static void viewBlockchain() {
        System.out.println("\nBlockchain: ");
        for (Block block : blockchain.getChain()) {
            System.out.println("Hash: " + block.getHash());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Transaction: " + block.getTransactions());
            System.out.println();
        }
    }

    private static void addTransaction() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter sender's public key (in Base64 format): ");
        String senderPublicKeyBase64 = scanner.nextLine();
        PublicKey senderPublicKey = KeyUtil.getPublicKeyFromBase64(senderPublicKeyBase64);

        System.out.print("Enter recipient's public key (in Base64 format): ");
        String recipientPublicKeyBase64 = scanner.nextLine();
        PublicKey recipientPublicKey = KeyUtil.getPublicKeyFromBase64(recipientPublicKeyBase64);

        System.out.print("Enter sender's private key (in Base64 format): ");
        String senderPrivateKeyBase64 = scanner.nextLine();
        PrivateKey senderPrivateKey = KeyUtil.getPrivateKeyFromBase64(senderPrivateKeyBase64);

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        System.out.println("Transaction processing. . .");

        //Asymmetric Encryption Implementation
        byte[] encryptedTransaction = AsymmetricEncryption.encrypt(
                String.valueOf(amount).getBytes(),
                recipientPublicKey
        );

        //Digital Signature Implementation
        byte[] signature = DigitalSignature.sign(
                encryptedTransaction,
                senderPrivateKey
        );

        // Hash the original transaction string
        String transactionString = senderPublicKeyBase64 + recipientPublicKeyBase64 + String.valueOf(amount);
        String hashedTransaction = hashString(transactionString);

        //for transmission purposes
        String encodedEncryptedTransaction = Base64.getEncoder().encodeToString(encryptedTransaction);
        String encodedSignature = Base64.getEncoder().encodeToString(signature);


        List<String> transactions = new ArrayList<>();
        transactions.add(encodedEncryptedTransaction);
        transactions.add(encodedSignature);
        transactions.add(hashedTransaction);
        blockchain.addBlock(transactions);

        System.out.println("Transaction successful!");
    }

    private static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                hexString.append(String.format("%02X", hashByte));
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}