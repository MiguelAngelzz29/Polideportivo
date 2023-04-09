package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import es.miguel.polideportivo_v2.R;

public class InicioActivity extends AppCompatActivity {

     ImageView reservaPista,actividadGim,actividadPiscina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //RESERVA PISTA
        reservaPista = findViewById(R.id.IvReservaPista);
        reservaPista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this,ReservaPistasActivity.class);
                startActivity(intent);
            }
        });

        //RESERVA GIM
        actividadGim = findViewById(R.id.IvReservaGim);
        actividadGim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this,ReservaGimActivity.class);
                startActivity(intent);
            }
        });



       //RESERVA PISCINA
        actividadPiscina = findViewById(R.id.IvReservaPiscina);
    }
}