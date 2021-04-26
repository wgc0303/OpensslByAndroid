/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2021/04/23
 *     desc   :
 *     version: 1.0
 * 
 * </pre>
 */

#include "openssl/evp.h"
#include "jni.h"
#include "LogUtils.cpp"
#include "string"

/**
 * sm4cbc加密
 */
static jbyteArray sm4CbcEncrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_);

/**
 * sm4cbc解密
 */
static jbyteArray sm4CbcDecrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_);

/**
 * sm4cbc加密
 * @param env
 * @param keys_
 * @param iv_
 * @param src_
 * @return
 */
static jbyteArray sm4CbcEncrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_) {
    LOGD("SM4->对称密钥，也就是说加密和解密用的是同一个密钥");
    jbyte *keys = env->GetByteArrayElements(keys_, nullptr);
    jbyte *src = env->GetByteArrayElements(src_, nullptr);
    jbyte *iv = env->GetByteArrayElements(iv_, nullptr);
    jsize src_Len = env->GetArrayLength(src_);

    int outlen = 0, cipherText_len = 0;
    //计算加密后数据长度并分配内存空间
    auto *out = (unsigned char *) malloc((src_Len / 16 + 1) * 16);
    //清空内存空间
    memset(out, 0, (src_Len / 16 + 1) * 16);

    EVP_CIPHER_CTX *ctx = EVP_CIPHER_CTX_new();
    EVP_CIPHER_CTX_init(ctx);
    //设置padding
    //EVP_CIPHER_CTX_set_padding(ctx,EVP_PADDING_PKCS7);

    LOGD("SM4->指定加密算法，初始化加密key/iv");
    EVP_EncryptInit_ex(ctx, EVP_sms4_cbc(), nullptr, (const unsigned char *) keys,
                       reinterpret_cast<const unsigned char *>(iv));
    LOGD("SM4->对数据进行加密运算");
    EVP_EncryptUpdate(ctx, out, &outlen, (const unsigned char *) src, src_Len);
    cipherText_len = outlen;

    LOGD("SM4->结束加密运算");
    EVP_EncryptFinal_ex(ctx, out + outlen, &outlen);
    cipherText_len += outlen;

    LOGD("SM4->EVP_CIPHER_CTX_cleanup");
    EVP_CIPHER_CTX_cleanup(ctx);

    LOGD("SM4->从jni释放数据指针");
    env->ReleaseByteArrayElements(keys_, keys, 0);
    env->ReleaseByteArrayElements(src_, src, 0);
    env->ReleaseByteArrayElements(iv_, iv, 0);

    jbyteArray cipher = env->NewByteArray(cipherText_len);
    LOGD("SM4->在堆中分配ByteArray数组对象成功，将拷贝数据到数组中");
    env->SetByteArrayRegion(cipher, 0, cipherText_len, (jbyte *) out);
    LOGD("SM4->释放内存");
    free(out);
    return cipher;
}

/**
 * sm4cbc解密
 * @param env
 * @param keys_
 * @param iv_
 * @param src_
 * @return
 */
static jbyteArray sm4CbcDecrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_) {
    LOGD("SM4->对称密钥，也就是说加密和解密用的是同一个密钥");
    jbyte *keys = env->GetByteArrayElements(keys_, nullptr);
    jbyte *src = env->GetByteArrayElements(src_, nullptr);
    jbyte *iv = env->GetByteArrayElements(iv_, nullptr);
    jsize src_Len = env->GetArrayLength(src_);

    int outlen = 0, plaintext_len = 0;

    auto *out = (unsigned char *) malloc(src_Len);
    memset(out, 0, src_Len);

    EVP_CIPHER_CTX *ctx = EVP_CIPHER_CTX_new();
    EVP_CIPHER_CTX_init(ctx);

    //设置padding
    //EVP_CIPHER_CTX_set_padding(ctx,EVP_PADDING_PKCS7);

    LOGD("SM4->指定解密算法，初始化解密key/iv");
    EVP_DecryptInit_ex(ctx, EVP_sms4_cbc(), nullptr, (const unsigned char *) keys,
                       (const unsigned char *) iv);
    LOGD("SM4->对数据进行解密运算");
    EVP_DecryptUpdate(ctx, out, &outlen, (const unsigned char *) src, src_Len);
    plaintext_len = outlen;

    LOGD("SM4->结束解密运算");
    EVP_DecryptFinal_ex(ctx, out + outlen, &outlen);
    plaintext_len += outlen;

    LOGD("SM4->EVP_CIPHER_CTX_cleanup");
    EVP_CIPHER_CTX_cleanup(ctx);

    LOGD("SM4->从jni释放数据指针");
    env->ReleaseByteArrayElements(keys_, keys, 0);
    env->ReleaseByteArrayElements(src_, src, 0);
    env->ReleaseByteArrayElements(iv_, iv, 0);

    jbyteArray cipher = env->NewByteArray(plaintext_len);
    LOGD("SM4->在堆中分配ByteArray数组对象成功，将拷贝数据到数组中");
    env->SetByteArrayRegion(cipher, 0, plaintext_len, (jbyte *) out);
    LOGD("SM4->释放内存");
    free(out);

    return cipher;
}
