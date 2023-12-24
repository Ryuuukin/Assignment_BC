This is a simple representation of Blockchain Technology Functionality with fundamental concepts, like:
Asymmetric Encryption, Digital Signature, Hashing, Blockchain Core Features (adding blocks, adding transactions, implementation of a Merkle Tree)

[Main.java]
The main menu-driven class that allows users to interact with the blockchain.
Options include:
> performing transactions

> viewing the blockchain

> showing the Merkle root

> mining a new block

> generating key pairs

> exiting the application.

[AsymmetricEncryption.java]
Implements asymmetric encryption using the RSA algorithm for secure transaction processing.

[DigitalSignature.java]
Provides digital signature functionality using the SHA-256 with RSA algorithm to ensure the integrity and authenticity of transactions.

[KeyUtil.java]
Manages key generation, conversion between key formats and Base64 encoding/decoding.

[Blockchain.java]
Defines the blockchain structure, containing a list of blocks.
Implements methods: 
> adding blocks

> creating a Merkle tree

> calculating the Merkle root

> mining new blocks.

[Block.java]
Represents a block in the blockchain
BLOCK
- timestamp
- previous hash
- list of transactions
- block's hash.
  
Note: Utilizes SHA-256 for hash calculation.

