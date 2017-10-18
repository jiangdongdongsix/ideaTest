package com.iqes.utils;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//*****************************
//Linux中openssl生成密钥pem文件
//*****************************
// 1、生成pkcs#1格式的私钥pem文件
// openssl genrsa -out private_key.pem 1024
// 2、生成公钥
// openssl rsa -in private_key.pem -pubout -out public_key.pem
// 3、使用openssl将刚才生成的公钥转为pkcs#8格式
// openssl pkcs8 -topk8 -inform PEM -in private_key.pem -outform PEM -nocrypt -out private_key_pkcs8.pem


/**
 * RSA工具类
 */
public class RSAUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_TRANSFORM = "RSA/ECB/PKCS1Padding";
    public static final String CIPHER_TRANSFORM_MOBILE = "RSA/ECB/NoPadding";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final int KEY_SIZE = 1024;

    private String cipherTransform = null;
    private String signatureAlgorithm = null;
    private int keySize = 0;

    private static RSAUtil instance = null;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    static {
        // for load public/private key from *.pem files

        // JCE does not work correctly! WHY?

        //if (Security.getProvider("BC") == null) {
        //Security.addProvider(new BouncyCastleProvider());
        //}
    }

    private RSAUtil(int keySize, String cipherTransform, String signatureAlgorithm) {
        super();
        this.keySize = keySize;
        this.cipherTransform = cipherTransform;
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public static synchronized RSAUtil getInstance(int keySize) {
        if (instance == null) {
            instance = new RSAUtil(keySize, CIPHER_TRANSFORM, SIGNATURE_ALGORITHM);
        }
        return instance;
    }

    public static synchronized RSAUtil getInstance() {
        if (instance == null) {
            instance = new RSAUtil(KEY_SIZE,CIPHER_TRANSFORM, SIGNATURE_ALGORITHM);
        }
        return instance;
    }
    /**
     * 生成随机密钥对
     */
    public KeyPair generateRandomKeyPair() throws RuntimeException {
        try {
            KeyPairGenerator keyPairGen = null;
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);//指定用何种算法
            keyPairGen.initialize(keySize, new SecureRandom());//keySize秘钥长度的秘钥大小  new SecureRandom()
            KeyPair keyPair = keyPairGen.genKeyPair();//的到生成的秘钥对
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 生成随机密钥对
     */
    public KeyPair generateKeyPair(byte[] seed) throws RuntimeException {
        try {
            KeyPairGenerator keyPairGen = null;
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(keySize, new SecureRandom(seed));
            KeyPair keyPair = keyPairGen.genKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 从RSA的PKCS8格式的Pem私钥文件的中读取RSA的私钥
     * 说明：
     * Linux中：(openssl genrsa -out private_key.pem 1024)生成的是pkcs#1格式的pem私钥文件
     * Linux中：(openssl pkcs8 -topk8 -inform PEM -in private_key.pem -outform PEM -nocrypt -out private_key_pkcs8.pem)
     * 将PKCS1格式的private_key.pem私钥文件转为PKCS8格式的private_key_pkcs8.pem
     *
     * @param path pem文件路径
     * @return 私钥对象
     * @throws Exception
     */
    private static PrivateKey loadPrivateKeyFromPKCS8Pem(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        s = br.readLine();
        while (s.charAt(0) != '-') {
            sb.append(s + "\r");
            s = br.readLine();
        }
//        String readLine = null;
//        while ((readLine = br.readLine()) != null) {
//            sb.append(readLine);
//        }
        byte[] keybyte = Base64.decodeBase64(sb.toString());
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keybyte);
        return kf.generatePrivate(keySpec);
    }



    /**
     * 从文件中读取公钥
     */
    public static PublicKey loadPublicKeyByPem(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String s = br.readLine();
        StringBuffer publickey = new StringBuffer();
        s = br.readLine();
        while (s.charAt(0) != '-') {
            publickey.append(s + "\r");
            s = br.readLine();
        }
        byte[] keybyte = Base64.decodeBase64(publickey.toString());
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte);
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }

    public static PublicKey encryptByPublicKeyString(String pubKey)throws  Exception{
        byte[] keybyte = Base64.decodeBase64(pubKey);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte);
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }


    public static PublicKey loadPublicKey(String keyPath) throws RuntimeException {
        try {
            File file = new File(keyPath);
            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            in.read(data);
            in.close();

            X509EncodedKeySpec x509Enc = new X509EncodedKeySpec(data);

            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(x509Enc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePrivateKey(PrivateKey key, String keyPath) throws RuntimeException {
        try {
            FileOutputStream out = new FileOutputStream(new File(keyPath));
            PKCS8EncodedKeySpec pkcs8Enc = null;
            pkcs8Enc = new PKCS8EncodedKeySpec(key.getEncoded());
            out.write(pkcs8Enc.getEncoded());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void savePublicKey(PublicKey key, String keyPath) throws RuntimeException {
        try {
            FileOutputStream out = new FileOutputStream(new File(keyPath));
            X509EncodedKeySpec x509Enc = null;
            x509Enc = new X509EncodedKeySpec(key.getEncoded());
            out.write(x509Enc.getEncoded());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateSignature(InputStream in, PrivateKey prikey) throws RuntimeException {
        try {
            Signature sig = Signature.getInstance(signatureAlgorithm);
            sig.initSign(prikey);

            byte[] buffer = new byte[1024];
            int len;
            while (0 <= (len = in.read(buffer))) {
                sig.update(buffer, 0, len);
            }
            in.close();

            return sig.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifySignature(InputStream in, PublicKey pubKey, byte[] orgSig) throws RuntimeException {
        try {
            Signature sig = Signature.getInstance(signatureAlgorithm);

            sig.initVerify(pubKey);

            byte[] buffer = new byte[1024];
            int len;
            while (0 <= (len = in.read(buffer))) {
                sig.update(buffer, 0, len);
            }
            in.close();

            return sig.verify(orgSig);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encode(Key key, byte[] data) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance(cipherTransform);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decode(Key key, byte[] data) throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance(cipherTransform);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换密钥为Base64
     *
     * @return
     */
    public static String transKeyToBase64(Key key) {
        byte[] encodedKey = key.getEncoded();
        String keyValue = Base64.encodeBase64String(encodedKey);
        return keyValue;
    }

    /**
     * 转换密钥为Hex
     *
     * @return
     */
    public static String transKeyToHex(Key key) {
        byte[] encodedKey = key.getEncoded();
        String keyValue = HexUtil.toHexString(encodedKey);
        return keyValue;
    }

    /**
     * 获取私钥对象
     *
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromPKCS8Hex(String privateKeyHex) throws Exception {
        byte[] data = HexUtil.toByteArray(privateKeyHex);
        PKCS8EncodedKeySpec pkcs8Enc = new PKCS8EncodedKeySpec(data);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(pkcs8Enc);
    }


    /**
     * 获取私钥对象
     *
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromPKCS8Base64(String privateKeyBase64) throws Exception {
        byte[] data = Base64.decodeBase64(privateKeyBase64);
        PKCS8EncodedKeySpec pkcs8Enc = new PKCS8EncodedKeySpec(data);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(pkcs8Enc);
    }

    /**
     * 获取公钥对象
     *
     * @param keyHex
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromHex(String keyHex) throws Exception {
        byte[] data = HexUtil.toByteArray(keyHex);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(spec);
    }


    /**
     * 从Base64的字符串获得公钥
     */
    public static PublicKey getPublicKeyFromBase64(String keyBase64) throws Exception {
        byte[] keybyte = Base64.decodeBase64(keyBase64);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte);
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }




    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }


    /**
     * <P>
     * 私钥解密结果
     * </p>
     *
     * @param encryptBase64 已加密数据
     * @param privateKey    私钥
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKeyBase64(String encryptBase64, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.decodeBase64(encryptBase64);
        byte[] decryptBytes=decryptByPrivateKey(bytes,privateKey);
        return new String(decryptBytes,"UTF-8");
    }
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
        // 对数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateK) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }


    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, PublicKey publicK) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptHex 已加密数据(十六进制编码)
     * @param privateKey    私钥
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKeyHex(String encryptHex, PrivateKey privateKey) throws Exception {
        byte[] bytes= HexUtil.toByteArray(encryptHex);
        byte[] decryptBytes = decryptByPrivateKey(bytes,privateKey);
        return new String(decryptBytes, "UTF-8");
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedDataHex 已加密数据(十六进制编码)
     * @param publicKey     公钥(十六进制编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKeyHex(String encryptedDataHex, PublicKey publicKey) throws Exception {
        byte[] bytes= HexUtil.toByteArray(encryptedDataHex);
        byte[] decryptData=decryptByPublicKey(bytes,publicKey);
        return new String(decryptData, "UTF-8");
    }

    /**
     * <p>
     * 公钥加密，返回十六进制编码的密文
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKeyHex(String data, PublicKey publicKey) throws Exception {
        byte[] encryptData=encryptByPublicKey(data.getBytes("UTF-8"),publicKey);
        return HexUtil.toHexString(encryptData);
    }




    /**
     * <p>
     * 公钥加密，返回Base64编码的密文
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKeyBase64(String data, PublicKey publicKey) throws Exception {
        byte[] encryptData=encryptByPublicKey(data.getBytes("UTF-8"),publicKey);
        return Base64.encodeBase64String(encryptData);
    }

    /**
     * <p>
     * 私钥加密，返回十六进制编码的密文
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKeyHex(String data, PrivateKey privateKey) throws Exception {
        byte[] encryptData=encryptByPrivateKey(data.getBytes("UTF-8"),privateKey);
        return HexUtil.toHexString(encryptData);
    }


    /**
     * <p>
     * 私钥加密，返回Base64编码的密文
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥
     * @return Base64字符串
     * @throws Exception
     */
    public static String encryptByPrivateKeyBase64(String data, PrivateKey privateKey) throws Exception {
        byte[] bytes = data.getBytes("UTF-8");
        byte[] encryptData=encryptByPrivateKey(bytes,privateKey);
        return Base64.encodeBase64String(encryptData);
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedDataBase64 已加密数据
     * @param publicKey     公钥
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKeyBase64(String encryptedDataBase64, PublicKey publicKey) throws Exception {
        byte[] decryptData=decryptByPublicKey(Base64.decodeBase64(encryptedDataBase64),publicKey);
        return new String(decryptData,"UTF-8");
    }



    /**
     * 为数据生成数字签名
     * @param data       待签名的数据（字符串）
     * @param privateKey 签名的私钥
     * @return 数字签名（16进制字符串）
     * @throws RuntimeException
     */
    public static String generateSignatureHex(String data, PrivateKey privateKey)
            throws RuntimeException {
        try {
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initSign(privateKey);
            byte[] buffer = data.getBytes("UTF-8");
            sig.update(buffer, 0, buffer.length);
            byte[] sigBytes = sig.sign();
            String sigHex= HexUtil.toHexString(sigBytes);
            return sigHex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }  catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 为数据生成数字签名
     * @param data       待签名的数据（字符串）
     * @param privateKey 签名的私钥
     * @return 数字签名（Base64字符串）
     * @throws RuntimeException
     */
    public static String generateSignatureBase64(String data, PrivateKey privateKey)
            throws RuntimeException {
        try {

            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initSign(privateKey);
            byte[] buffer = data.getBytes("UTF-8");
            sig.update(buffer, 0, buffer.length);
            byte[] sigBytes = sig.sign();
            String sigBase64 = Base64.encodeBase64String(sigBytes);
            return sigBase64;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }  catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 验证数字签名
     *
     * @param data            被签名的数据 （字符串）
     * @param publicKeyHex       签名的公钥（16进制字符串）
     * @param orgSigHexString 待验证的数字签名（16进制字符串）
     * @return 验证结果
     * @throws RuntimeException
     */
    public static boolean verifySignatureHex(String data, String publicKeyHex,
                                          String orgSigHexString) throws Exception {
        try {

            PublicKey publicK = getPublicKeyFromHex(publicKeyHex);

            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(publicK);

            byte[] buffer = data.getBytes("UTF-8");
            sig.update(buffer, 0, buffer.length);

            byte[] orgSigBytes = HexUtil.toByteArray(orgSigHexString);
            return sig.verify(orgSigBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 验证数字签名
     *
     * @param data            被签名的数据 （字符串）
     * @param publicKey       签名的公钥
     * @param orgSigBase64String 待验证的数字签名（Base64进制字符串）
     * @return 验证结果
     * @throws RuntimeException
     */
    public static boolean verifySignatureBase64(String data, PublicKey publicKey,
                                             String orgSigBase64String) throws Exception {
        try {

            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(publicKey);

            byte[] buffer = data.getBytes("UTF-8");
            sig.update(buffer, 0, buffer.length);

            byte[] orgSigBytes = Base64.decodeBase64(orgSigBase64String);
            return sig.verify(orgSigBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //生成公钥与私钥
    public static Map<String, String> genKeyPairBase64() throws Exception {
        Map<String, String> keyMap = new HashMap<String, String>();
        String publicKeyString = null;
        String privateKeyString = null;
        //
        String keyFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        RSAUtil rsaUtil = RSAUtil.getInstance(1024);
        try {
            KeyPair keyPair = rsaUtil.generateKeyPair(keyFrom.getBytes("UTF-8"));
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 得到公钥字符串
            publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
            // 得到私钥字符串
            privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
            keyMap.put("publicKey", publicKeyString);
            keyMap.put("privateKey", privateKeyString);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return keyMap;
    }


    //生成公钥与私钥
    public static Map<String, String> genKeyPairHex() throws Exception {
        Map<String, String> keyMap = new HashMap<String, String>();
        String publicKeyString = null;
        String privateKeyString = null;
        //
        String keyFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        RSAUtil rsaUtil = RSAUtil.getInstance(1024);
        try {
            KeyPair keyPair = rsaUtil.generateKeyPair(keyFrom.getBytes("UTF-8"));
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 得到公钥字符串
            publicKeyString = HexUtil.toHexString(publicKey.getEncoded());
            // 得到私钥字符串
            privateKeyString = HexUtil.toHexString(privateKey.getEncoded());
            keyMap.put("publicKey", publicKeyString);
            keyMap.put("privateKey", privateKeyString);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return keyMap;
    }



    public static void main(String[] args) throws Exception{
        String encrypted = "F8I10QnpUGso7j/gLMOg+2IdoJh5sMa9eSm8YFItidfUpXicI+Ga1PtaVL5QEi62e3QSB0MrdmwRaloRVze24AZSf03PrXIcIvweDCVrdZKelVeCyMR8KnR71r2Esz5f6CbVi8fvtklG39AmSPHxuqIamPfDfVzyS/1u3F65aeQ=";
//        byte[] keyByte = HexUtil.toByteArray(key);
//        System.out.println(Base64.encodeBase64String(keyByte));
//        String keyBased64 = Base64.encodeBase64String(keyByte);
//        String keyBased642 = keyBased64.substring(0,keyBased64.length()-2);
//        System.out.println(keyBased642);
//        PrivateKey privateKey = RSAHelper.getPrivateKey(keyBased64);
//        PrivateKey privateKey = getPrivateKeyFromPKCS8Hex(key);
//        String res= decryptByPrivateKeyHex(encrypted,privateKey);
//        System.out.println(res);

        String pwd =RSAUtil.decryptByPrivateKeyBase64(encrypted,RSAHelper.getPrivateKey(YanHuaConstants.PRIVATE_KEY));
        System.out.println(pwd);

//        byte[] b =  new BASE64Decoder().decodeBuffer(YanHuaConstants.PUBLIC_KEY);
//        System.out.println(HexUtil.toHexString(b));

//        String data = "2bf8f75ed4997fe8b09747f249a180ac835989d5b70570145290446c6d14c17bfec0513d261913eac93c01a2b399cb9f05dcd79761240dc258f9cc97d59c424432513171dd5ca1d9c21e1304de8d8e4d383b4a2fcbaf36ece045ff167c37dfa93f0ee32aad87ce22b4da3d21a100a14bd56cd5777f9a15d6ace472cea59b77300c87bb17d305e045bd60ef5c9074aada3f613ab6dc119087e5d9db5d1d80edb22601";
//        String res= decryptByPrivateKeyHex(data,RSAHelper.getPrivateKey(YanHuaConstants.PRIVATE_KEY));
//        System.out.println(res);
//        String pwd =RSAUtil.decryptByPrivateKeyBase64(data,RSAHelper.getPrivateKey(YanHuaConstants.PRIVATE_KEY));
//        System.out.println(pwd);

    }



//    //统一测试方法
//    public static void main(String[] args) throws Exception {
//        //本地加载公私钥
//        PublicKey publicKey = loadPublicKeyByPem("E://public_key.pem");
//        PrivateKey privateKey1 = loadPrivateKeyFromPKCS1Pem("E://private_key.pem");
//        PrivateKey privateKeyPkcs=loadPrivateKeyFromPKCS8Pem("E://private_key_pkcs8.pem");
//
//        System.out.println("公钥" + Base64.encodeBase64String(publicKey.getEncoded()));
//        System.out.println("私钥" + Base64.encodeBase64String(privateKeyPkcs.getEncoded()));
//
//        //要签名的字符串
//        String message = "上海宝山钢铁股份有限公司";
//
////        byte[] bytes = encryptByPrivateKey(message.getBytes("UTF-8"), privateKey1);
////        byte[] bytes2 = decryptByPublicKey(bytes, publicKey);
////
////        System.out.println("解密后的字符串:"+new String(bytes2, "UTF-8"));
//      //Base64公钥加密私钥解密
//        String encryptMess=encryptByPublicKeyBase64(message,publicKey);
//        String decryptMess=decryptByPrivateKeyBase64(encryptMess,privateKey1);
//        System.out.println("Base64公钥加密私钥解密："+decryptMess);
//        //Base64私钥加密公钥解密
//       String encryptMess2= encryptByPrivateKeyBase64(message,privateKey1);
//       String  decryptMess2=decryptByPublicKeyBase64(encryptMess2,publicKey);
//        System.out.println("Base64私钥加密公钥解密："+decryptMess2);
//        //Hex私钥加密公钥解密
//        String encryptMess3 = encryptByPrivateKeyHex(message,privateKey1);
//        String decryptMess3=decryptByPublicKeyHex(encryptMess3,publicKey);
//        System.out.println("Hex私钥加密公钥解密："+decryptMess3);
//
//        //Hex公钥加密私钥解密
//        String encryptMess4 = encryptByPublicKeyHex(message,publicKey);
//        String decryptMess4 = decryptByPrivateKeyHex(encryptMess4,privateKey1);
//        System.out.println("Hex公钥加密私钥解密："+decryptMess4);
//
//        //签名 传入指定字符串和16进制私钥
//        String signature = generateSignatureHex(message,privateKey1);
//        String signatureBase64=generateSignatureBase64(message,privateKey1);
//
//        System.out.println("Base64签名为："+signatureBase64);
//        System.out.println("Hex签名为："+signature);
//        //验签Hex
//        boolean verifyHexSigResult=verifySignatureHex(message,HexUtil.toHexString(publicKey.getEncoded()),signature);
//        //验签Base64
//        boolean verifyBase64SigResult=verifySignatureBase64(message,publicKey,signatureBase64);
//
//        System.out.println("验证Hex签名结果为:"+verifyHexSigResult);
//        System.out.println("验证Base64签名结果为:"+verifyBase64SigResult);
//    }

}