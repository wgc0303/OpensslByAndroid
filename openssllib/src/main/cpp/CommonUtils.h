/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2021/04/25
 *     desc   :
 *     version: 1.0
 * 
 * </pre>
 */

#include <string>
#include <jni.h>

#ifndef OPENSSL_COMMONUTILS_H
#define OPENSSL_COMMONUTILS_H


#ifdef  __cplusplus
extern "C" {
#endif

static char *javaPublicKey2OpensslPublicKey(std::string strPublicKey);

static char *javaPrivateKey2OpensslPrivateKey(std::string strPrivateKey);

static jstring str2jstring(JNIEnv *env, const char *pat);

static std::string jstring2str(JNIEnv *env, jstring jstr);

static jbyteArray intArray2jbyteArray(JNIEnv *env, const int array[], jsize keyLen);

static void printJniKey2JniIntArray(char *keyChar);

static bool checkPermission(JNIEnv *env, jobject context);

static char *convertJByteArrayToChars(JNIEnv *env, jbyteArray byteArray);

static jbyteArray convertCharToJByteArray(JNIEnv *env, const char *src);

static void hexStrToByte(const char *source, unsigned char *dest, int sourceLen);

static void byteToHexStr(const unsigned char *source, char *dest, int sourceLen);

#ifdef  __cplusplus
}
#endif


#endif //OPENSSL_COMMONUTILS_H
