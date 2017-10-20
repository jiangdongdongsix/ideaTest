package com.iqes.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAHelper {

    /**
     * 得到公钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    /**
     * 得到私钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 得到密钥字符串（经过base64编码）
     * @return
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }

    public static String decodePlain(String stringBase64) throws Exception {

        BASE64Decoder decoder = new BASE64Decoder();
//        System.out.print(stringBase64);
        byte[] enBytes = decoder.decodeBuffer(stringBase64);
        //加解密类
        Cipher cipher = Cipher.getInstance("RSA");//Cipher.getInstance("RSA/ECB/PKCS1Padding");

        PrivateKey privateKey = getPrivateKey(YanHuaConstants.PRIVATE_KEY);
        //解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[]deBytes = cipher.doFinal(enBytes);
        return new String(deBytes);
    }

    public static String test2(){
        String param = "C7RV8P+MV6i6l9mz68VBRczuoNe5KkA1b8yY/B+LQJkmEZOgWWzeh7qsxMDXlNRgsR62/iuKpYk4+QTUdZrIEV3KQETCwxtroc6HwHsHQLuGe3aRskOV9bV+8phCiw+81D8R/a2bcA6NjYGLGpjLXwaQ8jcjyfYAku+/j28GeKs=";
        try {
            String destr =  decodePlain(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) throws Exception {
//        test2();
        String keyFrom = "ouyeel-ys-99";
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //密钥位数
//        keyPairGen.initialize(1024);
        keyPairGen.initialize(1024, new SecureRandom(keyFrom.getBytes("UTF-8")));
        //密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String publicKeyString = getKeyString(publicKey);

        String privateKeyString = getKeyString(privateKey);
        System.out.println(publicKeyString);
        System.out.println("============");
        System.out.println(privateKeyString);
        //加解密类
        Cipher cipher = Cipher.getInstance("RSA");//Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //明文
        byte[] plainText = "admin".getBytes();

        //加密
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] enBytes = cipher.doFinal(plainText);
        System.out.println("============");
        System.out.println("============");
        //通过密钥字符串得到密钥
        publicKey = getPublicKey(publicKeyString);
        privateKey = getPrivateKey(privateKeyString);

        //解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[]deBytes = cipher.doFinal(enBytes);
        System.out.println("============");
        System.out.println(new String(deBytes));
    }
    }
