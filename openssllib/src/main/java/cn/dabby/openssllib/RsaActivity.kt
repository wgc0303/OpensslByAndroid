package cn.dabby.openssllib

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.dabby.openssllib.utils.RSAUtil
import kotlinx.android.synthetic.main.activity_rsa.*

/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/06/09
 *     desc   :
 *     version: 1.0
 *
 * </pre>
 */
class RsaActivity : AppCompatActivity() {
    private val cryptographicUtils = CryptographicUtils()
    private val TAG = "WGCTEST"
    private var TEST_DATA =
        "13bcdfasg5456!@#$%^&*()_+=~`/"

    private val TEST_PRIVATE_KEY =
        "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPabGdxBKjw4Lm39PbVuWRDfjbRdeA1tJiGeK2yL9ix+apKIsKBBBczu/zwzVpbTfV16IcW9AsX0ho0Bgx9Uvlh7uLxsVdcVHNna/hmKVO3A4NPifD1l9aZRRyBKJHHHap+Nh0lPlYFJg2qHzGyEPGjsB8rh/ddneLDGlKzPij7VAgMBAAECf2+yGceEsFNxsTRpc0zeogsOpDi4NZv3lylBYoIdE4fvD6uYbrDqMFTfKFpxYEC/ol4WC8g7Te3hikzl9LrzCMkzmu3Dh56tO4WMufO+bxYbEGv8fvacmXCuJNRn9mCD6Hg0MWCHA7sUpS/HwwnWOkY+EcCL4/kpHDl0qhx/nXUCQQD/16VYL9opZonpkZzyhnHwKs3cBYEkWpBtC0Srz5cBde6xUuBed1hOFIJHs2ccYW1Wyqvyhisu6gxhmnBd8qeLAkEA9sH/jiRns3nyv/0y6FPaMK5XLpiYKwV4oBRbt+rVTZRRJtvd1kkCm8XJxK35kg6sU2b51Js3g9vNF9guu15/HwJBAMj1hGTuwXh/mJikzhkaTekOVkEa+cOqd4OPtFMYbk6xadpTdPX+3JgIOAVGABok1RO1graveMATC8Km4tiIv90CQQCqRa35wMdKQqEuuCJzzMDoDLWeMq/2J91X4RR1Mfekg+8HuHo4jsLdCLu1GzP3BGcHWiyU99J0C61XgdaldoyTAkEAp7AaM+DQe43JandnmKiMRT23OLc5gglQ0AmLudb3Gw+qLK4CcAt4/D0cpNMb4jO8Iqgv2mOHtV39ll/CiEaHCw=="

    private val TEST_PUBLIC_KEY =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD2mxncQSo8OC5t/T21blkQ3420XXgNbSYhnitsi/YsfmqSiLCgQQXM7v88M1aW031deiHFvQLF9IaNAYMfVL5Ye7i8bFXXFRzZ2v4ZilTtwODT4nw9ZfWmUUcgSiRxx2qfjYdJT5WBSYNqh8xshDxo7AfK4f3XZ3iwxpSsz4o+1QIDAQAB"


    private var jniRsaPublicKeyEncryptString = ""
    private var javaRsaPublicKeyEncryptString = ""
    private var jniRsaPrivateKeyEncryptString = ""
    private var javaRsaPrivateKeyEncryptString = ""
    private var jniRsaPrivateKeyDecryptString = ""
    private var javaRsaPrivateKeyDecryptString = ""
    private var jniRsaPublicKeyDecryptString = ""
    private var javaRsaPublicKeyDecryptString = ""
    private var jniRsaPrivateKeySignString = ""
    private var javaRsaPrivateKeySignString = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RSAUtil.genKeyPair()
        setContentView(R.layout.activity_rsa)
        tvPublicKey.text = TEST_PUBLIC_KEY
        tvPrivateKey.text = TEST_PRIVATE_KEY
//        val color =Integer.toHexString( tvPublicKey.textColors.defaultColor )
//        Log.d("wgc","color  $color")
        etContent.setText(TEST_DATA)

        Log.d("wgc", "TEST_PUBLIC_KEY：${TEST_PUBLIC_KEY.length}")

        etContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                TEST_DATA = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        btnRSAPublicKeyEncrypt.setOnClickListener {
            compare2RSAPublicKeyEncrypt()
        }

        btnRSAPrivateKeyDecrypt.setOnClickListener {
            compare2PrivateKeyDecrypt()
        }

        btnRSAPrivateKeyEncrypt.setOnClickListener {
            compare2RSAPrivateKeyEncrypt()
        }

        btnRSAPublicKeyDecrypt.setOnClickListener {
            compare2RSAPrivateKeyDecrypt()
        }
        btnRSAPrivateKeySign.setOnClickListener {
            compare2RSAPrivateKeySign()
        }

        btnRSAPublicKeyVerify.setOnClickListener {
            compare2RSAPrivateKeyVerify()
        }

        btnGenerateRSAKey.setOnClickListener {
            cryptographicUtils.generateRSAKey()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun compare2RSAPublicKeyEncrypt() {
        val publicKey = TEST_PUBLIC_KEY.toByteArray()
        Log.d("wgc", "TEST_PUBLIC_KEY:$TEST_PUBLIC_KEY")
        val start = System.currentTimeMillis()
        val rsaPublicKeyEncrypt2ByteArray = cryptographicUtils.rsaPublicKeyEncrypt2ByteArray(
            TEST_PUBLIC_KEY,
            TEST_DATA.toByteArray()
        )
        Log.d("wgc", "耗时${System.currentTimeMillis() - start}")
        jniRsaPublicKeyEncryptString =
            Base64.encodeToString(rsaPublicKeyEncrypt2ByteArray, Base64.NO_WRAP)
        Log.d("wgc", "jniRsaPublicKeyEncryptString   :${jniRsaPublicKeyEncryptString}")
        tvJni.text = "jniRsaPublicKeyEncrypt加密：$jniRsaPublicKeyEncryptString"
        val start1 = System.currentTimeMillis()

        javaRsaPublicKeyEncryptString = RSAUtil.encrypt(TEST_PUBLIC_KEY, TEST_DATA)

        Log.d("wgc", "耗时${System.currentTimeMillis() - start1}")

    }

    @SuppressLint("SetTextI18n")
    private fun compare2PrivateKeyDecrypt() {
        val start = System.currentTimeMillis()
        val rsaPrivateKeyDecrypt2ByteArray = cryptographicUtils.rsaPrivateKeyDecrypt2ByteArray(
            TEST_PRIVATE_KEY,
            Base64.decode(javaRsaPublicKeyEncryptString, Base64.NO_WRAP)
        )
        Log.d("wgc", "耗时${System.currentTimeMillis() - start}")
        jniRsaPrivateKeyDecryptString = String(rsaPrivateKeyDecrypt2ByteArray!!)
        Log.d("wgc", "jniRsaPrivateKeyDecryptString   :${jniRsaPrivateKeyDecryptString}")
        tvJni.text = "jniRsaPrivateKey解密：$jniRsaPrivateKeyDecryptString"
    }


    @SuppressLint("SetTextI18n")
    private fun compare2RSAPrivateKeyEncrypt() {
        val start = System.currentTimeMillis()
        val rsaPrivateKeyEncrypt2ByteArray = cryptographicUtils.rsaPrivateKeyEncrypt2ByteArray(
            TEST_PRIVATE_KEY,
            TEST_DATA.toByteArray()
        )
        Log.d("wgc", "耗时${System.currentTimeMillis() - start}")
        jniRsaPrivateKeyEncryptString =
            Base64.encodeToString(rsaPrivateKeyEncrypt2ByteArray, Base64.NO_WRAP)
        tvJni.text = "jniRsaPrivateKey加密：$jniRsaPrivateKeyEncryptString"
        Log.d("wgc", "jniRsaPrivateKeyEncryptString:${jniRsaPrivateKeyEncryptString}")
    }


    @SuppressLint("SetTextI18n")
    private fun compare2RSAPrivateKeyDecrypt() {
        val start = System.currentTimeMillis()
        val rsaPublicKeyDecrypt2ByteArray = cryptographicUtils.rsaPublicKeyDecrypt2ByteArray(
            TEST_PUBLIC_KEY,
            Base64.decode(jniRsaPrivateKeyEncryptString, Base64.NO_WRAP)
        )
        Log.d("wgc", "耗时${System.currentTimeMillis() - start}")
        jniRsaPublicKeyDecryptString = String(rsaPublicKeyDecrypt2ByteArray!!)
        tvJni.text = "jniRsaPublicKey解密：$jniRsaPublicKeyDecryptString"
        Log.d("wgc", "jniRsaPublicKeyDecryptString:${jniRsaPublicKeyDecryptString}")
    }


    @SuppressLint("SetTextI18n")
    private fun compare2RSAPrivateKeySign() {
        val start = System.currentTimeMillis()
        val rsaPrivateKeySign2ByteArray = cryptographicUtils.rsaPrivateKeySign2ByteArray(
            TEST_PRIVATE_KEY,
            TEST_DATA.toByteArray()
        )
        Log.d("wgc", "耗时${System.currentTimeMillis() - start}")
        jniRsaPrivateKeySignString =
            Base64.encodeToString(rsaPrivateKeySign2ByteArray, Base64.NO_WRAP)
        tvJni.text = "jniRsaPrivateKey加签：$jniRsaPrivateKeySignString"
        Log.d("wgc", "jniRsaPrivateKeySignString:${jniRsaPrivateKeySignString}")
        val start1 = System.currentTimeMillis()
        val verifySign = RSAUtil.verifySign(TEST_PUBLIC_KEY, TEST_DATA, jniRsaPrivateKeySignString)
        Log.d("wgc", "耗时${System.currentTimeMillis() - start1}  $verifySign")
    }


    @SuppressLint("SetTextI18n")
    private fun compare2RSAPrivateKeyVerify() {
        val verify = cryptographicUtils.rsaPublicKeyVerify2Int(
            TEST_PUBLIC_KEY,
            TEST_DATA.toByteArray(),
            Base64.decode(jniRsaPrivateKeySignString, Base64.NO_WRAP)
        )

        tvJni.text = "jniRsaPublicKey验签：${verify == 1}"
    }


}