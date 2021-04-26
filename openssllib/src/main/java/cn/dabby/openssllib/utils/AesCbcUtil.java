package cn.dabby.openssllib.utils;

import android.util.Base64;

import java.security.AlgorithmParameters;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created on 2017/6/5.
 */
public class AesCbcUtil {


    private static String FORMAT_DEFAULT = "utf-8";
    private static String KEY_DEFAULT = "QXV0aEtFWUAyOWQxNzI3Yg==";
    private static String IV_DEFAULT = "QXV0aElWQDdkYTRjMmZhMA==";

    /**
     * AES解密
     *
     * @param data           //密文，被加密的数据
     * @param key            //秘钥
     * @param iv             //偏移量
     * @param encodingFormat //解密后的结果需要进行的编码
     */
    public static String decrypt(String data, String key, String iv, String encodingFormat) throws Exception {
        //被加密的数据
        byte[] dataByte = Base64.decode(data, Base64.NO_WRAP);
        //加密秘钥
        byte[] keyByte = Base64.decode(key, Base64.NO_WRAP);
        //偏移量
        byte[] ivByte = Base64.decode(iv, Base64.NO_WRAP);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));

        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            return new String(resultByte, encodingFormat);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data           //明文，待加密的数据
     * @param key            //密钥
     * @param iv             //偏移量
     * @param encodingFormat //明文编码
     */
    public static String encrypt(String data, String key, String iv, String encodingFormat) throws Exception {

        //待加密的数据
        byte[] dataByte = data.getBytes(encodingFormat);
        //加密秘钥
        byte[] keyByte = Base64.decode(key, Base64.NO_WRAP);
        //偏移量
        byte[] ivByte = Base64.decode(iv, Base64.NO_WRAP);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));

        cipher.init(Cipher.ENCRYPT_MODE, spec, parameters);// 初始化

        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            return Base64.encodeToString(resultByte, Base64.NO_WRAP);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data 明文，待加密的数据
     */
    public static String encrypt(String data) throws Exception {

        //待加密的数据
        byte[] dataByte = data.getBytes(FORMAT_DEFAULT);
        //加密秘钥
        byte[] keyByte = Base64.decode(KEY_DEFAULT, Base64.NO_WRAP);
        //偏移量
        byte[] ivByte = Base64.decode(IV_DEFAULT, Base64.NO_WRAP);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));

        cipher.init(Cipher.ENCRYPT_MODE, spec, parameters);// 初始化

        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            return Base64.encodeToString(resultByte, Base64.NO_WRAP);
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param data 密文，被加密的数据
     */
    public static String decrypt(String data) throws Exception {
        //被加密的数据
        byte[] dataByte = Base64.decode(data, Base64.NO_WRAP);
        //加密秘钥
        byte[] keyByte = Base64.decode(KEY_DEFAULT, Base64.NO_WRAP);
        //偏移量
        byte[] ivByte = Base64.decode(IV_DEFAULT, Base64.NO_WRAP);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));

        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);

        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            return new String(resultByte, FORMAT_DEFAULT);
        }
        return null;
    }

    public static String getUrlParamsByMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

}
