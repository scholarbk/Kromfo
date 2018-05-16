package scholar.kromfo.Classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.R;
import scholar.kromfo.Service.KromfoReceiver;
import scholar.kromfo.Service.KromfoService;


public class SettingsFragment extends Fragment {
        private RelativeLayout relativeLayout;
        SessionManager sessionManager;
    private Switch notSwitch;
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmen_settings, container, false);
        sessionManager=new SessionManager(getContext());
       notSwitch=(Switch)view.findViewById(R.id.notSwitch);

                relativeLayout=(RelativeLayout)view.findViewById(R.id.r1);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(),PasswordActivity.class));
                    }
                });
        if(sessionManager.isNotifyMeSense()==true){
            notSwitch.setChecked(true);
        }else{
            notSwitch.setChecked(false);
        }
        if (notSwitch != null) {
            notSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        sessionManager.setNotifyMeCharging(true);
                    } else {
                        sessionManager.setNotifyMeCharging(false);
                    }
                }
            });
        }
        return view;
    }

}
