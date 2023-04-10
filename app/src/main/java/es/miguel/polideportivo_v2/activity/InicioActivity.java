package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import es.miguel.polideportivo_v2.R;

public class InicioActivity extends AppCompatActivity {

     private ImageView reservaPista,actividadGim,actividadPiscina;
     private boolean seleccionaPista,seleccionaGim,seleccionaPiscina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //RESERVA PISTA
        reservaPista = findViewById(R.id.IvReservaPista);
        reservaPista.setOnClickListener( v -> {
                seleccionaPista = true;
                seleccionaGim = false;
                seleccionaPiscina=false;
                Intent intent = new Intent(InicioActivity.this,ReservaActivity.class);
                intent.putExtra("PISTA",seleccionaPista);
                intent.putExtra("GIM",seleccionaGim);
                intent.putExtra("PISCINA",seleccionaPiscina);
                startActivity(intent);

        });

        //RESERVA GIM
        actividadGim = findViewById(R.id.IvReservaGim);
        actividadGim.setOnClickListener(v -> {
            seleccionaPista = false;
            seleccionaGim = true;
            seleccionaPiscina=false;
                Intent intent = new Intent(InicioActivity.this, ReservaActivity.class);
                 intent.putExtra("PISTA",seleccionaPista);
                 intent.putExtra("GIM",seleccionaGim);
                 intent.putExtra("PISCINA",seleccionaPiscina);
                 startActivity(intent);

        });


       //RESERVA PISCINA
        actividadPiscina = findViewById(R.id.IvReservaPiscina);
        actividadPiscina.setOnClickListener(v -> {
            seleccionaPista = false;
            seleccionaGim = false;
            seleccionaPiscina=true;
            Intent intent = new Intent(InicioActivity.this,ReservaActivity.class);
            intent.putExtra("PISTA",seleccionaPista);
            intent.putExtra("GIM",seleccionaGim);
            intent.putExtra("PISCINA",seleccionaPiscina);
            startActivity(intent);
        });
    }
}