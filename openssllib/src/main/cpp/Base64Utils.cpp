/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/06/15
 *     desc   :
 *     version: 1.0
 * 
 * </pre>
 */


#include <openssl/evp.h>
#include <openssl/bio.h>
#include <openssl/buffer.h>
#include <string>
#include <iostream>


/**
 * base64解码
 */
static char *base64Decode(const char *input, int length, bool newLine);

/**
 * base64 编码
 */
static char *base64Encode(const char *buffer, int length, bool newLine);


/**
 * base64编码
 * @param input 待编码内容
 * @param length  待编码内容长度
 * @param newLine  是否换行
 * @return  编码后内容
 */
static char *base64Encode(const char *buffer, int length, bool newLine) {
    BIO *bmem = nullptr;
    BIO *b64 = nullptr;
    BUF_MEM *bptr;
    b64 = BIO_new(BIO_f_base64());
    if (!newLine) {
        BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);
    }
    bmem = BIO_new(BIO_s_mem());
    b64 = BIO_push(b64, bmem);
    BIO_write(b64, buffer, length);
    BIO_flush(b64);
    BIO_get_mem_ptr(b64, &bptr);
    BIO_set_close(b64, BIO_NOCLOSE);
    char *buff = (char *) malloc(bptr->length + 1);
    memcpy(buff, bptr->data, bptr->length);
    buff[bptr->length] = '\0';
    BIO_free_all(b64);
    return buff;
}

/**
 * base64解码
 * @param input 待解码内容
 * @param length  待解码内容长度
 * @param newLine  是否换行
 * @return  解码后内容
 */
static char *base64Decode(const char *input, int length, bool newLine) {
    BIO *b64 = nullptr;
    BIO *bmem = nullptr;
    char *buffer = (char *) malloc(length);
    memset(buffer, 0, length);
    b64 = BIO_new(BIO_f_base64());
    if (!newLine) {
        BIO_set_flags(b64, BIO_FLAGS_BASE64_NO_NL);
    }
    bmem = BIO_new_mem_buf(input, length);
    bmem = BIO_push(b64, bmem);
    BIO_read(bmem, buffer, length);
    BIO_free_all(bmem);
    return buffer;
}