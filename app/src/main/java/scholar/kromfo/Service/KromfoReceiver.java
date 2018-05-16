package scholar.kromfo.Service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.util.List;

import scholar.kromfo.Classes.LockScreenActivity;
import scholar.kromfo.Classes.MainActivity;
import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class KromfoReceiver extends BroadcastReceiver {
        SessionManager sessionManager;
    @Override
    public void onReceive(Context context , Intent intent) {
       sessionManager=new SessionManager(context);
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
                if(isNotifyMeOn()){
                    notification(context);
                }


        }
        else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {

            if(sessionManager.isChargnSense()==true){
                Intent intent1=new Intent(context,LockScreenActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);

            }
        }

    }
    public boolean isNotifyMeOn(){
        if(!sessionManager.isNotifyMeSense()==true){
            return  false;
        }
        return true;
    }
        public void notification(Context context){

            int mNotificationId = 001;


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle("KromFo App")
                            .setContentText("Would You Like To Enable Charger Removed Anti-Theft Mode?")
                            .setOngoing(true)
                            .setAutoCancel(true);
            mBuilder.setVibrate(new long[] { 1000, 1000});
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


            mBuilder.setContentIntent(contentIntent);


            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}