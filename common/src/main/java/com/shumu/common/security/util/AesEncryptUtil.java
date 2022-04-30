package com.shumu.common.security.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description: 对称加密AES
 * @Author: Li
 * @Date: 2022-01-13
 * @LastEditTime: 2022-01-13
 * @LastEditors: Li
 */
public class AesEncryptUtil {
    /** AES的NoPadding模式加密的key和data的byte字节数必须为16的倍数 */
    private static String KEY = "ABCDEF0123456789";
    /** 偏移量字符串必须是16位 */
    private static String IV = "ABCDEF0123456789";

    /**
     * 加密
     * 
     * @param data
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            // " 算法/模式/补码方式NoPadding/PKCS5Paddin "
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            // 输入大小（字节），每一块的大小
            int blockSize = cipher.getBlockSize();
            // 明文字符串变成字节数组然后用blockSize分块
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            // 加密一个很长的明文时如果不采用分块加密就会报错
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            // 拷贝明文数组到分好块的数组中
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            // 以独立于提供者的方式指定密钥
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            // 指定初始化向量 (IV)
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            // 使用密钥和一组算法参数初始化此密码
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            // 在单部分操作中加密
            byte[] encrypted = cipher.doFinal(plaintext);
            // Base64编码，如此密文无乱码，长度16位
            String encryptedStr = Base64.getEncoder().encodeToString(encrypted);
            // 返回密文
            return encryptedStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * 
     * @param data
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            
            byte[] encrypted1 = Base64.getDecoder().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 默认加密
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    /**
     * 默认解密
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
    }
}
