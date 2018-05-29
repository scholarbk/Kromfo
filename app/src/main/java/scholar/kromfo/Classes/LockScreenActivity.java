package scholar.kromfo.Classes;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import scholar.kromfo.Helpers.MotionSensors;
import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.R;
import scholar.kromfo.Service.KromfoService;
import scholar.kromfo.Service.VolumeService;


public class LockScreenActivity extends AppCompatActivity {
    SessionManager session;
    TextView timerView;
    Pinview pinview;
    String otp;
    Button finish;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;
    Vibrator v;
    Timer timer;
    MyTimerTask myTimerTask;
    private final long startTime =5 * 1000;
    private final long interval = 1 * 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_lock_screen);
        timerView=(TextView)findViewById(R.id.timer);
        pinview=(Pinview)findViewById(R.id.otpview);
        finish=(Button)findViewById(R.id.btn_finish);
        session=new SessionManager(LockScreenActivity.this);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(LockScreenActivity.this, R.raw.siren);
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);
        HashMap<String, String> phonesession = session.getOTP();
         otp = phonesession.get("OTP");

            if(otp=="0000" || otp==null){
                Toast.makeText(LockScreenActivity.this,"Please Set Your Password First",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LockScreenActivity.this,PasswordActivity.class));
                finish();
            }else{
                session.setKioskSense(true);
                startService(new Intent(LockScreenActivity.this, VolumeService.class));
                startService(new Intent(LockScreenActivity.this, KromfoService.class));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    v.vibrate(5000);
                }
                countDownTimer.start();

        }



        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(pinview.getValue()!=null){
            if( pinview.getValue().matches(otp)){
                if (mediaPlayer.isLooping()) {
                    mediaPlayer.setLooping(false);
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=null;

                    countDownTimer.cancel();
                    v.cancel();
                }


                if (mediaPlayer != null) {
                    mediaPlayer=null;
                }
                session.setKioskSense(false);
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                    myTimerTask.cancel();
                }
              
                stopService(new Intent(LockScreenActivity.this, VolumeService.class));
                stopService(new Intent(LockScreenActivity.this, MotionSensors.class));
                session.setFlatSense(false);
                startActivity(new Intent(LockScreenActivity.this,MainActivity.class));

                finish();
            }else {
                Toast.makeText(LockScreenActivity.this,"Wrong Code",Toast.LENGTH_SHORT).show();
                countDownTimer.start();
            }
        }
            }
        });


    }



    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);

        }

        public void onTick(long millisUntilFinished) {
            timerView.setText("Seconds remaining: " + millisUntilFinished / 1000);

        }

        public void onFinish() {

            timerView.setText("done!");
            if(mediaPlayer!=null){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=null;



                }
            }


            mediaPlayer = MediaPlayer.create(LockScreenActivity.this, R.raw.siren);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            if (timer == null) {
                myTimerTask = new MyTimerTask();
                timer = new Timer();
                timer.schedule(myTimerTask, 500, 500);
            }
        }
    }
    @Override
    public void onBackPressed() {

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            bringApplicationToFront();
        }
    }
    private void bringApplicationToFront() {
        KeyguardManager myKeyManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        if( myKeyManager.inKeyguardRestrictedInputMode())
            return;



        Intent notificationIntent = new Intent(this, LockScreenActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        try
        {
            pendingIntent.send();
        }
        catch (PendingIntent.CanceledException e)
        {
            e.printStackTrace();
        }
    }

}
