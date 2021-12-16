package cn.dabby.openssllib.utils;

/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2021/05/06
 *     desc   :
 *     version: 1.0
 *
 * </pre>
 */
public class TestPrintln {
    public static void p(String content) {
        int length = content.length();
        int offset = length / 2048;
        if (length % 2048 != 0) {
            offset += 1;
        }
        System.out.println("字符长度：" + length);
        int temp = 0;
        for (int i = 0; i < offset; i++) {

            if (i == offset - 1) {
                System.out.println(content.substring(temp, length));
            } else {
                System.out.println(content.substring(temp, (i + 1) * 2048));
            }

            temp = (i + 1) * 2048;

        }

    }
}
