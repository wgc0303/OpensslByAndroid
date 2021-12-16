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
    private var TEST_DATA = "13bcdfasg5456!@#\$%^&*()_+=~`/"
// private var TEST_DATA =
//        "13bcdfasg5456!@#$%^&*()_+=~`/iXSgGUjp9zCFtKzWqQaiHgqUKyyW2XvrR70JVTwms4mwiZR678JVvCb3P3tM+6Xv/f42dN+9I7IbhdRGP2emEqLFUTOkUFYz0NJYl2aW4ea3cMFgkzf9KuvNRE1OhOKVZvhhRcj6GgEXgq+Op+syqQXQpvDOsTRhkSxXIGBcTTs=iXSgGUjp9zCFtKzWqQaiHgqUKyyW2XvrR70JVTwms4mwiZR678JVvCb3P3tM+6Xv/f42dN+9I7IbhdRGP2emEqLFUTOkUFYz0NJYl2aW4ea3cMFgkzf9KuvNRE1OhOKVZvhhRcj6GgEXgq+Op+syqQXQpvDOsTRhkSxXIGBcTTs="

    private val TEST_PRIVATE_KEY =
        "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI/1zC9syNbSYt+vJJnnbkOnKK9vhnzKjxpmpiNhT1ABCFaiI3V+8dMdDk/ZOG/Tsk1FrTHJ/84uBf9sjhEJ/SvzWaEeKVz+0fkET2QzrD88tLdH1Jm+W5SFYFMcpe97zwgpPthxc+sxgEhjMH+z8FraDdsYAmE+B9paYTySxDbNAgMBAAECf1aZhjsBNdAEv3XP5jSvA6Wu6MqgTEbFk1BOb6LY5t05wcpbvvjvZIma8QEXqtuafaf+QjGxUjC1fuYoZ03YtHLUGVtqD0r1L6rfEBJteWBFXTD7ttwyiWBUISsoaZkmJ7RUQfX19gnnXItc8na6QKV/QWKV4KWv7eHI/xHYYkECQQDRbGO6QKibqLekf9cDKbjBnKwtCSYxx+1gY57sMgbf6rrx5gS3RUN3nW7zAU72URZ5Fjp/yzStQvxeRdM++rJFAkEAr/o63+Q0W7ctVJ9wHVD7RKs5EqPQyWx3KSIccFFnrOzbX05fACFbe7biNS96bKOh4srwpr1vyFSLV7zmRBJ+6QJBALmv7nkNrbJfFX2g8IdYbf4VqcBA1YHcMEh/7ECnVtshqOKlPdc2bczYZbDHnzjdyxxQLkxzY+CgfV4lcVntNTUCQHVdoXiDXbwxniULNR4ITZzNyYG6Cdzc31hqrKboVAoL6n9U6J1QohAPuCyanr7oH2b/zKKYQ35LjKeWj8ikwUkCQQCedapZwf+PjmxG461iniioUVdG4HMNM8HQVWD97DvUMgYJNaysfYvNR4dzWVBRhRDNVzRwFKrEl1Sn9A4YAQlu"

    private val TEST_PUBLIC_KEY =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAqDuIwASiaWAb9MoRQmCe2NPGQrsa/QK0wc5wz3oZaA8U8mDAEtkZTip2ij6a3uJb7v1hrE5+Sod5ymkLn278G9o5ZunNZxxBh366ED23v8FYX9fIU9zEiKQJvztUMqPG7d041S1kHc2ke0bKsllxUmyguHUKoSpcmBF9SKJEzQIDAQAB"


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

//        javaRsaPublicKeyEncryptString = RSAUtil.encrypt(TEST_PUBLIC_KEY, TEST_DATA)

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
        for (i in 1..20) {
            compare2RSAPublicKeyEncrypt()
            val rsaPrivateKeySign2ByteArray = cryptographicUtils.rsaPrivateKeySign2ByteArray(
                TEST_PRIVATE_KEY,
                TEST_DATA.toByteArray()
            )
            Log.d("wgc", "耗时${System.currentTimeMillis() - start}")
            jniRsaPrivateKeySignString =
                Base64.encodeToString(rsaPrivateKeySign2ByteArray, Base64.NO_WRAP)
        }
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