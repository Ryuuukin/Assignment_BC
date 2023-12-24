import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    Blockchain() {
        this.chain = new ArrayList<>();
        this.chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        List<String> transactions  = new ArrayList<>();
        transactions.add("Genesis Transaction");
        return new Block("0", transactions);
    }

    void addBlock(List<String> transactions) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getHash(), transactions);
        chain.add(newBlock);
    }

    void mineBlock(String minerPublicKey) {
        List<String> transactions = new ArrayList<>();
        transactions.add("Reward for " + minerPublicKey);
        addBlock(transactions);
    }
    List<Block> getChain() {
        return chain;
    }

    String getMerkleRoot() {
        List<String> transactions = new ArrayList<>();

        for (Block block : chain) {
            transactions.addAll(block.getTransactions());
        }

        return calculateMerkleRoot(transactions);
    }

    private String calculateMerkleRoot(List<String> transactions) {
        List<String> merkleTree = buildMerkleTree(transactions);
        return merkleTree.get(0);
    }

    private List<String> buildMerkleTree(List<String> transactions) {
        List<String> merkleTree = new ArrayList<>(transactions);

        while (merkleTree.size() > 1) {
            List<String> newMerkleTree = new ArrayList<>();

            for (int i = 0; i < merkleTree.size() - 1; i += 2) {
                String concatenatedHashes = merkleTree.get(i) + merkleTree.get(i + 1);
                String newHash = hashString(concatenatedHashes);
                merkleTree.add(newHash);
            }

            if (merkleTree.size() % 2 == 1) {
                newMerkleTree.add(merkleTree.get(merkleTree.size() - 1));
            }

            merkleTree = newMerkleTree;
        }

        return merkleTree;
    }

    private String hashString(String input) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte hashByte : hashBytes) {
                hexString.append(String.format("%02x", hashByte));
            }
            return hexString.toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
