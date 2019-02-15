package com.example.android.architecture.blueprints.todoapp.data.source.local;

public enum CipherModeType {
    /**
     * Cipher Block Chaining Mode
     */
    CBC("CBC"),

    /**
     * Electronic Codebook Mode
     */
    ECB("ECB");

    private String mName;

    private CipherModeType(String name) {
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
