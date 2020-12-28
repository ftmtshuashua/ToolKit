package com.acap.toolkit.codec;

import com.acap.toolkit.transform.BytesUtils;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <pre>
 * Tip:
 *      DES算法
 *
 * Created by ACap on 2020/12/22 13:50
 * </pre>
 */
public class DESUtils {
    ///////////////////////////////////////////////////////////////////////////
    // DES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the Base64-encode bytes of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the Base64-encode bytes of DES encryption
     */
    public static byte[] encryptDES2Base64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return Base64Utils.base64Encode(encryptDES(data, key, transformation, iv));
    }

    /**
     * Return the hex string of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the hex string of DES encryption
     */
    public static String encryptDES2HexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return BytesUtils.bytes2HexString(encryptDES(data, key, transformation, iv));
    }

    /**
     * Return the bytes of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES encryption
     */
    public static byte[] encryptDES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "DES", transformation, iv, true);
    }

    /**
     * Return the bytes of DES decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption for Base64-encode bytes
     */
    public static byte[] decryptBase64DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return decryptDES(Base64Utils.base64Decode(data), key, transformation, iv);
    }

    /**
     * Return the bytes of DES decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption for hex string
     */
    public static byte[] decryptHexStringDES(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decryptDES(BytesUtils.hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * Return the bytes of DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption
     */
    public static byte[] decryptDES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "DES", transformation, iv, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 3DES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the Base64-encode bytes of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the Base64-encode bytes of 3DES encryption
     */
    public static byte[] encrypt3DES2Base64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return Base64Utils.base64Encode(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * Return the hex string of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the hex string of 3DES encryption
     */
    public static String encrypt3DES2HexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return BytesUtils.bytes2HexString(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * Return the bytes of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES encryption
     */
    public static byte[] encrypt3DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "DESede", transformation, iv, true);
    }

    /**
     * Return the bytes of 3DES decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption for Base64-encode bytes
     */
    public static byte[] decryptBase64_3DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return decrypt3DES(Base64Utils.base64Decode(data), key, transformation, iv);
    }

    /**
     * Return the bytes of 3DES decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption for hex string
     */
    public static byte[] decryptHexString3DES(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decrypt3DES(BytesUtils.hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * Return the bytes of 3DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption
     */
    public static byte[] decrypt3DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "DESede", transformation, iv, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // AES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the Base64-encode bytes of AES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the Base64-encode bytes of AES encryption
     */
    public static byte[] encryptAES2Base64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return Base64Utils.base64Encode(encryptAES(data, key, transformation, iv));
    }

    /**
     * Return the hex string of AES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the hex string of AES encryption
     */
    public static String encryptAES2HexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return BytesUtils.bytes2HexString(encryptAES(data, key, transformation, iv));
    }

    /**
     * Return the bytes of AES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of AES encryption
     */
    public static byte[] encryptAES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "AES", transformation, iv, true);
    }

    /**
     * Return the bytes of AES decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of AES decryption for Base64-encode bytes
     */
    public static byte[] decryptBase64AES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return decryptAES(Base64Utils.base64Decode(data), key, transformation, iv);
    }

    /**
     * Return the bytes of AES decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of AES decryption for hex string
     */
    public static byte[] decryptHexStringAES(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decryptAES(BytesUtils.hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * Return the bytes of AES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of AES decryption
     */
    public static byte[] decryptAES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "AES", transformation, iv, false);
    }


    /**
     * Return the bytes of symmetric encryption or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param algorithm      The name of algorithm.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of symmetric encryption or decryption
     */
    private static byte[] symmetricTemplate(final byte[] data, final byte[] key, final String algorithm, final String transformation, final byte[] iv, final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
//        try {
//
//            DESedeKeySpec spec = new DESedeKeySpec(key);
//            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(algorithm);
//
//            Key deskey = keyfactory.generateSecret(spec);
//
//
//            Cipher cipher = Cipher.getInstance(transformation);
//            if (iv == null || iv.length == 0) {
//                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
//                        deskey);
//            } else {
//                AlgorithmParameterSpec ips = new IvParameterSpec(iv);
//                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
//                        deskey,
//                        ips);
//            }
//            return cipher.doFinal(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;

        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0) {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec);
            } else {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, params);
            }
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}
