/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/06/05
 *     desc   :
 *     version: 1.0
 * 
 * </pre>
 */

#include <android/log.h>

#define DEBUG 1 //日志开关，1为开，其它为关
#if(DEBUG==1)
#define LOG_TAG "JNI"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE,LOG_TAG,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#else
#define LOGV(...) NULL
#define LOGD(...) NULL
#define LOGI(...) NULL
#define LOGE(...) NULL
#endif
