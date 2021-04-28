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


#ifndef BASE64_UTILS_JNI_H
#define BASE64_UTILS_JNI_H

#ifdef  __cplusplus
extern "C" {
#endif

/**
* base64解码
*/
char *base64Decode(const char *input, int length, bool newLine);

/**
 * base64 编码
 */
char *base64Encode(const char *buffer, int length, bool newLine);

#ifdef  __cplusplus
}
#endif

#endif //BASE64_UTILS_JNI_H
