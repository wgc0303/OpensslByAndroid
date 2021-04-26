package cn.dabby.openssllib.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public  class Des3Util {

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return string 加密后文本
     * @throws Exception Exception
     */
    public static String encode(String plainText) throws Exception {

        Key desKey = null;
        DESedeKeySpec spec = new DESedeKeySpec("d843o01ad843o01ad843o01a".getBytes());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
        desKey = secretKeyFactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec("d843o01b".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes("utf-8"));
        return Base64.encode2String(encryptData);
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return string  解码文本
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key desKey = null;
        DESedeKeySpec spec = new DESedeKeySpec("d843o01ad843o01ad843o01a".getBytes());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
        desKey = secretKeyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec("d843o01b".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, desKey, ips);

        byte[] decryptData = cipher.doFinal(Base64.decode2ByteArray(encryptText));

        return new String(decryptData, "utf-8");
    }

    public static class Base64 {

        private static final char[] LEGAL_CHARS =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

        public static String encode2String(byte[] data) {
            int start = 0;
            int len = data.length;
            StringBuffer buf = new StringBuffer(data.length * 3 / 2);

            int end = len - 3;
            int i = start;
            int n = 0;

            while (i <= end) {
                int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (((int) data[i + 2]) & 0x0ff);

                buf.append(LEGAL_CHARS[(d >> 18) & 63]);
                buf.append(LEGAL_CHARS[(d >> 12) & 63]);
                buf.append(LEGAL_CHARS[(d >> 6) & 63]);
                buf.append(LEGAL_CHARS[d & 63]);

                i += 3;

                if (n++ >= 14) {
                    n = 0;
                    buf.append(" ");
                }
            }

            if (i == start + len - 2) {
                int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

                buf.append(LEGAL_CHARS[(d >> 18) & 63]);
                buf.append(LEGAL_CHARS[(d >> 12) & 63]);
                buf.append(LEGAL_CHARS[(d >> 6) & 63]);
                buf.append("=");
            } else if (i == start + len - 1) {
                int d = (((int) data[i]) & 0x0ff) << 16;

                buf.append(LEGAL_CHARS[(d >> 18) & 63]);
                buf.append(LEGAL_CHARS[(d >> 12) & 63]);
                buf.append("==");
            }

            return buf.toString();
        }

        public static int decode2Int(char c) {
            if (c >= 'A' && c <= 'Z') {
                return ((int) c) - 65;
            } else if (c >= 'a' && c <= 'z') {
                return ((int) c) - 97 + 26;
            } else if (c >= '0' && c <= '9') {
                return ((int) c) - 48 + 26 + 26;
            } else {
                switch (c) {
                    case '+':
                        return 62;
                    case '/':
                        return 63;
                    case '=':
                        return 0;
                    default:
                        throw new RuntimeException("unexpected code: " + c);
                }
            }
        }

        public static byte[] decode2ByteArray(String s) {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                decode(s, bos);
            } catch (IOException e) {
                throw new RuntimeException();
            }
            byte[] decodedBytes = bos.toByteArray();
            try {
                bos.close();
                bos = null;
            } catch (IOException ex) {
                System.err.println("Error while decoding BASE64: " + ex.toString());
            }
            return decodedBytes;
        }

        public static void decode(String s, OutputStream os) throws IOException {
            int i = 0;

            int len = s.length();

            while (true) {
                while (i < len && s.charAt(i) <= ' ') {
                    i++;
                }

                if (i == len) {
                    break;
                }

                int tri = (decode2Int(s.charAt(i)) << 18) + (decode2Int(s.charAt(i + 1)) << 12) + (decode2Int(s.charAt(i + 2)) << 6) + (decode2Int(s.charAt(i + 3)));

                os.write((tri >> 16) & 255);
                if (s.charAt(i + 2) == '=') {
                    break;
                }
                os.write((tri >> 8) & 255);
                if (s.charAt(i + 3) == '=') {
                    break;
                }
                os.write(tri & 255);

                i += 4;
            }
        }
    }

}

