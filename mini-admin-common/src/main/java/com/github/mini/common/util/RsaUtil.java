package com.github.mini.common.util;

import com.github.mini.common.constant.MiniAdminConstant;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @className: RsaUtil
 * @description: RSA 工具类
 * @author: HanSon.Q
 * @version: V1.0
 * @date: 2020/12/3
 */
@Slf4j
public class RsaUtil {
    private static final int DEFAULT_KEY_SIZE = 2048;
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 1013;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 1014;


    /**
     * 获取密钥对
     *
     * @return java.security.KeyPair
     */
    public static KeyPair getKeyPair(String secret,
                                     int keySize) throws Exception {
        KeyPairGenerator generator =
                KeyPairGenerator.getInstance(MiniAdminConstant.ALGORITHM_NAME);
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        generator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        return generator.generateKeyPair();
    }


    public static void generateKeyPair(String publicKeyFilename,
                                       String privateKeyFilename,
                                       String secret,
                                       int keySize) throws Exception {
        KeyPair keyPair = getKeyPair(secret, keySize);
        // 公钥
        String publicKey = new String(Base64Util.encoder(keyPair.getPublic().getEncoded()));
        writeFile(publicKeyFilename, publicKey.getBytes());
        // 私钥
        String privateKey = new String(Base64Util.encoder(keyPair.getPrivate().getEncoded()));
        writeFile(privateKeyFilename, privateKey.getBytes());


    }

    /**
     * 将秘钥输出到指定目录
     *
     * @param destPath 指定文件目录
     * @param bytes    文件内容
     * @throws IOException 异常
     */
    private static void writeFile(String destPath,
                                  byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }


    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return java.security.PrivateKey
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(MiniAdminConstant.ALGORITHM_NAME);
        byte[] decodedKey = Base64Util.decoder(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return java.security.PublicKey
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(MiniAdminConstant.ALGORITHM_NAME);
        byte[] decodedKey = Base64Util.decoder(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return java.lang.String
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(MiniAdminConstant.ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64Util.encoder(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return java.lang.String
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(MiniAdminConstant.ALGORITHM_NAME);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64Util.decoder(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        //对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return java.lang.String
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(MiniAdminConstant.ALGORITHM_NAME);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(MiniAdminConstant.MD5_RSA);
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64Util.encoder(signature.sign()), StandardCharsets.UTF_8);
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return boolean 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(MiniAdminConstant.ALGORITHM_NAME);
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(MiniAdminConstant.MD5_RSA);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64Util.decoder(sign.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        testGenKeyPair();
    }

    public static void testGenKeyPair() throws Exception {
        generateKeyPair("D:\\work\\workspace\\github\\mini-admin\\mini-admin-start\\src\\main\\resources\\key\\mini_rsa.pub",
                "D:\\work\\workspace\\github\\mini-admin\\mini-admin-start\\src\\main\\resources\\key\\mini_rsa.pri",
                "mini",
                2048);
    }


    public static void testKeyPair() {
        try {
            // 生成密钥对
            KeyPair keyPair = getKeyPair("pay123", 1024);
            String privateKey = new String(Base64Util.encoder(keyPair.getPrivate().getEncoded()));

            String publicKey = new String(Base64Util.encoder(keyPair.getPublic().getEncoded()));


            System.out.println("私钥:" + privateKey);


            System.out.println("公钥:" + publicKey);


            // RSA加密
            String data = "123456";
            String encryptData = encrypt(data, getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);
            // RSA签名
            String sign = sign(data, getPrivateKey(privateKey));
            // RSA验签
            boolean result = verify(data, getPublicKey(publicKey), sign);
            System.out.print("验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }

    public static PublicKey getPublicKey(InputStream pubKeyFileInputStream) throws Exception {
        String pubKey = StreamUtil.getString(pubKeyFileInputStream);
        log.info("公钥:{}", pubKey);
        return getPublicKey(pubKey);
    }

    public static PrivateKey getPrivateKey(InputStream priKeyFileInputStream) throws Exception {
        String priKey = StreamUtil.getString(priKeyFileInputStream);
        log.info("私钥:{}", priKey);
        return getPrivateKey(priKey);
    }

    public static String getPublicKeyStr(PublicKey publicKey) throws Exception {
        return new String(Base64Util.encoder(publicKey.getEncoded()));
    }

}
