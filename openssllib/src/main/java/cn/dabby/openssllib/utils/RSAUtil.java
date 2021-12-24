package cn.dabby.openssllib.utils;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @Description:
 * @Author hzj
 * @Date:Created in 14:51 2019/10/14
 * @Version:
 */
public class RSAUtil {

    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";


    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException 异常
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        BigInteger privateD = privateKey.getPrivateExponent();
        BigInteger n = privateKey.getModulus();
        String s = UtilTool.byteHexToSting(privateD.toByteArray());
        Log.d("wgc","私钥 D "+s);
        Log.d("wgc","私钥 N "+UtilTool.byteHexToSting(n.toByteArray()));
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        BigInteger publicD = publicKey.getPublicExponent();
        BigInteger e = publicKey.getModulus();
        Log.d("wgc","公钥 D "+UtilTool.byteHexToSting(publicD.toByteArray()));
        Log.d("wgc","公钥 E "+UtilTool.byteHexToSting(e.toByteArray()));
        //得到公钥字符串
        System.out.println("公钥：" + Base64.encodeToString(publicKey.getEncoded(), Base64.NO_WRAP));
        String privateK=Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP);
        // 得到私钥字符串
        System.out.println("私钥：" + privateK);
    }

    /**
     * RSA签名
     *
     * @param priKeyStr 商户私钥
     * @param srcStr    待签名数据
     * @return 签名值
     */
    public static String sign(String priKeyStr, String srcStr) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(priKeyStr, Base64.NO_WRAP));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(srcStr.getBytes(StandardCharsets.UTF_8));
        byte[] signed = signature.sign();
        return Base64.encodeToString(signed, Base64.NO_WRAP);
    }

    /**
     * RSA-SHA1公钥验签
     *
     * @param publicKeyStr RSA公钥字符串
     * @param srcStr       原文字符串
     * @param signStr      签名字符串
     * @return true.有效的签名，false.无效的签名
     */
    public static boolean verifySign(String publicKeyStr, String srcStr, String signStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKeyStr, Base64.NO_WRAP);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(srcStr.getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.decode(signStr, Base64.NO_WRAP));
        } catch (Exception e) {
            //who cares
        }
        return false;
    }


    /**
     * RSA公钥加密
     *
     * @param clientPubKey 商户公钥
     * @param str 加密字符串
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String clientPubKey, String str) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decode(clientPubKey, Base64.NO_WRAP);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)), Base64.NO_WRAP);
    }


    /**
     * RSA私钥解密
     *
     * @param str 加密字符串
     * @param clientPrivateKey 商户私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String clientPrivateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decode(str, Base64.NO_WRAP);
        //base64编码的私钥
        byte[] decoded = Base64.decode(clientPrivateKey, Base64.NO_WRAP);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte), StandardCharsets.UTF_8);
    }

}