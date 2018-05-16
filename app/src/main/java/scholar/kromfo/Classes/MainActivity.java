package scholar.kromfo.Classes;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.R;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private SessionManager session;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        session=new SessionManager(MainActivity.this);
        if(!session.isLoggedIn()&&mAuth.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,Register.class));
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, Home.newInstance());
        transaction.commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment currentFragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        currentFragment = Home.newInstance();
                        break;
                    case R.id.menus:
                        currentFragment = MenuItems.newInstance();
                        break;
                    case R.id.settings:
                        currentFragment = SettingsFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, currentFragment);
                transaction.commit();
                return true;
            }
        });
    }
}