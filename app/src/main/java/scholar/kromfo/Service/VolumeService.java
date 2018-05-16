package scholar.kromfo.Service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import scholar.kromfo.Classes.LockScreenActivity;
import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.Helpers.SettingsContentObserver;

public class VolumeService extends Service {
    private static final long INTERVAL = TimeUnit.SECONDS.toMillis(1); // periodic interval to check in seconds -> 2 seconds
    private static final String TAG = VolumeService.class.getSimpleName();
    private SettingsContentObserver mSettingsContentObserver;
    private SessionManager sessionManager;
    private Thread t = null;
    private Context ctx = null;
    private boolean running = false;

    @Override
    public void onDestroy() {
        Log.i(TAG, "Stopping service 'VolumeService'");
        getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);
        running =false;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Starting service 'VolumeService'");
        running = true;
        ctx = this;
        mSettingsContentObserver = new SettingsContentObserver(this,new Handler());
        getApplicationContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver );

        return Service.START_NOT_STICKY;
    }





    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
