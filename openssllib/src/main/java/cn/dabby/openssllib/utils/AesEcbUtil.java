package cn.dabby.openssllib.utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description: AES-128-ECB加解密
 * @Author hzj
 * @Date:Created in 10:18 2019/10/15
 * @Version:
 */
public class AesEcbUtil {
    /**
     * 加密
     *
     * @param password 加密密码
     * @param content  需要加密的内容
     * @return 加密字符串
     */
    public static String encrypt(String password, String content) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes(), "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(result, Base64.NO_WRAP);
    }

    /**
     * 解密
     *
     * @param password 解密密钥
     * @param content  待解密内容
     * @return 解密内容
     */
    public static String decrypt(String password, String content) throws Exception {
        if (content == null) {
            return null;
        }

        SecretKeySpec skeySpec = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] result = cipher.doFinal(Base64.decode(content, Base64.NO_WRAP));
        return new String(result, StandardCharsets.UTF_8);
    }
}