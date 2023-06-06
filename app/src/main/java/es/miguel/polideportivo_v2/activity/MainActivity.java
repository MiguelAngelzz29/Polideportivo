package es.miguel.polideportivo_v2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.data.ConexionDB;
import es.miguel.polideportivo_v2.dominio.Cliente;

public class MainActivity extends AppCompatActivity {

    private TextView email,password,registrarse;
    private Button login;
    private ImageView btn_google;
    private int GOOGLE_SIGN_IN = 100;

    // Crea una variable booleana para indicar si se han escrito datos en ambos campos
    private boolean camposLlenos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_google = findViewById(R.id.iv_google);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registrarse = findViewById(R.id.registrarse);
        login = findViewById(R.id.btnLogin);

        // Agrega un escuchador a los campos email y password
        email.addTextChangedListener(loginTextWatcher);
        password.addTextChangedListener(loginTextWatcher);

        // Actualiza el color del botón cuando se crea la actividad
        updateButtonColor();
        entrarAppConGoogle();
        registrarse();
    }

    // Crea un TextWatcher para actualizar el color del botón en función de si los campos están vacíos o no
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateButtonColor();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // Actualiza el color del botón según las condiciones establecidas
    private void updateButtonColor() {
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            // Si hay campos vacíos, se establece el color gris
            login.setTextColor(getResources().getColor(R.color.black));
            camposLlenos = false;
        }else {
            // Si ambos campos tienen datos, se establece el color teal_200
            login.setTextColor(getResources().getColor(R.color.teal_200));
            camposLlenos = true;
            entrarApp();
        }
    }

    public void entrarApp(){
        login.setOnClickListener(v -> {
           ConexionDB.getCliente(email.getText().toString(), new ConexionDB.ResultadoClienteCallback() {
                @Override
                public void onResultadoCliente(Cliente cliente) {
                    if(cliente != null && email.getText().toString().equals(cliente.getEmail())  &&
                            password.getText().toString().equals(cliente.getPassword())){

                        Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                        intent.putExtra("EMAIL_MAIN", email.getText().toString());
                        startActivity(intent);
                   }else{
                        Toast.makeText(MainActivity.this, "Credenciales incorrectas",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onError(Throwable t) {

                }
            });
        });
    }

    public void registrarse(){
        registrarse.setOnClickListener( v ->{

            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);

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