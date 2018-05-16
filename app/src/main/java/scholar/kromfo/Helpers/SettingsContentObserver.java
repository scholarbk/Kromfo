package scholar.kromfo.Helpers;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;

import java.util.logging.Logger;

public class SettingsContentObserver extends ContentObserver {
    int previousVolume;
    Context context;
    private static final String TAG = SettingsContentObserver.class.getSimpleName();
    public SettingsContentObserver(Context context, Handler handler) {
        super(handler);
       this.context=context;

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

        int delta=previousVolume-currentVolume;

        if(delta>0)
        {
            Log.i(TAG, "Stopping Volume 'VolumeService'");
            previousVolume=currentVolume;


            audio.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                    0);
        }
        else if(delta<0)
        {
            Log.i(TAG, "Increase Volume 'VolumeService'");
            previousVolume=currentVolume;
        }
    }
}
