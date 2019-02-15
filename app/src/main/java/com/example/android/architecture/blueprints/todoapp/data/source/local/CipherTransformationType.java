package com.example.android.architecture.blueprints.todoapp.data.source.local;

public class CipherTransformationType {
    private static final String _ = "/";

    public static final String AES_CBC_NoPadding = CipherAlgorithmType.AES + _ + CipherModeType.CBC + _ + CipherPaddingType.NoPadding;
    public static final String AES_CBC_PKCS5Padding = CipherAlgorithmType.AES + _ + CipherModeType.CBC + _ + CipherPaddingType.PKCS5Padding;
    public static final String AES_ECB_NoPadding = CipherAlgorithmType.AES + _ + CipherModeType.ECB + _ + CipherPaddingType.NoPadding;
    public static final String AES_ECB_PKCS5Padding = CipherAlgorithmType.AES + _ + CipherModeType.ECB + _ + CipherPaddingType.PKCS5Padding;

    public static final String DES_CBC_NoPadding = CipherAlgorithmType.DES + _ + CipherModeType.CBC + _ + CipherPaddingType.NoPadding;
    public static final String DES_CBC_PKCS5Padding = CipherAlgorithmType.DES + _ + CipherModeType.CBC + _ + CipherPaddingType.PKCS5Padding;
    public static final String DES_ECB_NoPadding = CipherAlgorithmType.DES + _ + CipherModeType.ECB + _ + CipherPaddingType.NoPadding;
    public static final String DES_ECB_PKCS5Padding = CipherAlgorithmType.DES + _ + CipherModeType.ECB + _ + CipherPaddingType.PKCS5Padding;

    public static final String DESede_CBC_NoPadding = CipherAlgorithmType.DESede + _ + CipherModeType.CBC + _ + CipherPaddingType.NoPadding;
    public static final String DESede_CBC_PKCS5Padding = CipherAlgorithmType.DESede + _ + CipherModeType.CBC + _ + CipherPaddingType.PKCS5Padding;
    public static final String DESede_ECB_NoPadding = CipherAlgorithmType.DESede + _ + CipherModeType.ECB + _ + CipherPaddingType.NoPadding;
    public static final String DESede_ECB_PKCS5Padding = CipherAlgorithmType.DESede + _ + CipherModeType.ECB + _ + CipherPaddingType.PKCS5Padding;

    public static final String RSA_ECB_PKCS1Padding = CipherAlgorithmType.RSA + _ + CipherModeType.ECB + _ + CipherPaddingType.PKCS1Padding;
    public static final String RSA_ECB_OAEPWithSHA_1AndMGF1Padding = CipherAlgorithmType.RSA + _ + CipherModeType.ECB + _ + CipherPaddingType.OAEPWithSHA_1AndMGF1Padding;
    public static final String RSA_ECB_OAEPWithSHA_256AndMGF1Padding = CipherAlgorithmType.RSA + _ + CipherModeType.ECB + _ + CipherPaddingType.OAEPWithSHA_256AndMGF1Padding;

}
