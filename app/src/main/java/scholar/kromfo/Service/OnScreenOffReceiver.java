package scholar.kromfo.Service;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import scholar.kromfo.Classes.LockScreenActivity;
import scholar.kromfo.Helpers.AppController;
import scholar.kromfo.Helpers.SessionManager;

public class OnScreenOffReceiver extends BroadcastReceiver {
    SessionManager sessionManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        sessionManager=new SessionManager(context);
        if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            AppController ctx = (AppController) context.getApplicationContext();
            // is Kiosk Mode active?
            if(sessionManager.isKioskSense()==true) {
                wakeUpDevice(ctx);
            }
        }
    }

    private void wakeUpDevice(AppController context) {
        KeyguardManager myKeyManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);

        PowerManager.WakeLock wakeLock = context.getWakeLock();
        if (wakeLock.isHeld()) {

            wakeLock.release();

            // release old wake lock
        }

        // create a new wake lock...
        wakeLock.acquire();

        // ... and release again
        wakeLock.release();

    }


}