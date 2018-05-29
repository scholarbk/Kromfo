package scholar.kromfo.Classes;





import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import scholar.kromfo.Helpers.MotionSensors;
import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.R;


public class Home extends Fragment {

        private SessionManager sessionManager;


    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      final View view = inflater.inflate(R.layout.fragment_home, container, false);
            sessionManager=new SessionManager(getContext());
            if(sessionManager.isLoggedIn()==false){
                startActivity(new Intent(getActivity(),WalkThrough.class));
            }



        return view;


    }


}