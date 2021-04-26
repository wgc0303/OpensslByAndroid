# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
#cmake可编译的最小版本
cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.




#指定c++文件路徑
file(GLOB SOURCE ${CMAKE_SOURCE_DIR}/*.cpp)

##表示把src/main/cpp加入到include目录，这样在代码中使用：#include <...>就能访问到头文件
#include_directories(src/main/cpp)
include_directories(${CMAKE_SOURCE_DIR})


#添加目标库
add_library( # Sets the name of the library.
        crypto_lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        #目标库c++资源路径
        ${SOURCE})



#添加两个预编译库
add_library(# Sets the name of the library.
        openssl-crypto
        # Sets the library as a static library.
        STATIC
        IMPORTED)

set_target_properties(
        # Specifies the target library.
        openssl-crypto
        # Specifies the parameter you want to define.
        PROPERTIES IMPORTED_LOCATION
        # Provides the path to the library you want to import.
        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libcrypto.a)

add_library(# Sets the name of the library.
        openssl-ssl
        # Sets the library as a static library.
        STATIC
        IMPORTED)

set_target_properties(
        # Specifies the target library.
        openssl-ssl
        # Specifies the parameter you want to define.
        PROPERTIES IMPORTED_LOCATION
        # Provides the path to the library you want to import.
        ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libssl.a)

#查找android log库
find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

#编译关联目标库
target_link_libraries( # Specifies the target library.
        crypto_lib
        # Links the target library to the log library
        # included in the NDK.
        #目标库与其他库关联
        ${log-lib} openssl-ssl openssl-crypto)


