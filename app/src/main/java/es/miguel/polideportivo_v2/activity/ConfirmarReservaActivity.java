package es.miguel.polideportivo_v2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.SQLOutput;

import es.miguel.polideportivo_v2.R;

public class ConfirmarReservaActivity extends AppCompatActivity {

    private LinearLayout reservar;
    ImageView imageView;
    TextView tv_descripcion,tv_horario,tv_capacidad,tv_ubicacion;
    TextView flecha_izq;
    LinearLayout  layout_capacidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_reserva);

        flecha_izq = findViewById(R.id.confirmar_flecha_izq);
        imageView = findViewById(R.id.imageView_confirmar);
        tv_descripcion = findViewById(R.id.tv_confirmar_descripcion);
        tv_horario = findViewById(R.id.tv_confirmar_hora);
        tv_capacidad = findViewById(R.id.tv_confirmar_capacidad);
        tv_ubicacion = findViewById(R.id.tv_lugar);
        layout_capacidad = findViewById(R.id.confirmar_capacidad);

        recibirDatosIntent();
        mostrarToastPersonalizado();
        flechaVolver();
        quitarCapacidad();


    }

    public void mostrarToastPersonalizado(){
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_layout, null);
        TextView tvMensaje = toastLayout.findViewById(R.id.tvMensaje);
        tvMensaje.setText("Reserva realizada con éxito!");

        reservar = findViewById(R.id.layout_reservar_actividad);

        reservar.setOnClickListener(v ->{
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);
            toast.show();

        });
    }

    public void recibirDatosIntent(){
        Intent intent = getIntent();
        int imagen = intent.getIntExtra("IMAGEN_RESERVA",0);
        String hora = intent.getStringExtra("HORARIO_RESERVA");
        String descripcion = intent.getStringExtra("DESCRIPCION_RESERVA");
        String ubicacion = intent.getStringExtra("UBICACION_RESERVA");
        int capacidad = intent.getIntExtra("CAPACIDAD",0);

        imageView.setImageResource(imagen);
        tv_horario.setText(hora);
        tv_descripcion.setText(descripcion);
        tv_capacidad.setText(""+capacidad);
        tv_ubicacion.setText(ubicacion);
    }

    public void flechaVolver(){
        flecha_izq.setOnClickListener( v ->{
            Intent intent = new Intent(v.getContext(),InicioActivity.class);
            v.getContext().startActivity(intent);

        });
    }

    public void quitarCapacidad(){
        Intent intent = getIntent();
        String descripcion = intent.getStringExtra("DESCRIPCION_RESERVA");

        if(descripcion.equalsIgnoreCase("Pádel")
                || descripcion.equalsIgnoreCase("Tenis")
                || descripcion.equalsIgnoreCase("Baloncesto")
                || descripcion.equalsIgnoreCase("Fútbol sala")){

            layout_capacidad.setVisibility(View.GONE);
        }
    }
}