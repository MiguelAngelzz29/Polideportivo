package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.miguel.polideportivo_v2.R;

public class HorarioActivity extends AppCompatActivity {

    private TextView flecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        flecha = findViewById(R.id.horario_flecha_izq);

        flecha.setOnClickListener( v ->{
            Intent intent = new Intent(this,InicioActivity.class);
            startActivity(intent);
        });


    }
}
