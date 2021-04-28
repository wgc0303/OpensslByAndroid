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

#ifndef COMMON_UTILS_JNI_H
#define COMMON_UTILS_JNI_H


#ifdef  __cplusplus
extern "C" {
#endif

char *javaPublicKey2OpensslPublicKey(std::string strPublicKey);

char *javaPrivateKey2OpensslPrivateKey(std::string strPrivateKey);

jstring str2jstring(JNIEnv *env, const char *pat);

std::string jstring2str(JNIEnv *env, jstring jstr);

jbyteArray intArray2jbyteArray(JNIEnv *env, const int array[], jsize keyLen);

void printJniKey2JniIntArray(char *keyChar);

bool checkPermission(JNIEnv *env, jobject context);

char *convertJByteArrayToChars(JNIEnv *env, jbyteArray byteArray);

jbyteArray convertCharToJByteArray(JNIEnv *env, const char *src);

void hexStrToByte(const char *source, unsigned char *dest, int sourceLen);

void byteToHexStr(const unsigned char *source, char *dest, int sourceLen);


#ifdef  __cplusplus
}
#endif


#endif //COMMON_UTILS_JNI_H
