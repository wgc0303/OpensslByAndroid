/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2021/04/28
 *     desc   :
 *     version: 1.0
 * 
 * </pre>
 */


#ifndef AES_UTILS_JNI_H
#define AES_UTILS_JNI_H

#include <jni.h>

#ifdef  __cplusplus
extern "C" {
#endif

/**
 * AES-CBC-128加密
 */
jbyteArray aesCbcEncrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_);

/**
 * AES-CBC-128解密
 */
jbyteArray aesCbcDecrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_);

/**
 * AES-ECB-128加密
 */
jbyteArray aesEcbEncrypt(JNIEnv *env, jbyteArray keys_, jbyteArray src_);

/**
 * AES-ECB-128解密
 */
jbyteArray aesEcbDecrypt(JNIEnv *env, jbyteArray keys_, jbyteArray src_);


#ifdef  __cplusplus
}
#endif

#endif //AES_UTILS_JNI_H
