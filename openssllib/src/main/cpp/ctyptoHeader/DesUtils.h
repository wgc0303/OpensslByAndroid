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


#ifndef DES_UTILS_JNI_H
#define DES_UTILS_JNI_H

#include "jni.h"

#ifdef  __cplusplus
extern "C" {
#endif

/**
* des3cbc加密
*/
jbyteArray des3CbcEncrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_);

/**
 * des3cbc解密
 */
jbyteArray des3CbcDecrypt(JNIEnv *env, jbyteArray keys_, jbyteArray iv_, jbyteArray src_);

#ifdef  __cplusplus
}
#endif

#endif //DES_UTILS_JNI_H
