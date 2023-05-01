package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.miguel.polideportivo_v2.R;

public class InicioActivity extends AppCompatActivity {


     private boolean seleccionaPista,seleccionaGim,seleccionaPiscina;
     private LinearLayout reservaPista,actividadGim,actividadPiscina,centro,horario,misReservas;
     private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

       //toastPersonalizado();

        informacionCentro();
        informacionHorario();
        recibirDatos();
        reservarPista();
        reservarGim();
        reservarPiscina();
        misReservas();



    }

    public void reservarPista(){
        reservaPista = findViewById(R.id.cv_reserva_pistas);
        reservaPista.setOnClickListener( v -> {
            recibirDatos();
            seleccionaPista = true;
            seleccionaGim = false;
            seleccionaPiscina=false;
            Intent intent = new Intent(InicioActivity.this,SeleccionarActividadActivity.class);
            intent.putExtra("PISTA",seleccionaPista);
            intent.putExtra("GIM",seleccionaGim);
            intent.putExtra("PISCINA",seleccionaPiscina);
            intent.putExtra("EMAIL_INICIO",email);
            startActivity(intent);
        });
    }
    public void reservarGim(){
        actividadGim = findViewById(R.id.cv_reserva_gim);
        actividadGim.setOnClickListener(v -> {
            seleccionaPista = false;
            seleccionaGim = true;
            seleccionaPiscina=false;
            Intent intent = new Intent(InicioActivity.this, SeleccionarActividadActivity.class);
            intent.putExtra("PISTA",seleccionaPista);
            intent.putExtra("GIM",seleccionaGim);
            intent.putExtra("PISCINA",seleccionaPiscina);
            intent.putExtra("EMAIL_INICIO",email);
            System.out.println("emailiniciommmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm" + email);
            startActivity(intent);
        });
    }
    public void reservarPiscina(){
        actividadPiscina = findViewById(R.id.cv_reserva_piscina);
        actividadPiscina.setOnClickListener(v -> {
            seleccionaPista = false;
            seleccionaGim = false;
            seleccionaPiscina=true;
            recibirDatos();
            Intent intent = new Intent(InicioActivity.this,SeleccionarActividadActivity.class);
            intent.putExtra("PISTA",seleccionaPista);
            intent.putExtra("GIM",seleccionaGim);
            intent.putExtra("PISCINA",seleccionaPiscina);
            intent.putExtra("EMAIL_INICIO",email);
            startActivity(intent);
        });
    }

    public void misReservas(){
        misReservas = findViewById(R.id.cv_mis_reservas);
        misReservas.setOnClickListener( v->{
            recibirDatos();
            Intent intent = new Intent(InicioActivity.this,MisReservasActivity.class);
            intent.putExtra("EMAIL_INICIO",email);
            startActivity(intent);
        });

    }
    public void informacionCentro(){
        centro = findViewById(R.id.cv_centro);
        centro.setOnClickListener( v ->{
            Intent intent = new Intent(this,CentroActivity.class);
            startActivity(intent);
        });
    }
    public void informacionHorario(){
        horario = findViewById(R.id.cv_horarios);
        horario.setOnClickListener( v ->{
            Intent intent = new Intent (this,HorarioActivity.class);
            startActivity(intent);
        });
    }

    public void registrarse(){

    }

    public void toastPersonalizado(){
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_layout, null);
        TextView tvMensaje = toastLayout.findViewById(R.id.tvMensaje);
        Intent intent = getIntent();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        String nombre = usuario.getDisplayName();
        tvMensaje.setText("Bienvenido " + nombre);


            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);
            toast.show();
    }

    public void recibirDatos(){
        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL_MAIN");
    }
}