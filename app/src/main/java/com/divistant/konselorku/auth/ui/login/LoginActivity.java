package com.divistant.konselorku.auth.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.divistant.konselorku.MainActivity;
import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.signup.FinishEdu;
import com.divistant.konselorku.auth.ui.signup.FinishSignup;
import com.divistant.konselorku.ui.intro.IntroActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    ProgressBar loadingProgressbar;
    EditText uname, passwd;
    Button signIn;
    SignInButton signInWithGoogle;
    private static int RC_SIGN_IN=9001;
    FirebaseAuth fAuth;
    GoogleSignInClient mGSC;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser =fAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingProgressbar = (ProgressBar) findViewById(R.id.loading);
        uname = (EditText) findViewById(R.id.username);
        passwd = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.login);
        signInWithGoogle = (SignInButton) findViewById(R.id.sign_in_google);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    final Intent i = new Intent(LoginActivity.this, IntroActivity.class);

                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });
        // Start the thread
        t.start();

        fAuth = FirebaseAuth.getInstance();

        //SIGN IN WITH EMAIL AND PASSWORD CODE
        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                doLogin(uname.getText().toString(), passwd.getText().toString());
            }
        });

        // SIGN IN WITH GOOGLE CODE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGSC = GoogleSignIn.getClient(this,gso);


        signInWithGoogle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                doLogIn();
            }
        });

    }

    /**
     * LOGIN WITH GOOGLE
     * showing choose account pop up for sign in with google, then call onActivityResult as result
     * from choose account and login
     */
    private void doLogIn(){
        Intent i = mGSC.getSignInIntent();
        Log.e("[LOGIN]","Button Pressed!");
        startActivityForResult(i,RC_SIGN_IN);
    }

    /**
     * SIGN IN WITH EMAIL AND PASSWORD
     * @param email user email
     * @param password user password
     */
    private void doLogin(String email, String password){
        fAuth.signInWithEmailAndPassword(uname.getText().toString(), passwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = fAuth.getCurrentUser();
                            doLogin(user.getEmail());
//                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            finish();
                        }else{
                            Log.e("[FAUTH]","Auth Error");
                        }
                    }
                });
    }


    /**
     * Result for sign in with google
     * Get google account token then auth with that token with authWithGoogleMethod
     * @param requestCode google service request code
     * @param resultCode google service result code
     * @param data google choose account intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           try {
               GoogleSignInAccount account = task.getResult(ApiException.class);
               Log.d("[AUTH]","FAuth " + account.getId());
               authWithGoogle(account.getIdToken());
           }catch (ApiException e){
               if(e.getStatusCode() == 12500){
                   Toast.makeText(getApplicationContext(),"Please update google play services",Toast.LENGTH_LONG).show();
               }
               e.printStackTrace();
           }
        }
    }

    /**
     * ACTUALLY LOGIN WITH GOOGLE
     * @param idToken token from google choose account
     */
    private void authWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = fAuth.getCurrentUser();
                    doLogin(user.getEmail());
                    finish();
                }else{
                    Log.e("[FAUTH]","Auth Error");
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("[LOGIN]", "Connection Error");
    }

    private void doLogin(String email){
        final LoginInterface service = LoginApi.getClient().create(LoginInterface.class);
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("email",email);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParam)).toString());
        Call<UserModel> call = service.doLogin(body);
        call.enqueue(new Callback<UserModel>(){
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.e("[AUTH]", String.valueOf(response.code()));

                final UserModel user = response.body();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("TOKEN","Bearer " + user.getToken());
                editor.putString("UPROGRESS",user.getProgress());


                if(user.getProgress().equals("1")){
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), FinishSignup.class));
                    finish();
                }else if(user.getProgress().equals("2")){
                    editor.putString("ROLE", user.getRole_code());
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), FinishEdu.class));
                    finish();
                }else{
                    editor.putString("ROLE", user.getRole_code());
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("[AUTH ERR]", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
