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

#include <ctyptoHeader/Sm3Utils.h>
#include <string>
#include "LogUtils.cpp"
#include "openssl/evp.h"
#include "openssl/sm3.h"
#include "ctyptoHeader/CommonUtils.h"
#include<iostream>



/**
 * SM3 摘要算法 sm3.h中提供的方法实现
 * @param src_
 * @return hexString
 */
// jstring sm3Digest(JNIEnv *env,jbyteArray src_) {
//    LOGD("SM3->信息摘要算法");
//    char *src = convertJByteArrayToChars(env, src_);
//    jsize src_Len = strlen(src);
//
//    unsigned char digest[SM3_DIGEST_LENGTH];
//    sm3_ctx_t ctx;
//    sm3_init(&ctx);
//    LOGD("SM3->进行SM3信息摘要运算");
//    sm3_update(&ctx, reinterpret_cast<const unsigned char *>(src), src_Len);
//    sm3_final( &ctx,digest);
//    LOGD("SM3->摘要数据转hex");
//    char out[1024] = { 0 };
//    byteToHexStr(digest,out,sizeof(digest));
//    free(src);
//    return env->NewStringUTF(out);
//}


/**
 * SM3 摘要算法 EVP实现
 * @param src_
 * @return hexString
 */
jstring sm3Digest(JNIEnv *env, jbyteArray src_) {
    LOGD("SM3->信息摘要算法");
    char *src = convertJByteArrayToChars(env, src_);
    jsize src_Len = strlen(src);
    unsigned int len;
    unsigned char digest[SM3_DIGEST_LENGTH];
    EVP_MD_CTX *md_ctx = EVP_MD_CTX_new();
    LOGD("SM3->初始化摘要结构体");
    EVP_MD_CTX_init(md_ctx);
    EVP_DigestInit_ex(md_ctx, EVP_sm3(), NULL);
    LOGD("SM3->调用摘要UpDate计算摘要");
    EVP_DigestUpdate(md_ctx, src, src_Len);
    LOGD("SM3->摘要结束，输出摘要值");
    EVP_DigestFinal_ex(md_ctx, digest, &len);
    LOGD("SM3->释放内存");
    EVP_MD_CTX_free(md_ctx);
    free(src);
    LOGD("SM3->摘要数据转hex");
    char out[1024] = {0};
    byteToHexStr(digest, out, len);

    return env->NewStringUTF(out);
}

