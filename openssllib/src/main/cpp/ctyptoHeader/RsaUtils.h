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


#ifndef RSA_JNI_H
#define RSA_JNI_H

#ifdef  __cplusplus
extern "C" {
#endif

jbyteArray publicKeyEncrypt(JNIEnv *env, jbyteArray publicKeys_, jbyteArray src_);

jbyteArray privateKeyEncrypt(JNIEnv *env, jbyteArray privateKeys_, jbyteArray src_);

jbyteArray privateKeyDecrypt(JNIEnv *env, jbyteArray privateKeys_, jbyteArray src_);

jbyteArray publicKeyDecrypt(JNIEnv *env, jbyteArray publicKeys_, jbyteArray src_);

jbyteArray privateKeySign(JNIEnv *env, jbyteArray privateKeys_, jbyteArray src_);

jint publicKeyVerify(JNIEnv *env, jbyteArray publicKeys_, jbyteArray src_, jbyteArray sign_);

void generateRSAKey();


#ifdef  __cplusplus
}
#endif

#endif //RSA_JNI_H
