package es.miguel.polideportivo_v2.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.miguel.polideportivo_v2.R;

public class ConfirmarReservaActivity extends AppCompatActivity {

    private LinearLayout reservar;
    private ImageView imageView;
    private TextView tv_descripcion,tv_horario,tv_capacidad,tv_ubicacion;
    private TextView flecha_izq;
    private LinearLayout  layout_capacidad;
    private double precio_pagado = 0;

    private BroadcastReceiver miBroadcastReceiver;
    private String email;

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
        reservar = findViewById(R.id.layout_reservar_actividad);


        recibirDatosIntent();
        flechaVolver();
        quitarCapacidad();
        seleccionarIluminacion();


        reservar.setOnClickListener(v -> {
            mostrarToastPersonalizado();
            guardarDatos();
        });





    }

    public void mostrarToastPersonalizado(){
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_layout, null);
        TextView tvMensaje = toastLayout.findViewById(R.id.tvMensaje);
        tvMensaje.setText("Reserva realizada con éxito!");

            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(toastLayout);
            toast.show();


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
        tv_capacidad.setText(capacidad+"");
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


        private void guardarDatos() {
            // Obtener la instancia de Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Intent intent = getIntent();
            int id_pista = intent.getIntExtra("ID_PISTA_RESERVA",0);
            int imagen = intent.getIntExtra("IMAGEN_RESERVA",0);
            String hora = intent.getStringExtra("HORARIO_RESERVA");
            String descripcion = intent.getStringExtra("DESCRIPCION_RESERVA");
            String ubicacion = intent.getStringExtra("UBICACION_RESERVA");
            int capacidad = intent.getIntExtra("CAPACIDAD",0);
            String email = intent.getStringExtra("EMAIL_RESERVA");


            GregorianCalendar fecha = new GregorianCalendar();
            Instant instant = fecha.toInstant();
            Timestamp timestamp = new Timestamp(instant.toEpochMilli());


            // Crear un mapa con los atributos del usuario
            Map<String, Object> user = new HashMap<>();
            user.put("fecha_reserva", timestamp);
            user.put("horario_reservado", hora );
            user.put("precio_pagado", precio_pagado);
            user.put("id_pista", id_pista );
            user.put("id_cliente", email);



            // Añadir el documento a Firestore (la colección "usuarios" no tiene por qué existir previamente)
            db.collection("ReservaPista").add(user)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "Documento creado con ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error al crear el documento", e));
        }

    public void seleccionarIluminacion(){
        TextView con_iluminacion,sin_iluminacion;
        precio_pagado = 0;
        con_iluminacion = findViewById(R.id.tv_con_iluminación);
        sin_iluminacion = findViewById(R.id.tv_sin_iluminación);

        con_iluminacion.setOnClickListener( v -> {

            precio_pagado = 12.00;
            con_iluminacion.setBackgroundColor(getResources().getColor(R.color.teal_200));
            sin_iluminacion.setBackgroundColor(getResources().getColor(R.color.white));
        });

        sin_iluminacion.setOnClickListener( v -> {

            precio_pagado = 9.00;
            con_iluminacion.setBackgroundColor(getResources().getColor(R.color.white));
            sin_iluminacion.setBackgroundColor(getResources().getColor(R.color.teal_200));
        });

    }

}