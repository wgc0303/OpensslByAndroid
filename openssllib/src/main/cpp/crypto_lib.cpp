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
//
//

#include <jni.h>
#include <bitset>
#include "AesUtils.cpp"
#include "RsaUtils.cpp"
#include "CommonUtils.cpp"
#include "MD5Utils.cpp"
#include "Base64Utils.cpp"
#include "DesUtils.cpp"
#include "Sm4Utils.cpp"
#include "Sm3Utils.cpp"

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

    const int privateKeys[] = {45, 45, 45, 45, 45, 66, 69, 71, 73, 78, 32, 82, 83, 65, 32, 80, 82,
                               73, 86, 65, 84, 69, 32, 75, 69, 89, 45, 45, 45, 45, 45, 10, 77, 73,
                               73, 67, 100, 103, 73, 66, 65, 68, 65, 78, 66, 103, 107, 113, 104,
                               107, 105, 71, 57, 119, 48, 66, 65, 81, 69, 70, 65, 65, 83, 67, 65,
                               109, 65, 119, 103, 103, 74, 99, 65, 103, 69, 65, 65, 111, 71, 66, 65,
                               78, 80, 89, 107, 113, 72, 57, 43, 107, 117, 97, 112, 86, 115, 66, 10,
                               70, 99, 119, 113, 68, 112, 121, 69, 108, 108, 100, 69, 79, 72, 77,
                               102, 102, 76, 114, 75, 112, 54, 51, 104, 66, 77, 57, 52, 115, 112,
                               72, 70, 48, 43, 86, 52, 68, 81, 114, 116, 118, 98, 73, 77, 111, 115,
                               84, 89, 56, 75, 120, 49, 101, 79, 43, 117, 67, 117, 73, 77, 117, 55,
                               57, 121, 10, 87, 55, 72, 89, 97, 107, 71, 118, 103, 56, 102, 89, 115,
                               47, 56, 65, 109, 47, 121, 114, 89, 83, 101, 75, 108, 113, 80, 83,
                               120, 107, 77, 112, 108, 113, 48, 109, 105, 114, 82, 101, 71, 47, 118,
                               77, 50, 51, 117, 120, 81, 111, 72, 48, 109, 77, 72, 56, 100, 77, 102,
                               70, 110, 119, 117, 122, 10, 98, 104, 75, 56, 69, 76, 85, 55, 112, 52,
                               66, 48, 120, 102, 99, 99, 57, 120, 53, 72, 66, 116, 86, 85, 74, 69,
                               109, 70, 65, 103, 77, 66, 65, 65, 69, 67, 103, 89, 65, 69, 105, 86,
                               101, 110, 76, 101, 99, 55, 47, 87, 97, 49, 55, 85, 82, 70, 112, 87,
                               83, 85, 51, 47, 98, 55, 10, 78, 43, 119, 70, 51, 56, 74, 84, 82, 86,
                               110, 101, 116, 48, 83, 98, 50, 109, 43, 57, 75, 106, 119, 89, 102,
                               67, 65, 81, 67, 117, 55, 71, 118, 119, 54, 119, 55, 74, 121, 121, 72,
                               104, 76, 103, 113, 82, 73, 75, 98, 75, 43, 77, 79, 53, 67, 98, 82,
                               47, 69, 49, 51, 71, 107, 70, 10, 71, 120, 116, 102, 106, 99, 108, 68,
                               106, 53, 86, 82, 103, 104, 87, 114, 84, 80, 74, 51, 67, 68, 67, 99,
                               57, 86, 66, 119, 100, 108, 85, 54, 108, 113, 55, 48, 56, 110, 53, 50,
                               78, 106, 49, 104, 49, 113, 66, 101, 55, 79, 48, 109, 105, 51, 48, 84,
                               47, 75, 49, 108, 86, 108, 73, 86, 10, 67, 53, 65, 121, 79, 99, 114,
                               72, 117, 118, 118, 67, 110, 120, 116, 107, 111, 81, 74, 66, 65, 79,
                               113, 68, 72, 56, 111, 69, 76, 81, 72, 97, 112, 103, 121, 120, 101,
                               75, 101, 49, 54, 67, 55, 105, 51, 111, 90, 117, 51, 77, 57, 112, 86,
                               104, 82, 86, 90, 121, 73, 90, 72, 86, 106, 120, 10, 68, 86, 115, 51,
                               57, 105, 88, 117, 103, 69, 109, 122, 69, 86, 81, 122, 88, 72, 80,
                               115, 103, 82, 77, 57, 110, 86, 67, 102, 50, 84, 81, 122, 56, 57, 101,
                               47, 100, 84, 77, 89, 72, 43, 69, 67, 81, 81, 68, 110, 81, 99, 90, 77,
                               112, 108, 51, 73, 54, 111, 102, 83, 114, 106, 85, 74, 10, 109, 54,
                               112, 86, 51, 55, 102, 104, 85, 80, 116, 69, 71, 105, 105, 65, 120,
                               76, 109, 117, 113, 70, 110, 97, 118, 111, 65, 82, 122, 71, 101, 120,
                               78, 113, 109, 66, 116, 118, 90, 82, 102, 117, 67, 48, 56, 79, 50, 90,
                               49, 56, 121, 57, 73, 105, 67, 71, 111, 52, 115, 110, 43, 80, 55, 71,
                               10, 108, 87, 52, 108, 65, 107, 69, 65, 109, 120, 122, 110, 53, 101,
                               77, 108, 77, 100, 106, 66, 104, 66, 118, 79, 120, 110, 57, 88, 100,
                               56, 117, 118, 106, 66, 80, 65, 69, 97, 67, 71, 119, 121, 82, 74, 57,
                               119, 47, 78, 106, 89, 114, 68, 88, 116, 97, 66, 66, 85, 51, 102, 112,
                               78, 47, 65, 10, 85, 111, 85, 50, 88, 97, 98, 78, 75, 85, 68, 84, 48,
                               81, 81, 112, 48, 112, 71, 97, 99, 57, 87, 55, 87, 51, 82, 86, 103,
                               81, 74, 65, 66, 79, 88, 84, 117, 86, 80, 99, 115, 75, 67, 109, 57,
                               56, 78, 81, 120, 68, 57, 84, 119, 68, 88, 89, 119, 121, 68, 102, 56,
                               88, 86, 77, 10, 88, 107, 51, 77, 85, 80, 75, 110, 49, 73, 89, 115,
                               87, 68, 81, 111, 68, 103, 122, 71, 85, 107, 84, 53, 117, 111, 103,
                               104, 89, 106, 79, 111, 70, 74, 89, 74, 100, 65, 103, 66, 65, 70, 106,
                               43, 88, 88, 53, 99, 104, 119, 65, 114, 77, 81, 74, 65, 70, 43, 122,
                               116, 65, 99, 47, 104, 10, 89, 48, 73, 115, 89, 121, 108, 120, 67,
                               106, 99, 115, 69, 88, 103, 43, 50, 87, 67, 75, 66, 79, 83, 98, 118,
                               79, 109, 115, 110, 89, 118, 68, 49, 113, 78, 65, 80, 122, 83, 72, 51,
                               98, 110, 87, 73, 54, 85, 49, 118, 53, 71, 110, 70, 121, 121, 84, 47,
                               122, 50, 97, 54, 56, 121, 115, 10, 114, 102, 66, 101, 101, 43, 76,
                               110, 53, 84, 75, 108, 74, 119, 61, 61, 10, 45, 45, 45, 45, 45, 69,
                               78, 68, 32, 82, 83, 65, 32, 80, 82, 73, 86, 65, 84, 69, 32, 75, 69,
                               89, 45, 45, 45, 45, 45, 10};
    jsize keyLen = sizeof(privateKeys) / sizeof(privateKeys[0]);
    LOGD("组装key");
    jbyteArray rsaPrivateKeyArrays = intArray2jbyteArray(env, privateKeys, keyLen);
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
    return md5(env, content);
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
