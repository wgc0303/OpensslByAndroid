/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/06/11
 *     desc   :
 *     version: 1.0
 * 
 * </pre>
 */

#include <jni.h>
#include <string>
#include "LogUtils.cpp"
#include "openssl/md5.h"

/**
 * md5加密
 */
static jstring md5(JNIEnv *env, jbyteArray src_);

/**
 * md5 加密
 * @param src_
 * @return hexString
 */
static jstring md5(JNIEnv *env, jbyteArray src_) {
    LOGD("MD5->信息摘要算法第五版");
    jbyte *src = env->GetByteArrayElements(src_, nullptr);
    jsize src_Len = env->GetArrayLength(src_);

    char buff[3] = {'\0'};
    char hex[33] = {'\0'};
    unsigned char digest[MD5_DIGEST_LENGTH];

//    MD5((const unsigned char *) src, src_Len, digest);

    MD5_CTX ctx;
    MD5_Init(&ctx);
    LOGD("MD5->进行MD5信息摘要运算");
    MD5_Update(&ctx, src, src_Len);
    MD5_Final(digest, &ctx);

    strcpy(hex, "");
    LOGD("MD5->把哈希值按%%02x格式定向到缓冲区");
    for (int i = 0; i != sizeof(digest); i++) {
        sprintf(buff, "%02x", digest[i]);
        strcat(hex, buff);
    }
    LOGD("MD5->%s", hex);

    LOGD("MD5->从jni释放数据指针");
    env->ReleaseByteArrayElements(src_, src, 0);

    return env->NewStringUTF(hex);
}
