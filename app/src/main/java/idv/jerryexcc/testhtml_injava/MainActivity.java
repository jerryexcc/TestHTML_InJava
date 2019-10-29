package idv.jerryexcc.testhtml_injava;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView webView = findViewById(R.id.webVIew);
        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.edText);
        String url_string = "file:///android_asset/testJS.html";

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().isEmpty()){//判斷user是否有填資料,並且檢查是否為空白字元
                    Toast.makeText(MainActivity.this, "請打字", Toast.LENGTH_SHORT).show();
                    return;
                }
                //呼叫mobileCallJs這支JS並把editText的文字擷取轉成字串
                webView.loadUrl("javascript:mobileCallJs('" + editText.getText().toString() + "')");
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);//允許使用 javascript
        webView.getSettings().setDomStorageEnabled(true);//HTML 可在Local端儲存資料

        webView.addJavascriptInterface(new JavaScriptInterface(), "android_app");//JS與Android溝通用的Interfacem

        webView.setWebChromeClient(new WebChromeClient());//webview 才會顯示 alert

        webView.loadUrl(url_string);//讀取網頁
    }

    private class JavaScriptInterface {
        @JavascriptInterface
        public void callAndroid(String param){
            Toast.makeText(MainActivity.this, param, Toast.LENGTH_SHORT).show();
        }
    }
}
