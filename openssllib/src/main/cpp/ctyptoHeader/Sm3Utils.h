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


#ifndef SM3_UTILS_JNI_H
#define SM3_UTILS_JNI_H

#include <jni.h>

#ifdef  __cplusplus
extern "C" {
#endif

/**
 * SM3 摘要算法
 */
jstring sm3Digest(JNIEnv *env, jbyteArray src_);

#ifdef  __cplusplus
}
#endif

#endif //SM3_UTILS_JNI_H
