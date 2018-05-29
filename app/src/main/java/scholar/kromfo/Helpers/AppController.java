package scholar.kromfo.Helpers;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

import scholar.kromfo.Service.KromfoService;
import scholar.kromfo.Service.OnScreenOffReceiver;

public class AppController extends Application {

    private AppController instance;
    private PowerManager.WakeLock wakeLock;
    private OnScreenOffReceiver onScreenOffReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerKioskModeScreenOffReceiver();
        startKioskService();
       startMotionSensor();

    }

    private void startMotionSensor() {
        startService(new Intent(this, MotionSensors.class));
    }

    private void registerKioskModeScreenOffReceiver() {
        // register screen off receiver
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        onScreenOffReceiver = new OnScreenOffReceiver();
        registerReceiver(onScreenOffReceiver, filter);
    }

    public PowerManager.WakeLock getWakeLock() {
        if(wakeLock == null) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup");
        }
        return wakeLock;
    }

    private void startKioskService() { // ... and this method
        startService(new Intent(this, KromfoService.class));
    }

}