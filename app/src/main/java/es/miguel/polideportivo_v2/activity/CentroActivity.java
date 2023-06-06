package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.miguel.polideportivo_v2.R;

public class CentroActivity extends AppCompatActivity {

    private TextView flecha;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centro);

        flecha = findViewById(R.id.centro_flecha_izq);
        intent = new Intent(this, InicioActivity.class);

        flecha.setOnClickListener(v -> irAInicio());
    }

    public void setFlecha(TextView flecha) {
        this.flecha = flecha;
    }

    public void irAInicio() {
        startActivity(intent);
    }
}