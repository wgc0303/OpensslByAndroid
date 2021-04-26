package cn.dabby.openssllib

import android.Manifest
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

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
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnBase64.setOnClickListener {
            startActivity(Intent(this@MainActivity, Base64Activity::class.java))
        }
        btnAES.setOnClickListener {
            startActivity(Intent(this@MainActivity, AesActivity::class.java))
        }
        btnDES.setOnClickListener {
            startActivity(Intent(this@MainActivity, DESActivity::class.java))
        }
        btnRSA.setOnClickListener {
            startActivity(Intent(this@MainActivity, RsaActivity::class.java))
        }
        btnH5.setOnClickListener {
            startActivity(Intent(this@MainActivity, TestH5AuthActivity::class.java))
        }

        btnTestPermission.setOnClickListener {
            for (index in 1 until 50) {
                Log.d("wgc", "checkUsePermission$index")
                synchronized(MainActivity::class.java) {
                    val start = System.currentTimeMillis()
                    val checkUsePermission =
                        CryptographicUtils().checkUsePermission(this@MainActivity)
                    Log.d(
                        "wgc",
                        "checkUsePermission:${checkUsePermission}       耗时  ${System.currentTimeMillis() - start}"
                    )
                }
            }

        }

        if (!checkPermissions(permissions)) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                requestPermissions(permissions, 2222)
            }
        }

        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            val signs = packageInfo.signatures
            val sign = signs[0]
            val hashcode = sign.hashCode()
            Log.d("test", "hashCode : $hashcode")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


        val cryptographicUtils = CryptographicUtils()

        val key = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8)
        val iv = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8)
        val bytes =
            cryptographicUtils.sm4CbcEncrypt2ByteArray(key, iv, "01234567".toByteArray())
        val sm4CbcDecrypt2ByteArray = cryptographicUtils.sm4CbcDecrypt2ByteArray(key, iv, bytes)
        val string = String(sm4CbcDecrypt2ByteArray!!, StandardCharsets.UTF_8)
        Log.d("wgc","解密后数据:$string")

        val sm3Digest2HexString = cryptographicUtils.sm3Digest2HexString("abc".toByteArray())
        Log.d("wgc","sm3Digest2HexString:   $sm3Digest2HexString")
    }


    private fun checkPermissions(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        var hasAllPermissions = true
        for (permission in permissions) {
            hasAllPermissions = ContextCompat.checkSelfPermission(
                this,
                permission
            ) === PackageManager.PERMISSION_GRANTED
            if (!hasAllPermissions) {
                break
            }
        }
        return hasAllPermissions
    }
}