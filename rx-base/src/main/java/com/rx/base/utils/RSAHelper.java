package com.rx.base.utils;

import com.rx.base.result.type.BusinessException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSAHelper {


    /**
     * RSA 解密
     * @param encryptedBytes
     * @param privateKey
     * @param keyLength
     * @param reserveSize
     * @param cipherAlgorithm
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int decryptBlockSize = keyByteSize - reserveSize;
        int nBlock = encryptedBytes.length / keyByteSize;
        ByteArrayOutputStream outbuf = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                outbuf.write(decryptedBlock);
            }
            outbuf.flush();
            return outbuf.toByteArray();
        } catch (Exception e) {
            throw new BusinessException("DEENCRYPT ERROR:", e);
        } finally {
            try{
                if(outbuf != null){
                    outbuf.close();
                }
            }catch (Exception e){
                outbuf = null;
                throw e;
            }
        }
    }

    /**
     * RSA 加密
     * @param plainBytes
     * @param publicKey
     * @param keyLength
     * @param reserveSize
     * @param cipherAlgorithm
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int encryptBlockSize = keyByteSize - reserveSize;
        int nBlock = plainBytes.length / encryptBlockSize;
        if ((plainBytes.length % encryptBlockSize) != 0) {
            nBlock += 1;
        }
        ByteArrayOutputStream outbuf = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                outbuf.write(encryptedBlock);
            }
            outbuf.flush();
            return outbuf.toByteArray();
        } catch (Exception e) {
            throw new BusinessException("ENCRYPT ERROR:", e);
        } finally {
            try{
                if(outbuf != null){
                    outbuf.close();
                }
            }catch (Exception e){
                outbuf = null;
               throw e;
            }
        }
    }

    /**
     * 获取RSA PKCS#8私钥
     * @param privateKeyPath
     * @param keyAlgorithm
     * @return
     */
    public static PrivateKey getPriKey(String privateKeyPath,String keyAlgorithm){
        PrivateKey privateKey = null;
        InputStream inputStream = null;
        try {
            inputStream = RSAHelper.class.getClassLoader().getResourceAsStream(privateKeyPath);
            privateKey = getPrivateKey(inputStream,keyAlgorithm);
        } catch (Exception e) {
            throw new BusinessException("加载私钥出错!");
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                   throw new BusinessException("加载私钥,关闭流时出错!");
                }
            }
        }
        return privateKey;
    }

    /**
     * 获取RSA PKCS#8公钥
     * @param publicKeyPath
     * @param keyAlgorithm
     * @return
     */
    public static PublicKey getPubKey(String publicKeyPath, String keyAlgorithm){
        PublicKey publicKey = null;
        InputStream inputStream = null;
        try
        {
            inputStream = RSAHelper.class.getClassLoader().getResourceAsStream(publicKeyPath);
            publicKey = getPublicKey(inputStream,keyAlgorithm);
        } catch (Exception e) {

            e.printStackTrace();//EAD PUBLIC KEY ERROR
            throw new BusinessException("加载公钥出错!");
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    throw new BusinessException("加载公钥,关闭流时出错!");
                }
            }
        }
        return publicKey;
    }

    /**
     * 获取RSA PKCS#8公钥
     * @param inputStream
     * @param keyAlgorithm
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(InputStream inputStream, String keyAlgorithm) throws Exception {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(decodeBase64(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            //下行出错  java.security.spec.InvalidKeySpecException: java.security.InvalidKeyException: IOException: DerInputStream.getLength(): lengthTag=127, too big.
            PublicKey publicKey = keyFactory.generatePublic(pubX509);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("b8.........");
            throw new BusinessException("加载公钥出错:", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                inputStream = null;
               throw e;
            }
        }
    }

    /**
     * 获取RSA PKCS#8私钥
     * @param inputStream
     * @param keyAlgorithm
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(InputStream inputStream, String keyAlgorithm) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decodeBase64(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
            return privateKey;
        } catch (Exception e) {
            throw new BusinessException("READ PRIVATE KEY ERROR:" ,e);
        }  finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                inputStream = null;
                throw e;
            }
        }
    }
    //一下面是base64的编码和解码
    public static String encodeBase64(byte[]input) throws Exception{
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod= clazz.getMethod("encode", byte[].class);
        mainMethod.setAccessible(true);
        Object retObj=mainMethod.invoke(null, new Object[]{input});
        return (String)retObj;
    }
    /***
     * decode by Base64
     */
    public static byte[] decodeBase64(String input) throws Exception{
        Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method mainMethod= clazz.getMethod("decode", String.class);
        mainMethod.setAccessible(true);
        Object retObj=mainMethod.invoke(null, input);
        return (byte[])retObj;
    }

}