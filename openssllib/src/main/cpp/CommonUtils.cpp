/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/06/10
 *     desc   :
 *     version: 1.0
 * 
 * </pre>
 */


#include <LogUtils.cpp>
#include <string>
#include <jni.h>
#include <ctyptoHeader/CommonUtils.h>

#include <openssl/bio.h>
#include <openssl/evp.h>
#include <openssl/pem.h>
#include <openssl/ec.h>
#include <openssl/ossl_typ.h>
#include <openssl/bn.h>
#include <openssl/crypto.h>
#include <openssl/opensslconf.h>
#include <openssl/obj_mac.h>
#include <openssl/err.h>
#include <openssl/evp.h>

/**
 * java生成的公钥转openssl 生成的公钥
 */
char *javaPublicKey2OpensslPublicKey(std::string strPublicKey) {
    int nPublicKeyLen = strPublicKey.size();
    for (int i = 64; i < nPublicKeyLen; i += 64) {
        if (strPublicKey[i] != '\n') {
            strPublicKey.insert(i, "\n");
        }
        i++;
    }
    strPublicKey.insert(0, "-----BEGIN PUBLIC KEY-----\n");
    strPublicKey.append("\n-----END PUBLIC KEY-----\n");
    LOGD("jniKey:\n%s", strPublicKey.c_str());

    char *chPublicKey = const_cast<char *>(strPublicKey.c_str());
    return chPublicKey;
}

/**
 * java生成的私钥转openssl 生成的私钥
 */
char *javaPrivateKey2OpensslPrivateKey(std::string strPrivateKey) {
    int nPrivateKeyLen = strPrivateKey.size();
    for (int i = 64; i < nPrivateKeyLen; i += 64) {
        if (strPrivateKey[i] != '\n') {
            strPrivateKey.insert(i, "\n");
        }
        i++;
    }
    strPrivateKey.insert(0, "-----BEGIN PRIVATE KEY-----\n");
    strPrivateKey.append("\n-----END PRIVATE KEY-----\n");
    LOGD("jniKey:\n%s", strPrivateKey.c_str());

    char *chPrivateKey = const_cast<char *>(strPrivateKey.c_str());
    return chPrivateKey;
}

/**
 * c++ string转jstring
 */
jstring str2jstring(JNIEnv *env, const char *pat) {
    //定义java String类 strClass
    jclass strClass = (env)->FindClass("java/lang/String");
    //获取String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
    jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    //建立byte数组
    jbyteArray bytes = (env)->NewByteArray(strlen(pat));
    //将char* 转换为byte数组
    (env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    // 设置String, 保存语言类型,用于byte数组转换至String时的参数
    jstring encoding = (env)->NewStringUTF("utf-8");
    //将byte数组转换为java String,并输出
    return (jstring) (env)->NewObject(strClass, ctorID, bytes, encoding);
}

/**
 * jstring转c++ string
 */
std::string jstring2str(JNIEnv *env, jstring jstr) {
    char *rtn = nullptr;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    std::string stemp(rtn);
    free(rtn);
    return stemp;
}

/**
 * int数组转jbyteArray 主要用于密钥的转换
 */
jbyteArray intArray2jbyteArray(JNIEnv *env, const int array[], jsize keyLen) {
    char *keys = new char[keyLen];
    for (int i = 0; i < keyLen; i++) {
        keys[i] = static_cast<char>(array[i] );
    }
    jbyteArray keyArray = env->NewByteArray(keyLen);
    env->SetByteArrayRegion(keyArray, 0, keyLen, reinterpret_cast<const jbyte *>(keys));
    //用完删除new 出来的对象，避免内存泄漏
    delete[]keys;
    return keyArray;
}

/**
 * 分段打印密钥数组
 * @param keyChar
 */
void printJniKey2JniIntArray(char *keyChar) {
    int len = strlen(keyChar);
    std::string keyString;
    for (int i = 0; i < len; i++) {
        char k = keyChar[i];
        if (i == 0) {
            keyString.append("{");
            keyString.append(std::to_string(k));
        } else if (i == len - 1) {
            keyString.append(",");
            keyString.append(std::to_string(k));
            keyString.append("}");
        } else {
            keyString.append(",");
            keyString.append(std::to_string(k));
        }
        if (keyString.length() > 1000) {
            LOGD("jni  key int 数组:%s", keyString.c_str());
            keyString.clear();
        }
    }
//    const  char* privateKey=keyString.c_str();
    LOGD("jni  key int 数组:%s", keyString.c_str());
    keyString.clear();
}

/**
 * 增加JNI使用的条件，校验权限
 * @param env
 * @param context
 * @return
 */
bool checkPermission(JNIEnv *env, jobject context) {
    jclass contextClass = env->GetObjectClass(context);
    jmethodID getApplicationContextMethod = env->GetMethodID(contextClass, "getApplicationContext",
                                                             "()Landroid/content/Context;");
    jobject applicationContext = env->CallObjectMethod(context, getApplicationContextMethod);
    if (applicationContext == nullptr) {
        env->DeleteLocalRef(contextClass);
        return false;
    }

    const char *pkg = "cn.dabby.openssllib";
    jmethodID getPackageNameMethod = env->GetMethodID(contextClass, "getPackageName",
                                                      "()Ljava/lang/String;");
    jobject packageName = (env->CallObjectMethod(applicationContext, getPackageNameMethod));
    jclass string = env->FindClass("java/lang/String");
    jmethodID getBytesMethodID = env->GetMethodID(string, "getBytes", "()[B");
    auto arrays = static_cast<jbyteArray>(env->CallObjectMethod(packageName, getBytesMethodID));
    char *pkgName = convertJByteArrayToChars(env, arrays);
    if (strcmp(pkg, pkgName) != 0) {
        //释放资源
        env->DeleteLocalRef(contextClass);
        env->DeleteLocalRef(applicationContext);
        env->DeleteLocalRef(packageName);
        env->DeleteLocalRef(string);
        env->DeleteLocalRef(arrays);
        delete pkgName;
        return false;
    }

    jmethodID getPackageManagerMethod = env->GetMethodID(contextClass, "getPackageManager",
                                                         "()Landroid/content/pm/PackageManager;");
    jobject packageManager = env->CallObjectMethod(applicationContext, getPackageManagerMethod);
    jclass packageManagerClass = env->GetObjectClass(packageManager);
    jmethodID getPackageInfoMethod = env->GetMethodID(packageManagerClass, "getPackageInfo",
                                                      "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jstring name = env->NewStringUTF(pkgName);
    jobject packageInfo = env->CallObjectMethod(packageManager, getPackageInfoMethod, name, 64);
    jclass packageInfoClass = env->GetObjectClass(packageInfo);
    jfieldID signaturesFieldID = env->GetFieldID(packageInfoClass, "signatures",
                                                 "[Landroid/content/pm/Signature;");
    auto signatures = static_cast<jobjectArray>(env->GetObjectField(packageInfo,
                                                                    signaturesFieldID));
    jobject signature = env->GetObjectArrayElement(signatures, 0);
    jclass signatureClass = env->GetObjectClass(signature);
    jmethodID hashCodeMethod = env->GetMethodID(signatureClass, "hashCode", "()I");
    jint hashCode = env->CallIntMethod(signature, hashCodeMethod);
    //释放删除资源
    env->DeleteLocalRef(contextClass);
    env->DeleteLocalRef(applicationContext);
    env->DeleteLocalRef(packageName);
    env->DeleteLocalRef(string);
    env->DeleteLocalRef(arrays);

    env->DeleteLocalRef(packageManager);
    env->DeleteLocalRef(name);
    env->DeleteLocalRef(packageInfo);
    env->DeleteLocalRef(packageInfoClass);
    env->DeleteLocalRef(packageManagerClass);
    env->DeleteLocalRef(signatures);
    env->DeleteLocalRef(signature);
    env->DeleteLocalRef(signatureClass);
    delete pkgName;
    if (hashCode != 679247880) {
        return false;
    }

//    //静态调用,校验资源完整性，防止外部拿到so库直接只用
////    jclass jniResCheckClass = env->FindClass("cn/dabby/sdk/wiiauth/util/SoResCheck");
////    jmethodID checkSdkSecurity = env->GetStaticMethodID(jniResCheckClass, "checkSdkSecurity", "(Landroid/content/Context;)Z");
////    jboolean success = env->CallStaticBooleanMethod( jniResCheckClass,checkSdkSecurity,applicationContext);
//
//    //实例调用,校验资源完整性，防止外部拿到so库直接只用
//    jclass jniResCheckClass = env->FindClass("cn/dabby/sdk/wiiauth/util/SoResCheck");
//    jmethodID init = env->GetMethodID(jniResCheckClass, "<init>", "()V");
//    jobject resCheck = env->NewObject(jniResCheckClass, init);
//    jmethodID checkSdkSecurity = env->GetMethodID(jniResCheckClass, "checkSdkSecurity", "(Landroid/content/Context;)Z");
//    jboolean success = env->CallBooleanMethod( resCheck,checkSdkSecurity,applicationContext);
//
//    env->DeleteLocalRef(jniResCheckClass);
//    env->DeleteLocalRef(resCheck);
//    if(!success){
//        return false;
//    }

    return true;
}

/**
 * jbyteArray转char
 * 调用该方法后如果不是返回java层需free(chars);
 */
char *convertJByteArrayToChars(JNIEnv *env, jbyteArray byteArray) {
    char *chars = nullptr;
    jbyte *bytes;
    bytes = env->GetByteArrayElements(byteArray, nullptr);
    int len = env->GetArrayLength(byteArray);
    chars = new char[len + 1];
    memset(chars, 0, static_cast<size_t>(len + 1));
    memcpy(chars, bytes, static_cast<size_t>(len));
    env->ReleaseByteArrayElements(byteArray, bytes, 0);
    return chars;
}

/**
 * char转jbyteArray
 * 调用该方法后如果不是返回java层需调用env->DeleteLocalRef(content);
 */
jbyteArray convertCharToJByteArray(JNIEnv *env, const char *src) {
    jsize len = strlen(src);
    jbyteArray content = env->NewByteArray(len);
    env->SetByteArrayRegion(content, 0, len, reinterpret_cast<const jbyte *>(src));
    return content;
}


/**
 * 字节流转换为十六进制字符串
 * @param source 原字符
 * @param dest  目标字符
 * @param sourceLen 原字符长度
 */
void byteToHexStr(const unsigned char *source, char *dest, int sourceLen) {
    short i;
    unsigned char highByte, lowByte;

    for (i = 0; i < sourceLen; i++) {
        highByte = source[i] >> 4;
        lowByte = source[i] & 0x0f;

        highByte += 0x30;

        if (highByte > 0x39)
            dest[i * 2] = highByte + 0x07;
        else
            dest[i * 2] = highByte;

        lowByte += 0x30;
        if (lowByte > 0x39)
            dest[i * 2 + 1] = lowByte + 0x07;
        else
            dest[i * 2 + 1] = lowByte;
    }
    return;
}

/**
 * 十六进制字符串转换为字节流
 * @param src 原字符
 * @param dst  目标字符
 */
int hexStrToByte(char *dst, const char *src) {
    while (*src) {
        if (' ' == *src) {
            src++;
            continue;
        }
        sscanf(src, "%02X", dst);
        src += 2;
        dst++;
    }
    return 0;
}


void HextoChar(uint8_t *u8, uint8_t len, char *ch) {
    uint8_t tmp = 0x00;
    for (int i = 0; i < len; i++) {
        for (int j = 0; j < 2; j++) {
            tmp = (*(u8 + i) >> 4) * (1 - j) + (*(u8 + i) & 0x0F) * j;
            if (tmp >= 0 && tmp <= 9) {
                ch[2 * i + j] = tmp + '0';
            } else if (tmp >= 0x0A && tmp <= 0x0F) {
                ch[2 * i + j] = tmp - 0x0A + 'A';
            }
        }
    }
}


// 将hexarr 转成16进制的字符串  如 0x11 0x22  转了之后是 “1122”
std::string arr2hex(const unsigned char *arr, size_t len) {
    size_t i;
    std::string res;
    char tmp[3];
    const char *tab = "0123456789ABCDEF";

    res.reserve(len * 2 + 1);
    for (i = 0; i < len; ++i) {
        tmp[0] = tab[arr[i] >> 4];
        tmp[1] = tab[arr[i] & 0xf];
        tmp[2] = '\0';
        res.append(tmp);
    }

    return res;
}
