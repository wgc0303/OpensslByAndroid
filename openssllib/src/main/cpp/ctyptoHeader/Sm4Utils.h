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


#ifndef SM4_UTILS_JNI_H
#define SM4_UTILS_JNI_H

#include "jni.h"

#ifdef  __cplusplus
extern "C" {
#endif

/**
* sm4cbc加密
*/
 jbyteArray sm4CbcEncrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_);

/**
 * sm4cbc解密
 */
 jbyteArray sm4CbcDecrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_);

#ifdef  __cplusplus
}
#endif

#endif //SM4_UTILS_JNI_H
