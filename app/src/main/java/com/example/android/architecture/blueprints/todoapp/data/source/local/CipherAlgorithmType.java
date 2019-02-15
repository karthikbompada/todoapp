package com.example.android.architecture.blueprints.todoapp.data.source.local;

public enum CipherAlgorithmType {
    /**
     * Advanced Encryption Standard as specified by NIST in FIPS 197. Also known
     * as the Rijndael algorithm by Joan Daemen and Vincent Rijmen, AES is a
     * 128-bit block cipher supporting keys of 128, 192, and 256 bits.
     */
    AES("AES"),

    /**
     * The Digital Encryption Standard.
     */
    DES("DES"),

    /**
     * Triple DES Encryption (also known as DES-EDE, 3DES, or Triple-DES). Data
     * is encrypted using the DES algorithm three separate times. It is first
     * encrypted using the first subkey, then decrypted with the second subkey,
     * and encrypted with the third subkey.
     */
    DESede("DESede"),

    /**
     * The RSA encryption algorithm
     */
    RSA("RSA");

    private String mName;

    private CipherAlgorithmType(String name) {
        mName = name;
    }

    /**
     * Get the algorithm name of the enum value.
     *
     * @return The algorithm name
     */
    public String getAlgorithmName() {
        return mName;
    }
}
