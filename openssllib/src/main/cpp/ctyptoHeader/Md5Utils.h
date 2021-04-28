/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2021/04/27
 *     desc   :
 *     version: 1.0
 * 
 * </pre>
 */

#include <jni.h>

#ifndef MD5_JNI_H
#define MD5_JNI_H

#ifdef  __cplusplus
extern "C" {
#endif

/**
* md5摘要
*/
jstring md5(JNIEnv *env, jbyteArray src_);


#ifdef  __cplusplus
}
#endif

#endif //MD5_JNI_H
