package cn.dabby.openssllib

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tts.*
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


/**
 * <pre>
 *
 *     author : wgc
 *     time   : 2020/06/17
 *     desc   :
 *     version: 1.0
 *
 * </pre>
 */
class TTSActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tts)
        textToSpeech = TextToSpeech(this, this) // 参数Context,TextToSpeech.OnInitListener
        textToSpeech?.setOnUtteranceProgressListener(object:UtteranceProgressListener(){
            override fun onDone(utteranceId: String?) {
                TODO("Not yet implemented")
            }

            override fun onError(utteranceId: String?) {
                TODO("Not yet implemented")
            }

            override fun onStart(utteranceId: String?) {
                TODO("Not yet implemented")
            }

        })
        btnRead.setOnClickListener {
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech?.setPitch(1.0f);
            //设定语速 ，默认1.0正常语速
            textToSpeech?.setSpeechRate(1.0f);
            //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
            textToSpeech?.speak("各个国家有各个国家的国歌,肥化肥，会挥发。", TextToSpeech.QUEUE_ADD, null);

            TestThread(assets).start()
        }

    }


   inner class TestThread(val assets: AssetManager) :Thread(){
       override fun run() {
           super.run()
           var reader: BufferedReader? = null
           val inputStream =assets.open("xingchenbian.txt")
           var tempString: String? = null
           var line = 1
           try {
               reader =  BufferedReader(InputStreamReader(inputStream,"utf-8"));

               do {
                   tempString = reader.readLine()
                   if (tempString != null) {
                       line ++
                       if(line>=10000){
                           break
                       }
                      if(!TextUtils.isEmpty( tempString.trim())) {
                          System.out.println("Line" + line + ":" + tempString)
                          textToSpeech?.speak(
                              tempString,
                              TextToSpeech.QUEUE_ADD,
                              null
                          )
                      }
                   } else {
                       break
                   }
               } while (true)

               reader.close();
           } catch (e: FileNotFoundException) {
               e.printStackTrace();
           } catch (e:IOException ) {
               e.printStackTrace();
           }finally{
               if(reader != null){
                   try {
                       reader.close();
                   } catch (e: IOException) {
                       e.printStackTrace();
                   }
               }
           }
       }
   }


    override fun onDestroy() {
        super.onDestroy()
        textToSpeech?.shutdown()
    }


    override fun onInit(status: Int) {
        if (status === TextToSpeech.SUCCESS) {
            val result: Int = textToSpeech?.setLanguage(Locale.CHINA)!!
            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show()
            }
        }
    }
}