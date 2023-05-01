package es.miguel.polideportivo_v2.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.miguel.polideportivo_v2.R;

public class MainActivity extends AppCompatActivity {


    private TextView email,password,registrarse,recuperarPass;
    private Button login;
    private ImageView btn_google;
    private int GOOGLE_SIGN_IN = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_google = findViewById(R.id.iv_google);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registrarse = findViewById(R.id.registrarse);
        login = findViewById(R.id.btnLogin);
        recuperarPass = findViewById(R.id.forgotpass);

         entrarApp();
         entrarAppConGoogle();;
         registrarse();
         recuperarPass();

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("message", "Integración Firebase completa");
        analytics.logEvent("InitScreen", bundle);


    }

    public void entrarApp(){
        login.setOnClickListener( v -> {

           /* if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {*/


                        Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                       intent.putExtra("EMAIL_MAIN", email.getText().toString());
                        startActivity(intent);
                 //   }

               });
        /*    }
        });*/
    }

    public void registrarse(){
        registrarse.setOnClickListener( v ->{

          Intent intent = new Intent(this, RegistroActivity.class);
          startActivity(intent);

        });
    }

    public void recuperarPass(){
        recuperarPass.setOnClickListener( v -> {


        });

    }

    public void salirApp(){

        SharedPreferences preferences = getSharedPreferences(getString(R.string.archivo_preferencias), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        FirebaseAuth.getInstance().signOut();
        onBackPressed();

    }

    public void entrarAppConGoogle(){

        btn_google.setOnClickListener( v ->{
            SharedPreferences preferencias = getSharedPreferences(getString(R.string.archivo_preferencias), Context.MODE_PRIVATE);
            String email = preferencias.getString("EMAIL",null);

            GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

            GoogleSignInClient googleClient = GoogleSignIn.getClient(this,googleConf);
            startActivityForResult(googleClient.getSignInIntent(),GOOGLE_SIGN_IN);
             googleClient.signOut();
            if(email != null){

                Intent intent = new Intent(this, InicioActivity.class);
                intent.putExtra("EMAIL_MAIN", email);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleSignInAccount account = null;
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                account = task.getResult(ApiException.class);
            } catch (ApiException e) {
                e.printStackTrace();
            }

            if(account!= null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(this, t ->{
                    if(t.isSuccessful()){

                        Intent intent = new Intent(this, InicioActivity.class);
                        intent.putExtra("EMAIL_MAIN", email.getText().toString());
                        startActivity(intent);
                    }
                });
            }
        }
    }


}