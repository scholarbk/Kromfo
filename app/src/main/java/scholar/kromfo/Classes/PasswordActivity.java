package scholar.kromfo.Classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.R;

public class PasswordActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TextView textView,textView2;
    Button button;
    Pinview pinview,pinview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        pinview=(Pinview)findViewById(R.id.otpview);
        textView=(TextView)findViewById(R.id.txtMes);
        pinview2=(Pinview)findViewById(R.id.otpview1);
        textView2=(TextView)findViewById(R.id.txtMes1);
        button=(Button)findViewById(R.id.btn_finish);

        sessionManager=new SessionManager(PasswordActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pinview.getValue().matches("0000")){
                    Toast.makeText(PasswordActivity.this,"You Cannot Use 0000",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PasswordActivity.this,PasswordActivity.class));
                }else{
                    if(pinview.getValue().matches(pinview2.getValue())){
                        sessionManager.createOTP(pinview.getValue());
                        Toast.makeText(PasswordActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PasswordActivity.this,MainActivity.class));

                    }else{
                        Toast.makeText(PasswordActivity.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PasswordActivity.this,PasswordActivity.class));
                    }
                }


            }
        });
    }
}
