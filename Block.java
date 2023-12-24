import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Block {
    private long timestamp;
    private String previousHash;
    private List<String> transactions;
    private String hash;

    private static final String MINER_REWARD = "Mining Reward";

    Block(String previousHash, List<String> transactions) {
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        this.transactions = new ArrayList<>(transactions);
        this.hash = calculateHash();
        transactions.add(MINER_REWARD);
    }

    private String calculateHash() {
        String data = timestamp + previousHash + transactions.toString();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte hashByte : hashBytes) {
                hexString.append(String.format("%02x", hashByte));
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    long getTimestamp() {
        return timestamp;
    }

    String getPreviousHash() {
        return previousHash;
    }

    List<String> getTransactions() {
        return transactions;
    }

    String getHash() {
        return hash;
    }

}
