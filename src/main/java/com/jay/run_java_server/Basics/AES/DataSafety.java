package com.jay.run_java_server.Basics.AES;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class DataSafety {

    public enum TokenTypes {
        NONE, ONLY_FOR_GET_API_KEY,
        API_TOKEN, SESSION_TOKEN, USER_TOKEN,
        PAYLOAD
    }

    // KeyIv class to hold the constants
    public static class KeyIv {
        private final String name;
        private final String key;
        private final String iv;
        private final String algorithm;

        public KeyIv(String name, String key, String iv, String algorithm) {
            this.name = name;
            this.key = key;
            this.iv = iv;
            this.algorithm = algorithm;
        }

        public String getName() {
            return name;
        }

        public String getKey() {
            return key;
        }

        public String getIv() {
            return iv;
        }

        public String getAlgorithm() {
            return algorithm;
        }
    }

    public class KeysAndIvs {

        // Centralized storage for encryption keys and IVs
        private static final Map<TokenTypes, KeyIv> keyIvMap = new HashMap<>();

        static {
            keyIvMap.put(TokenTypes.ONLY_FOR_GET_API_KEY,
                    new KeyIv("NILL", "mskhrv25smr40dbk3sijdn3jnd632nds", "ndft3sj320tgnm32",
                            "AES/CBC/PKCS5Padding"));
            keyIvMap.put(TokenTypes.API_TOKEN,
                    new KeyIv("apitoken", "64gf25amsk62389756jkeqczmt846gw9", "m6765dfdf6g6dbfn",
                            "AES/CBC/PKCS5Padding"));
            keyIvMap.put(TokenTypes.SESSION_TOKEN,
                    new KeyIv("sessiontoken", "nqwerog5904mwefceg0438mdg23kmsm1", "abc456hjbow98n5t",
                            "AES/CBC/PKCS5Padding"));
            keyIvMap.put(TokenTypes.USER_TOKEN,
                    new KeyIv("usertoken", "msdtqportndvskfgb634nsd37ndgfn39", "b43uhf873d937gne",
                            "AES/CBC/PKCS5Padding"));
            keyIvMap.put(TokenTypes.PAYLOAD,
                    new KeyIv("payload", "nuebfuewr63nkefw032njdsbfjj23uub", "midfgngr743brhsd",
                            "AES/CBC/PKCS5Padding"));
        }

        // Factory method to get the KeyIv based on the level
        public static KeyIv getKeyIvOf(TokenTypes level) {
            KeyIv keyIv = keyIvMap.get(level);
            if (keyIv == null) {
                throw new IllegalArgumentException("Invalid Level");
            }
            return keyIv;
        }

        public static String getNameOf(TokenTypes level) {
            KeyIv keyIv = keyIvMap.get(level);
            if (keyIv == null) {
                throw new IllegalArgumentException("Invalid Level");
            }
            return keyIv.getName();
        }

    }

    // Encrypt a plaintext string
    public static String encrypt(String plaintext, TokenTypes encryptionLevel) {
        if (encryptionLevel == TokenTypes.NONE) {
            return plaintext;
        }

        KeyIv keyIv = KeysAndIvs.getKeyIvOf(encryptionLevel);

        try {
            Cipher cipher = Cipher.getInstance(keyIv.getAlgorithm());
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyIv.getKey().getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(keyIv.getIv().getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return null;
        }

    }

    // Decrypt an encrypted string
    public static String decrypt(String encryptedText, TokenTypes encryptionLevel) {
        if (encryptionLevel == TokenTypes.NONE) {
            return encryptedText;
        }

        KeyIv keyIv = KeysAndIvs.getKeyIvOf(encryptionLevel);

        try {
            Cipher cipher = Cipher.getInstance(keyIv.getAlgorithm());
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyIv.getKey().getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(keyIv.getIv().getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            return null;
        }

    }
}
