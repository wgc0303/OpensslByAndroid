package cn.dabby.openssllib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

import cn.dabby.openssllib.utils.PhotoUtils;

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
public class TestH5AuthActivity extends AppCompatActivity {
    private String url = "https://auth.dabby.cn/fcfe/lipAuth/index.html#/guide?certtoken=4223dd0d-61a6-4b59-9d7e-2f4c3e9bc561";
    private final static int PHOTO_REQUEST = 100;
    private final static int VIDEO_REQUEST = 120;
    private final static String TAG = "wgc";
    private WebView webView;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private boolean videoFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_auth);
        initWebView();
    }

        //初始化webView
        private void initWebView() {
            //从布局文件中扩展webView
            webView = (WebView) this.findViewById(R.id.webView);
            initWebViewSetting();
        }

        //初始化webViewSetting
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private void initWebViewSetting() {
            WebSettings settings = webView.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setDomStorageEnabled(true);
            settings.setDefaultTextEncodingName("UTF-8");
            settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
            settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
            // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
            settings.setAllowFileAccessFromFileURLs(false);
            // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
            settings.setAllowUniversalAccessFromFileURLs(false);
            //开启JavaScript支持
            settings.setJavaScriptEnabled(true);
            // 支持缩放
            settings.setSupportZoom(true);
            //辅助WebView设置处理关于页面跳转，页面请求等操作
            webView.setWebViewClient(new MyWebViewClient());
            //辅助WebView处理图片上传操作
            webView.setWebChromeClient(new MyChromeWebClient());
            //加载地址
            webView.loadUrl(url);
        }

//        @OnClick(R.id.titleBar_btn_back)
//        public void onViewClicked() {
//            if (webView.canGoBack()) {
//                webView.goBack();// 返回前一个页面
//            } else {
//                finish();
//            }
//        }

        //自定义 WebViewClient 辅助WebView设置处理关于页面跳转，页面请求等操作【处理tel协议和视频通讯请求url的拦截转发】
        private class MyWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("wgc","url:"+url);
                if (!TextUtils.isEmpty(url)) {
                    videoFlag = url.contains("vedio");
                }
                if (url.trim().startsWith("tel")) {//特殊情况tel，调用系统的拨号软件拨号【<a href="tel:1111111111">1111111111</a>】
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else {
                    String port = url.substring(url.lastIndexOf(":") + 1, url.lastIndexOf("/"));//尝试要拦截的视频通讯url格式(808端口)：【http://xxxx:808/?roomName】
                    if (port.equals("808")) {//特殊情况【若打开的链接是视频通讯地址格式则调用系统浏览器打开】
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    } else {//其它非特殊情况全部放行
                        view.loadUrl(url);
                    }
                }
                return true;
            }
        }


        private Uri imageUri;

        //自定义 WebChromeClient 辅助WebView处理图片上传操作【<input type=file> 文件上传标签】
        public class MyChromeWebClient extends WebChromeClient {
            // For Android 3.0-
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg)");
                mUploadMessage = uploadMsg;
                if (videoFlag) {
                    recordVideo();
                } else {
                    takePhoto();
                }

            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                Log.d(TAG, "openFileChoose( ValueCallback uploadMsg, String acceptType )");
                mUploadMessage = uploadMsg;
                if (videoFlag) {
                    recordVideo();
                } else {
                    takePhoto();
                }
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
                mUploadMessage = uploadMsg;
                if (videoFlag) {
                    recordVideo();
                } else {
                    takePhoto();
                }
            }

            // For Android 5.0+
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Log.d(TAG, "onShowFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
                mUploadCallbackAboveL = filePathCallback;
                String[] acceptTypes = fileChooserParams.getAcceptTypes();
                for (int i = 0; i <acceptTypes.length ; i++) {
                    Log.d("wgc","acceptTypes:"+acceptTypes[i]);
                    if(acceptTypes[i].contains("video")){
                        videoFlag = true;
                    }
                }
                if (videoFlag) {
                    recordVideo();
                } else {
                    takePhoto();
                }
                return true;
            }
        }

        /**
         * 拍照
         */
        private void takePhoto() {
            File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(fileUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(TestH5AuthActivity.this, getPackageName() + ".fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
            }
            PhotoUtils.takePicture(TestH5AuthActivity.this, imageUri, PHOTO_REQUEST);
        }

        /**
         * 录像
         */
        private void recordVideo() {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            //限制时长
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
            //开启摄像机
            startActivityForResult(intent, VIDEO_REQUEST);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            //如果按下的是回退键且历史记录里确实还有页面
            if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PHOTO_REQUEST) {
                if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (mUploadCallbackAboveL != null) {
                    onActivityResultAboveL(requestCode, resultCode, data);
                } else if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }
            } else if (requestCode == VIDEO_REQUEST) {
                if (null == mUploadMessage && null == mUploadCallbackAboveL) return;

                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (mUploadCallbackAboveL != null) {
                    if (resultCode == RESULT_OK) {
                        mUploadCallbackAboveL.onReceiveValue(new Uri[]{result});
                        mUploadCallbackAboveL = null;
                    } else {
                        mUploadCallbackAboveL.onReceiveValue(new Uri[]{});
                        mUploadCallbackAboveL = null;
                    }

                } else if (mUploadMessage != null) {
                    if (resultCode == RESULT_OK) {
                        mUploadMessage.onReceiveValue(result);
                        mUploadMessage = null;
                    } else {
                        mUploadMessage.onReceiveValue(Uri.EMPTY);
                        mUploadMessage = null;
                    }

                }
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
            if (requestCode != PHOTO_REQUEST || mUploadCallbackAboveL == null) {
                return;
            }
            Uri[] results = null;
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    results = new Uri[]{imageUri};
                } else {
                    String dataString = data.getDataString();
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        results = new Uri[clipData.getItemCount()];
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            ClipData.Item item = clipData.getItemAt(i);
                            results[i] = item.getUri();
                        }
                    }

                    if (dataString != null)
                        results = new Uri[]{Uri.parse(dataString)};
                }
            }
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (webView != null) {
                webView.setWebViewClient(null);
                webView.setWebChromeClient(null);
                webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                webView.clearHistory();
//            ((ViewGroup) webView.getParent()).removeView(webView);
                webView.destroy();
                webView = null;
            }
        }
    }
