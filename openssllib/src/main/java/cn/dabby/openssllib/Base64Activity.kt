package cn.dabby.openssllib

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_base_64.*

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
class Base64Activity : AppCompatActivity() {

    private val cryptographicUtils = CryptographicUtils()
    private var jniBase64EncodeString: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_64)

        btnEncode.setOnClickListener {
            jniBase64EncodeString = cryptographicUtils.base64Encode(etContent.text.toString())
            Log.d("wgc", "jnibase64Encode：$jniBase64EncodeString")
            val encodeJava = Base64.encodeToString(
                etContent.text.toString().toByteArray(),
                Base64.NO_WRAP
            )
            Log.d(
                "wgc",
                "java ${encodeJava}"
            )
            Log.d("wgc","${jniBase64EncodeString==encodeJava}")
        }

        btnDecode.setOnClickListener {
            val base64Decode = cryptographicUtils.base64Decode(jniBase64EncodeString)
            Log.d("wgc", "base64Decode：$base64Decode")
            val decodeJava = String(
                Base64.decode(
                    jniBase64EncodeString?.toByteArray(),
                    Base64.NO_WRAP
                )
            )
            Log.d(
                "wgc",
                "java $decodeJava"
            )
            Log.d("wgc","${base64Decode==decodeJava}")
        }
    }
}