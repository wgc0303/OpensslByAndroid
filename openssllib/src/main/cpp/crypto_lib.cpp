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

#include <jni.h>
#include <bitset>
#include "ctyptoHeader/AesUtils.h"
#include "ctyptoHeader/RsaUtils.h"
#include "ctyptoHeader/CommonUtils.h"
#include "ctyptoHeader/Base64Utils.h"
#include "ctyptoHeader/DesUtils.h"
#include "ctyptoHeader/Sm4Utils.h"
#include "ctyptoHeader/Sm3Utils.h"
#include "LogUtils.cpp"
#include "ctyptoHeader/Md5Utils.h"


/*****************************************AES测试**************************************************/
extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_aesCbcEncrypt2ByteArray(JNIEnv *env, jobject instance,
                                                                    jbyteArray key, jbyteArray iv,
                                                                    jbyteArray content) {
    return aesCbcEncrypt(env, key, iv, content);

}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_aesEcbEncrypt2ByteArray(JNIEnv *env, jobject instance,
                                                                    jbyteArray key,
                                                                    jbyteArray content) {
    return aesEcbEncrypt(env, key, content);

}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_aesCbcDecrypt2ByteArray(JNIEnv *env, jobject instance,
                                                                    jbyteArray key, jbyteArray iv,
                                                                    jbyteArray content) {

    return aesCbcDecrypt(env, key, iv, content);

}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_aesEcbDecrypt2ByteArray(JNIEnv *env, jobject instance,
                                                                    jbyteArray key,
                                                                    jbyteArray content) {
    return aesEcbDecrypt(env, key, content);
}

/*****************************************AES测试**************************************************/



/*****************************************RSA测试**************************************************/

extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_rsaPublicKeyEncrypt2ByteArray(JNIEnv *env,
                                                                          jobject instance,
                                                                          jstring key,
                                                                          jbyteArray content) {

    std::string strPublicKey = jstring2str(env, key);
    char *keyChar = javaPublicKey2OpensslPublicKey(strPublicKey);
    //打印成数组形式
    printJniKey2JniIntArray(keyChar);

    int len = strlen(keyChar);
    jbyteArray ivArray = env->NewByteArray(len);
    env->SetByteArrayRegion(ivArray, 0, len, reinterpret_cast<const jbyte *>(keyChar));
    //公钥加密
    jbyteArray src = publicKeyEncrypt(env, ivArray, content);
    env->DeleteLocalRef(ivArray);
    env->DeleteLocalRef(instance);
    return src;
}


extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_rsaPrivateKeyDecrypt2ByteArray(JNIEnv *env,
                                                                           jobject instance,
                                                                           jstring key,
                                                                           jbyteArray content) {
    std::string strPrivateKey = jstring2str(env, key);
    char *keyChar = javaPrivateKey2OpensslPrivateKey(strPrivateKey);
    //打印成数组形式
    printJniKey2JniIntArray(keyChar);

    int len = strlen(keyChar);
    jbyteArray ivArray = env->NewByteArray(len);
    env->SetByteArrayRegion(ivArray, 0, len, reinterpret_cast<const jbyte *>(keyChar));
    return privateKeyDecrypt(env, ivArray, content);
}


extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_rsaPrivateKeyEncrypt2ByteArray(JNIEnv *env,
                                                                           jobject instance,
                                                                           jstring key,
                                                                           jbyteArray content) {
    std::string strPrivateKey = jstring2str(env, key);
    char *keyChar = javaPrivateKey2OpensslPrivateKey(strPrivateKey);
    //打印成数组形式
     printJniKey2JniIntArray(keyChar);

    int len = strlen(keyChar);
    jbyteArray ivArray = env->NewByteArray(len);
    env->SetByteArrayRegion(ivArray, 0, len, reinterpret_cast<const jbyte *>(keyChar));
    return privateKeyEncrypt(env, ivArray, content);
}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_rsaPublicKeyDecrypt2ByteArray(JNIEnv *env,
                                                                          jobject instance,
                                                                          jstring key,
                                                                          jbyteArray content) {

    std::string strPublicKey = jstring2str(env, key);
    char *keyChar = javaPublicKey2OpensslPublicKey(strPublicKey);
    int len = strlen(keyChar);
    jbyteArray ivArray = env->NewByteArray(len);
    env->SetByteArrayRegion(ivArray, 0, len, reinterpret_cast<const jbyte *>(keyChar));
    return publicKeyDecrypt(env, ivArray, content);
}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_rsaPrivateKeySign2ByteArray(JNIEnv *env,
                                                                        jobject instance,
                                                                        jstring key,
                                                                        jbyteArray content) {

//
//    std::string strPrivateKey = jstring2str(env, key);
//    char *keyChar = javaPrivateKey2OpensslPrivateKey(strPrivateKey);
//    LOGD("jniKEY :%s", keyChar);
//    int len = strlen(keyChar);
//    jbyteArray ivArray = env->NewByteArray(len);
//    env->SetByteArrayRegion(ivArray, 0, len, reinterpret_cast<const jbyte *>(keyChar));

//    char *keyChar = "-----BEGIN RSA PRIVATE KEY-----\nMIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANPYkqH9+kuapVsB\nFcwqDpyElldEOHMffLrKp63hBM94spHF0+V4DQrtvbIMosTY8Kx1eO+uCuIMu79y\nW7HYakGvg8fYs/8Am/yrYSeKlqPSxkMplq0mirReG/vM23uxQoH0mMH8dMfFnwuz\nbhK8ELU7p4B0xfcc9x5HBtVUJEmFAgMBAAECgYAEiVenLec7/Wa17URFpWSU3/b7\nN+wF38JTRVnet0Sb2m+9KjwYfCAQCu7Gvw6w7JyyHhLgqRIKbK+MO5CbR/E13GkF\nGxtfjclDj5VRghWrTPJ3CDCc9VBwdlU6lq708n52Nj1h1qBe7O0mi30T/K1lVlIV\nC5AyOcrHuvvCnxtkoQJBAOqDH8oELQHapgyxeKe16C7i3oZu3M9pVhRVZyIZHVjx\nDVs39iXugEmzEVQzXHPsgRM9nVCf2TQz89e/dTMYH+ECQQDnQcZMpl3I6ofSrjUJ\nm6pV37fhUPtEGiiAxLmuqFnavoARzGexNqmBtvZRfuC08O2Z18y9IiCGo4sn+P7G\nlW4lAkEAmxzn5eMlMdjBhBvOxn9Xd8uvjBPAEaCGwyRJ9w/NjYrDXtaBBU3fpN/A\nUoU2XabNKUDT0QQp0pGac9W7W3RVgQJABOXTuVPcsKCm98NQxD9TwDXYwyDf8XVM\nXk3MUPKn1IYsWDQoDgzGUkT5uoghYjOoFJYJdAgBAFj+XX5chwArMQJAF+ztAc/h\nY0IsYylxCjcsEXg+2WCKBOSbvOmsnYvD1qNAPzSH3bnWI6U1v5GnFyyT/z2a68ys\nrfBee+Ln5TKlJw==\n-----END RSA PRIVATE KEY-----\n";
//    int len = strlen(keyChar);
//    jbyteArray ivArray = env->NewByteArray(len);
//    env->SetByteArrayRegion(ivArray, 0, len, reinterpret_cast<const jbyte *>(keyChar));


    std::string strPrivateKey = jstring2str(env, key);
    char *keyChar = javaPrivateKey2OpensslPrivateKey(strPrivateKey);
    int len = strlen(keyChar);
    jbyteArray rsaPrivateKeyArrays = env->NewByteArray(len);
    env->SetByteArrayRegion(rsaPrivateKeyArrays, 0, len, reinterpret_cast<const jbyte *>(keyChar));

    jbyteArray src = privateKeySign(env, rsaPrivateKeyArrays, content);
    env->DeleteLocalRef(rsaPrivateKeyArrays);
    return src;
}

extern "C" JNIEXPORT jint JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_rsaPublicKeyVerify2Int(JNIEnv *env,
                                                                   jobject instance,
                                                                   jstring key,
                                                                   jbyteArray content,
                                                                   jbyteArray sign) {
    std::string strPublicKey = jstring2str(env, key);
    char *keyChar = javaPublicKey2OpensslPublicKey(strPublicKey);
    int len = strlen(keyChar);
    jbyteArray ivArray = env->NewByteArray(len);
    env->SetByteArrayRegion(ivArray, 0, len, reinterpret_cast<const jbyte *>(keyChar));
    return publicKeyVerify(env, ivArray, content, sign);
}

extern "C" JNIEXPORT void JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_generateRSAKey(JNIEnv *env,
                                                           jobject instance) {

    generateRSAKey();
}



/*****************************************RSA测试**************************************************/


extern "C" JNIEXPORT jstring JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_md52HexString(JNIEnv *env, jobject instance,
                                                          jbyteArray content) {
//    Md5Jni md5Jni = Md5Jni();
    jstring md = md5(env, content);
    return md;
}

extern "C" JNIEXPORT jboolean JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_checkUsePermission(JNIEnv *env, jobject instance,
                                                               jobject context) {
    return checkPermission(env, context);
}

/****************************************base64测试************************************************/
extern "C" JNIEXPORT jstring JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_base64Decode(JNIEnv *env, jobject instance,
                                                         jstring content) {
    const char *content_ = env->GetStringUTFChars(content, nullptr);
    char *decodeString = base64Decode(content_, strlen(content_), false);
    LOGD("jniDecodeString   :%s", decodeString);
    env->ReleaseStringUTFChars(content, content_);
    env->DeleteLocalRef(instance);
    env->DeleteLocalRef(content);
    free(decodeString);
    return env->NewStringUTF(decodeString);

}
extern "C" JNIEXPORT jstring JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_base64Encode(JNIEnv *env, jobject instance,
                                                         jstring content) {
    const char *content_ = env->GetStringUTFChars(content, nullptr);
    char *encodeString = base64Encode(content_, strlen(content_), false);
    LOGD("jniEncodeString    :%s", encodeString);
    env->ReleaseStringUTFChars(content, content_);
    env->DeleteLocalRef(instance);
    env->DeleteLocalRef(content);
    free(encodeString);
    return env->NewStringUTF(encodeString);
}

/****************************************base64测试************************************************/



/*****************************************DES测试**************************************************/

extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_des3CbcEncrypt2ByteArray(JNIEnv *env, jobject instance,
                                                                     jbyteArray key, jbyteArray iv,
                                                                     jbyteArray content) {
    return des3CbcEncrypt(env, key, iv, content);

}
extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_des3CbcDecrypt2ByteArray(JNIEnv *env, jobject instance,
                                                                     jbyteArray key, jbyteArray iv,
                                                                     jbyteArray content) {
//    const char *key_ = "d843o01ad843o01ad843o01a";
//    const char *iv_ = "d843o01b";
//    jbyteArray k = ConvertCharToJByteArray(env, key_);
//    jbyteArray i = ConvertCharToJByteArray(env, iv_);
//    jbyteArray src = des3CbcDecrypt(env, k, i, content);
//    env->DeleteLocalRef(k);
//    env->DeleteLocalRef(i);
//    return src;
    return des3CbcDecrypt(env, key, iv, content);

}
/*****************************************DES测试**************************************************/



extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_sm4CbcEncrypt2ByteArray(JNIEnv *env, jobject instance,
                                                                    jbyteArray key, jbyteArray iv,
                                                                    jbyteArray content) {
    return sm4CbcEncrypt(env, key, iv, content);

}

extern "C" JNIEXPORT jbyteArray JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_sm4CbcDecrypt2ByteArray(JNIEnv *env, jobject instance,
                                                                    jbyteArray key, jbyteArray iv,
                                                                    jbyteArray content) {
//    const char *key_ = "d843o01ad843o01ad843o01a";
//    const char *iv_ = "d843o01b";
//    jbyteArray k = ConvertCharToJByteArray(env, key_);
//    jbyteArray i = ConvertCharToJByteArray(env, iv_);
//    jbyteArray src = des3CbcDecrypt(env, k, i, content);
//    env->DeleteLocalRef(k);
//    env->DeleteLocalRef(i);
//    return src;
    return sm4CbcDecrypt(env, key, iv, content);

}


extern "C" JNIEXPORT jstring JNICALL
Java_cn_dabby_openssllib_CryptographicUtils_sm3Digest2HexString(JNIEnv *env, jobject instance,
                                                                jbyteArray content) {
    return sm3Digest(env, content);
}
