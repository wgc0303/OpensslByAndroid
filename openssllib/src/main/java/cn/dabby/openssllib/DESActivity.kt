package cn.dabby.openssllib

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.dabby.openssllib.utils.Des3Util
import kotlinx.android.synthetic.main.activity_des.*

/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/06/16
 *     desc   :
 *     version: 1.0
 *
 * </pre>
 */
class DESActivity : AppCompatActivity() {
    private val cryptographicUtils = CryptographicUtils()
    private val key="d843o01ad843o01ad843o01a"
    private val iv="d843o01b"
    private var des3EncryptString = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_des)
        btn_des3_cbc_encrypt.setOnClickListener {
            val des3CbcEncrypt2ByteArray = cryptographicUtils.des3CbcEncrypt2ByteArray(
                key.toByteArray(),
                iv.toByteArray(),
                etContent.text.toString().toByteArray()
            )
            des3EncryptString = Des3Util.Base64.encode2String(des3CbcEncrypt2ByteArray)
            Log.d("wgc", "jni  :$des3EncryptString")
            Log.d("wgc", "java  :${Des3Util.encode(etContent.text.toString())}")
            Log.d(
                "wgc",
                "${des3EncryptString == Des3Util.encode(
                    etContent.text.toString()
                )}"
            )
        }

        btn_des3_cbc_decrypt.setOnClickListener {
            val des3CbcDecrypt2ByteArray = cryptographicUtils.des3CbcDecrypt2ByteArray(
                key.toByteArray(),
                iv.toByteArray(),
                Des3Util.Base64.decode2ByteArray(des3EncryptString)
            )
            Log.d("wgc","jni des3解密：${String(des3CbcDecrypt2ByteArray!!)}")
        }
    }
}