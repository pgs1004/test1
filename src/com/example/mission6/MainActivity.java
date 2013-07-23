package com.example.mission6;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	//�� �� 
	private static final String TAG = "SampleWebViewActivity";

	private WebView webView;
	private Button loadBtn;
	private Handler mHandler = new Handler();
	
	//���⼭���� ���ϸ��̼�
	boolean isPageOpen = false;
	Animation translateLeftAnim;
	Animation translateRightAnim;
	LinearLayout slidingPage01;
	Button openBtn01;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//����
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        //����
        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(true);

        webView.setWebChromeClient(new WebBrowserClient());
        //webView.addJavascriptInterface(new JavaScriptMethods(), "sample");
        webView.loadUrl("http://m.naver.com");
        
        //�����߰� - ��ũ ���ο��� �̵�
        webView.setWebViewClient(new WebClient());

        final EditText urlInput = (EditText) findViewById(R.id.urlInput);
        loadBtn = (Button) findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener(new OnClickListener() {
       	 public void onClick(View v) {
       		 webView.loadUrl(urlInput.getText().toString());
       	 }
        });
        
        
        //�����߰� - �ڷΰ��� ���
     
    	//���ϸ��̼�

        // Sliding Page
        slidingPage01 = (LinearLayout) findViewById(R.id.slidingPage01);

        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_bottom);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.traslate_top);

        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);


        // Open Button
        openBtn01 = (Button) findViewById(R.id.openBtn01);
        openBtn01.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {

        		// start animation
        		if (isPageOpen) {
        			slidingPage01.startAnimation(translateRightAnim);
        		} else {
        			slidingPage01.setVisibility(View.VISIBLE);
        			slidingPage01.startAnimation(translateLeftAnim);
        		}

        	}
        });

    }
    
    
    //���� Ŭ����    
    final class JavaScriptMethods {
   	 JavaScriptMethods() {
        }

        public void clickOnFace() {
            mHandler.post(new Runnable() {
                public void run() {
               	 loadBtn.setText("Ŭ���Ŀ���");
               	 webView.loadUrl("javascript:changeFace()");
                }
            });

        }
    }

    final class WebBrowserClient extends WebChromeClient {
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d(TAG, message);
            result.confirm();

            return true;
        }
    }
    
    //��ũ ������ �ܺ� �������� �ȿ����� �� �ۿ��� �����ϵ��� ���ִ� Ŭ����
    class WebClient extends WebViewClient {    	
    	@Override    	
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		view.loadUrl(url);
    		return true;
    	}
    }

    
    
    //���ϸ��̼� Ŭ����
    private class SlidingPageAnimationListener implements AnimationListener {

		public void onAnimationEnd(Animation animation) {
			if (isPageOpen) {
				slidingPage01.setVisibility(View.INVISIBLE);

				openBtn01.setText("<<");
				isPageOpen = false;
			} else {
				openBtn01.setText(">>");
				isPageOpen = true;
			}
		}

		public void onAnimationRepeat(Animation animation) {

		}

		public void onAnimationStart(Animation animation) {

		}

    }


}