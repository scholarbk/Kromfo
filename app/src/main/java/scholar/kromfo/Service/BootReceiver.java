package scholar.kromfo.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import scholar.kromfo.Classes.LockScreenActivity;
import scholar.kromfo.Helpers.SessionManager;

public class BootReceiver extends BroadcastReceiver {
    SessionManager sessionManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        sessionManager=new SessionManager(context);
        if(sessionManager.isBootSense()){
            Intent myIntent = new Intent(context, LockScreenActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }else{

        }


    }
}
