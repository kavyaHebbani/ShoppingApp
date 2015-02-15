package example.com.rocketinternettest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * Created by Kavya Shree, 14/02/15.
 */
public class SplashScreen extends Activity {
    private static final String TAG = "SplashScreen";
    VideoView videoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SPLASH_TIME_OUT = 2500;

        try {
            videoHolder = new VideoView(this);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            RelativeLayout rl = new RelativeLayout(this);
            rl.setBackgroundColor(Color.parseColor("#000000"));
            RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            rl.addView(videoHolder);
            videoHolder.setLayoutParams(lParams);
            setContentView(rl, lParams);
            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
            videoHolder.setVideoURI(video);
            videoHolder.start();
        } catch (Exception ex) {
            Log.e(TAG, "Exception", ex);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity_.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}