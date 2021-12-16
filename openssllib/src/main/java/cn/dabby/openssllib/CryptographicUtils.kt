package cn.dabby.openssllib

import android.content.Context

/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/06/08
 *     desc   :
 *     version: 1.0
 *
 * </pre>
 */
class CryptographicUtils {
    companion object {
        init {
            System.loadLibrary("crypto_lib")
        }
    }

    /**
     * AES-ECB-128 加密
     * @param key 密钥
     * @param content  加密内容
     * @return
     */
    external fun aesEcbEncrypt2ByteArray(
        key: ByteArray?,
        content: ByteArray?
    ): ByteArray?

    /**
     * AES-ECB-128 解密
     * @param key 密钥
     * @param content  待解密内容
     * @return
     */
    external fun aesEcbDecrypt2ByteArray(
        key: ByteArray?,
        content: ByteArray?
    ): ByteArray?


    /**
     * AES-CBC-128 加密
     * @param key 密钥
     * @param iv 密钥偏移
     * @param content  加密内容
     * @return
     */
    external fun aesCbcEncrypt2ByteArray(
        key: ByteArray?,
        iv: ByteArray?,
        content: ByteArray?
    ): ByteArray?

    /**
     * AES-CBC-128 解密
     * @param key 密钥
     * @param iv 密钥偏移
     * @param content  待解密内容
     * @return
     */
    external fun aesCbcDecrypt2ByteArray(
        key: ByteArray?,
        iv: ByteArray?,
        content: ByteArray?
    ): ByteArray?


    /**
     * RSA 公钥加密
     * @param key 密钥
     * @param content  待加密内容
     * @return
     */
    external fun rsaPublicKeyEncrypt2ByteArray(
        key: String?,
        content: ByteArray?
    ): ByteArray?


    /**
     * RSA 私钥解密
     * @param key 密钥
     * @param content  待解密内容
     * @return
     */
    external fun rsaPrivateKeyDecrypt2ByteArray(
        key: String?,
        content: ByteArray?
    ): ByteArray?


    /**
     * RSA 私钥加密
     * @param key 密钥
     * @param content  待加密内容
     * @return
     */
    external fun rsaPrivateKeyEncrypt2ByteArray(
        key: String?,
        content: ByteArray?
    ): ByteArray?


    /**
     * RSA 公钥解密
     * @param key 密钥
     * @param content  待解密内容
     * @return
     */
    external fun rsaPublicKeyDecrypt2ByteArray(
        key: String?,
        content: ByteArray?
    ): ByteArray?

    /**
     * RSA 私钥签名
     * @param key 密钥
     * @param content  待签名内容
     * @return
     */
    external fun rsaPrivateKeySign2ByteArray(
        key: String?,
        content: ByteArray?
    ): ByteArray?

    /**
     * RSA 公钥验签
     * @param key 密钥
     * @param content  待验签内容
     * @return 返回0失败 1成功
     */
    external fun rsaPublicKeyVerify2Int(
        key: String?,
        content: ByteArray?,
        sign: ByteArray?
    ): Int

    /**
     * RSA 生成密钥对
     */
    external fun generateRSAKey(

    )


    external fun md52HexString(
        content: ByteArray?
    ): String?


    external fun checkUsePermission(
        context: Context
    ): Boolean

    /**
     * base解码
     * @param content String?
     * @return String?
     */
    external fun base64Decode(
        content: String?
    ): String?

    /**
     * base64编码
     * @param content String?
     * @return String?
     */
    external fun base64Encode(
        content: String?
    ): String?


    external fun des3CbcEncrypt2ByteArray(
        key: ByteArray?,
        iv: ByteArray?,
        content: ByteArray?
    ): ByteArray?

    external fun des3CbcDecrypt2ByteArray(
        key: ByteArray?,
        iv: ByteArray?,
        content: ByteArray?
    ): ByteArray?

}