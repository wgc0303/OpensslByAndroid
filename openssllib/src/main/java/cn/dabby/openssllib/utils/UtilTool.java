package cn.dabby.openssllib.utils;

import java.util.Random;

public class UtilTool {
    public static byte[] mergeByte(byte[] source, byte[] s, int begin, int end) {
        byte[] bytes = new byte[source.length + end - begin];
        System.arraycopy(source, 0, bytes, 0, source.length);
        System.arraycopy(s, begin, bytes, source.length, end);

        return bytes;
    }

    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        b[0] = (byte) (temp & 0xff);
        temp = temp >> 8;
        b[1] = (byte) (temp & 0xff);

        return b;
    }

    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff);

        s1 <<= 8;
        s = (short) (s0 | s1);

        return s;
    }

    // 随机生成16位字符串
    public static String getRandomStr(int len) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    //异或和校验
    public static byte bcc_check(byte[] bytes) {
    	byte bcc_sum = 0;
    	for ( byte theByte : bytes ) {
    		bcc_sum ^= theByte;
    	}
    	
    	return bcc_sum;
    }

    //System.arraycopy()方法
    public static byte[] byteMergerAddShort(int len, byte[] cmd) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (len & 0xff);
        bytes[1] = (byte) ((len & 0xff00) >> 8);
        byte[] data = byteMerger(bytes, cmd);
        return data;
    }

    //System.arraycopy()方法
    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    public static int byteToInt(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }

    public static String bytes2HexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }

    public static String byteHexToSting(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder stringBuffer = new StringBuilder();
        for (int aR_data : data) {
            //            stringBuffer.append(Integer.toHexString(aR_data & 0x00ff));
            stringBuffer.append(String.format("%02x", aR_data & 0x00ff));
        }
        return stringBuffer.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEFabcdef".indexOf(c);
    }
}






