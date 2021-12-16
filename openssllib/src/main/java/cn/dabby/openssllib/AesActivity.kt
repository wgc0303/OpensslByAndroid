package cn.dabby.openssllib

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.dabby.openssllib.utils.AesCbcUtil
import cn.dabby.openssllib.utils.AesEcbUtil
import com.blankj.utilcode.util.EncryptUtils
import kotlinx.android.synthetic.main.activity_aes.*
import java.util.*

class AesActivity : AppCompatActivity() {

    private val TAG = "WGCTEST"
    private val TEST_DATA =
        "13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—13bcdfasg5456!@#$%^&*()_+=~`/|?><:'-+*./数据加解密测试—"
    private val TEST_KEY = "1234567812345678"
    private val TEST_IV = "879ouB4QG57HTIm8"

    private var javaAesCbcEncryptString = ""
    private var jniAesCbcEncryptString = ""
    private var javaAesEcbEncryptString = ""
    private var jniAesEcbEncryptString = ""

    private var javaAesCbcDecryptString = ""
    private var jniAesCbcDecryptString = ""
    private var javaAesEcbDecryptString = ""
    private var jniAesEcbDecryptString = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aes)
        tvKey.text = TEST_KEY
        tvIV.text = TEST_IV
        etContent.setText(TEST_DATA)




        btn_aes_cbc_encrypt.setOnClickListener {
            compare2AesCbcEncrypt()
        }

        btn_aes_ecb_encrypt.setOnClickListener {
            compare2aesEcbEncrypt()
        }

        btn_aes_cbc_decrypt.setOnClickListener {
            compare2aesCbcDecrypt()
        }

        btn_aes_ecb_decrypt.setOnClickListener {
            compare2aesEcbDecrypt()
        }

        btnMd5.setOnClickListener {
            md5Test()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun compare2AesCbcEncrypt() {
        val cryptographicUtils = CryptographicUtils()
        val aesCbcEncrypt2ByteArray = cryptographicUtils.aesCbcEncrypt2ByteArray(
            TEST_KEY.toByteArray(),
            TEST_IV.toByteArray(),
            TEST_DATA.toByteArray()
        )
        jniAesCbcEncryptString = Base64.encodeToString(aesCbcEncrypt2ByteArray, Base64.NO_WRAP)
//        Log.d("wgc", "jniAesCbcEncryptString   :${jniAesCbcEncryptString}")
        tvJni.text = "jniAesCbc加密：$jniAesCbcEncryptString"
        javaAesCbcEncryptString = AesCbcUtil.encrypt(
            TEST_DATA,
            Base64.encodeToString(TEST_KEY.toByteArray(), Base64.NO_WRAP),
            Base64.encodeToString(TEST_IV.toByteArray(), Base64.NO_WRAP),
            "UTF-8"
        )
//        Log.d("wgc", "javaAesCbcEncryptString   :${javaAesCbcEncryptString}")
        tvJava.text = "javaAesCbc加密：$javaAesCbcEncryptString"
        Log.d(
            "wgc", "${javaAesCbcEncryptString == javaAesCbcEncryptString}"
        )
    }

    @SuppressLint("SetTextI18n")
    private fun compare2aesEcbEncrypt() {
        val cryptographicUtils = CryptographicUtils()
        Log.d("wgc", "TEST_DATA${TEST_DATA.length}")
        val aesEcbEncrypt2ByteArray = cryptographicUtils.aesEcbEncrypt2ByteArray(
            TEST_KEY.toByteArray(),
            TEST_DATA.toByteArray()
        )
        jniAesEcbEncryptString = Base64.encodeToString(aesEcbEncrypt2ByteArray, Base64.NO_WRAP)
//        Log.d("wgc", "jniAesEcbEncryptString   :${jniAesEcbEncryptString}")
        tvJni.text = "jniAesEcb加密：$jniAesEcbEncryptString"
        javaAesEcbEncryptString = AesEcbUtil.encrypt(
            TEST_KEY,
            TEST_DATA
        )
//        Log.d("wgc", "javaAesEcbEncryptString:${javaAesEcbEncryptString}")
        tvJava.text = "javaAesEcb加密：$javaAesEcbEncryptString"
        Log.d("wgc", "${javaAesEcbEncryptString == jniAesEcbEncryptString}")
    }

    @SuppressLint("SetTextI18n")
    private fun compare2aesEcbDecrypt() {
        val cryptographicUtils = CryptographicUtils()
        val aesEcbDecrypt2ByteArray = cryptographicUtils.aesEcbDecrypt2ByteArray(
            TEST_KEY.toByteArray(),
            Base64.decode(javaAesEcbEncryptString, Base64.NO_WRAP)
        )
        jniAesEcbDecryptString = String(aesEcbDecrypt2ByteArray!!)
        tvJni.text = "jniAesEcb解密：$jniAesEcbDecryptString"
        javaAesEcbDecryptString = AesEcbUtil.decrypt(TEST_KEY, jniAesEcbEncryptString)
        tvJava.text = "javaAesEcb解密：$javaAesEcbDecryptString"
        Log.d("wgc", "${jniAesEcbDecryptString == javaAesEcbDecryptString}")
    }

    @SuppressLint("SetTextI18n")
    private fun compare2aesCbcDecrypt() {
        val cryptographicUtils = CryptographicUtils()
        val aesCbcDecrypt2ByteArray = cryptographicUtils.aesCbcDecrypt2ByteArray(
            TEST_KEY.toByteArray(),
            TEST_IV.toByteArray(),
            Base64.decode(javaAesCbcEncryptString, Base64.NO_WRAP)
        )
        jniAesCbcDecryptString = String(aesCbcDecrypt2ByteArray!!)
        tvJni.text = "jniAesCbc解密：$jniAesCbcDecryptString"
        javaAesCbcDecryptString = AesCbcUtil.decrypt(
            jniAesCbcEncryptString,
            Base64.encodeToString(TEST_KEY.toByteArray(), Base64.NO_WRAP),
            Base64.encodeToString(TEST_IV.toByteArray(), Base64.NO_WRAP),
            "UTF-8"
        )
        tvJava.text = "javaAesCbc解密：$javaAesCbcDecryptString"
        Log.d("wgc", "${jniAesCbcDecryptString == javaAesCbcDecryptString}")
    }

    @SuppressLint("SetTextI18n")
    private fun md5Test() {
        val cryptographicUtils = CryptographicUtils()
        val jniMd5 = cryptographicUtils.md52HexString(TEST_DATA.toByteArray())
        tvJni.text = "jniMD5加密：$jniMd5"
        Log.d("wgc", "jniMd5:$jniMd5")
        val javaMd5 = EncryptUtils.encryptMD5ToString(TEST_DATA).toLowerCase(Locale.ROOT)
        tvJava.text = "javaMD5加密：$javaMd5"
        Log.d("wgc", "javaMd5:$javaMd5")
        Log.d("wgc", "${jniMd5 == javaMd5}")
    }


}

