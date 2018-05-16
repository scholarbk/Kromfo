package scholar.kromfo.Classes;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import dmax.dialog.SpotsDialog;
import scholar.kromfo.Helpers.SessionManager;
import scholar.kromfo.R;


public class Register extends Auxilliary {

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    Pinview pinview;
    CountryCodePicker ccp;
    TextView tBefore,tTerms,tOpe,tNot,tSit,tEnt,tPlease,tx_Ex,terms;
    AppCompatEditText etPhone;
    Button sendcode,verifycode,goback;
   SpotsDialog dialog;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ccp=(CountryCodePicker) findViewById(R.id.ccp);
        etPhone = (AppCompatEditText) findViewById(R.id.ed_phone);
        sendcode = (Button) findViewById(R.id.auth_button);
        verifycode = (Button) findViewById(R.id.btn_verify_otp);
        goback = (Button) findViewById(R.id.goback);
        tBefore = (TextView)findViewById(R.id.et_mobileNumberCod);
        tOpe = (TextView)findViewById(R.id.txtoperator);
        tTerms = (TextView)findViewById(R.id.terms);
        tNot = (TextView)findViewById(R.id.txtoperator);
        tPlease = (TextView)findViewById(R.id.txtMed);
        tSit = (TextView)findViewById(R.id.sitback);
        tEnt = (TextView)findViewById(R.id.manualotp);
        terms= (TextView)findViewById(R.id.terms);

        pinview=(Pinview)findViewById(R.id.pinview);
        tx_Ex = (TextView)findViewById(R.id.et_Ex);
        mAuth = FirebaseAuth.getInstance();
        session=new SessionManager(Register.this);
        if(session.isLoggedIn()&&mAuth.getCurrentUser()!=null){
            startActivity(new Intent(Register.this,MainActivity.class));
        }
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newI = new Intent(Intent.ACTION_VIEW);
                newI.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newI.setData(Uri.parse("https://sites.google.com/view/askpharmpivacy/terms-and-conditions"));
                startActivity(newI);
            }
        });
        dialog=new SpotsDialog(Register.this,"Sending Code...");
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;

               dialog.dismiss();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Log.w(TAG, "onVerificationFailed", e);
                dialog.dismiss();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(Register.this,"InValid Phone Number", Toast.LENGTH_SHORT).show();
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(Register.this,"Verification Failed"
                            , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
               dialog.dismiss();
                mVerificationId = verificationId;
                mResendToken = token;
                tx_Ex.setVisibility(View.GONE);
                tBefore.setVisibility(View.GONE);
                sendcode.setVisibility(View.GONE);
                tTerms.setVisibility(View.GONE);
                tOpe.setVisibility(View.GONE);
                tNot.setVisibility(View.GONE);
                ccp.setVisibility(View.GONE);
                tPlease.setVisibility(View.GONE);
                etPhone.setVisibility(View.GONE);
                pinview.setVisibility(View.VISIBLE);
                tSit.setVisibility(View.VISIBLE);
                tEnt.setVisibility(View.VISIBLE);
                verifycode.setVisibility(View.VISIBLE);
                goback.setVisibility(View.VISIBLE);

                // ...
            }
        };

       sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                ccp.registerCarrierNumberEditText(etPhone);

                String phonenumber=ccp.getFullNumberWithPlus();
                if(isOnline()){
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phonenumber,
                            60,
                            java.util.concurrent.TimeUnit.SECONDS,
                            Register.this,
                            mCallbacks);
                }else {
                    dialog.dismiss();
                    Toast.makeText(Register.this,"Check Your Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        });

        verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new SpotsDialog(Register.this,"Verifying...");
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, pinview.getValue().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tBefore.setVisibility(View.VISIBLE);
                sendcode.setVisibility(View.VISIBLE);
                tTerms.setVisibility(View.VISIBLE);
                tOpe.setVisibility(View.VISIBLE);
                tNot.setVisibility(View.VISIBLE);
                ccp.setVisibility(View.VISIBLE);
                tPlease.setVisibility(View.VISIBLE);
                etPhone.setVisibility(View.VISIBLE);
                pinview.setVisibility(View.GONE);
                tSit.setVisibility(View.GONE);
                tEnt.setVisibility(View.GONE);
                verifycode.setVisibility(View.GONE);
                goback.setVisibility(View.GONE);
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "signInWithCredential:success");
                            session.createMobileNumber(etPhone.getText().toString());
                            dialog.dismiss();
                            session.setLogin(true);
                            startActivity(new Intent(Register.this,MainActivity.class));
                            Toast.makeText(Register.this,"Verification Done", Toast.LENGTH_SHORT).show();
                            finish();
                            // ...
                        } else {
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                dialog.dismiss();
                                Toast.makeText(Register.this,"Invalid Verification", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
