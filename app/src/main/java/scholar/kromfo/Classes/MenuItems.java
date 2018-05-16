package scholar.kromfo.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.R;


public class MenuItems extends Fragment {
    private Switch chargingSwitch,bootSwitch;
    SessionManager sessionManager;
    public static MenuItems newInstance() {
       MenuItems fragment = new MenuItems();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.menu_items, container, false);
        sessionManager=new SessionManager(getContext());
        chargingSwitch=(Switch)view.findViewById(R.id.chargingSwitch);
        bootSwitch=(Switch)view.findViewById(R.id.bootSwitch);
        if(sessionManager.isChargnSense()==true){
            chargingSwitch.setChecked(true);
        }else{
            chargingSwitch.setChecked(false);
        }
        if (chargingSwitch != null) {
            chargingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        sessionManager.setChargeSense(true);
                    } else {
                        sessionManager.setChargeSense(false);
                    }
                }
            });
        }

        if(sessionManager.isBootSense()==true){
            bootSwitch.setChecked(true);
        }else{
            bootSwitch.setChecked(false);
        }
        if ( bootSwitch != null) {
            bootSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        sessionManager.setBootSense(true);
                    } else {
                        sessionManager.setBootSense(false);
                    }
                }
            });
        }
        return view;
    }

}
