package es.miguel.polideportivo_v2.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InterruptedIOException;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.data.ConexionDB;
import es.miguel.polideportivo_v2.dominio.Actividad;
import es.miguel.polideportivo_v2.dominio.Pista;
import es.miguel.polideportivo_v2.dominio.ReservaActividad;

public class ConfirmarReservaActivity extends AppCompatActivity {

    private LinearLayout reservar;
    private ImageView imageView;
    private EditText texto;
    private TextView tv_descripcion, tv_horario, tv_duracion, tv_ubicacion;
    private TextView flecha_izq;
    private LinearLayout layout_capacidad, layout_iluminacion;
    private double precio_pagado = 0;
    private String email, nombre, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_reserva);

        flecha_izq = findViewById(R.id.confirmar_flecha_izq);
        imageView = findViewById(R.id.imageView_confirmar);
        tv_descripcion = findViewById(R.id.tv_confirmar_descripcion);
        tv_horario = findViewById(R.id.tv_confirmar_hora);
        tv_ubicacion = findViewById(R.id.tv_lugar);
        layout_iluminacion = findViewById(R.id.layout_confirmar4);
        reservar = findViewById(R.id.layout_reservar_actividad);
        texto = findViewById(R.id.editText_confirmar);
        tv_duracion = findViewById(R.id.tv_confirmar_duracion);

        flechaVolver();
        if (descripcionPista()) {

            recibirDatosIntentPista();
        } else if (descripcionActividad()) {
            recibirDatosIntentActividad();
        } else {
            // recibirDatosPiscina();
        }

        reservar.setOnClickListener(v -> {
            mostrarToastPersonalizado();
            if (descripcionPista()) {
                guardarDatosPista();
            } else if (descripcionActividad()) {
                guardarDatosActividad();
            }
        });
    }

    public void mostrarToastPersonalizado() {
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_layout, null);
        TextView tvMensaje = toastLayout.findViewById(R.id.tvMensaje);
        tvMensaje.setText("Reserva realizada con éxito!");

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }

    public void recibirDatosIntentPista() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID_RESERVA",0);
        String imagen = intent.getStringExtra("IMAGEN_RESERVA");
        String hora = intent.getStringExtra("HORARIO_RESERVA");
        String descripcion = intent.getStringExtra("DESCRIPCION_RESERVA");
        String ubicacion = intent.getStringExtra("UBICACION_RESERVA");
        int capacidad = intent.getIntExtra("CAPACIDAD", 0);
        Glide.with(this)
                .load(imagen)
                .into(imageView);

        tv_horario.setText(hora);
        tv_ubicacion.setText(ubicacion);
        tv_duracion.setText("TIEMPO: 90'");

        ConexionDB.getPista(id, new ConexionDB.ResultadoPistaCallback() {

            @Override
            public void onResultadoPista(Pista p) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // aquí puedes actualizar la interfaz de usuario con los datos de la actividad
                        texto.setText(p.getDescripcion());
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                // Aquí puedes manejar errores relacionados con la obtención de la actividad
            }
        });

        seleccionarIluminacion();
    }

    public void recibirDatosIntentActividad() {
        layout_iluminacion.setVisibility(View.GONE);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID_RESERVA_ACTIVIDAD", 0);
        String imagen = intent.getStringExtra("IMAGEN_RESERVA_ACTIVIDAD");
        String hora = intent.getStringExtra("HORARIO_RESERVA_ACTIVIDAD");
        String ubicacion = intent.getStringExtra("UBICACION_RESERVA_ACTIVIDAD");
        String fechaStr = intent.getStringExtra("FECHA_RESERVA_ACTIVIDAD");

        LocalDateTime fecha = LocalDateTime.parse(fechaStr);
        Timestamp timestamp = Timestamp.valueOf(fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Glide.with(this)
                .load(imagen)
                .into(imageView);

        tv_horario.setText(hora);
        tv_ubicacion.setText(ubicacion);
        tv_duracion.setText("TIEMPO: 60'");

        ConexionDB.getActividad(id, new ConexionDB.ResultadoActividadCallback() {

            @Override
            public void onResultadoActividad(Actividad a) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // aquí puedes actualizar la interfaz de usuario con los datos de la actividad
                        texto.setText(a.getDescripcion());
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                // Aquí puedes manejar errores relacionados con la obtención de la actividad
            }
        });
    }

    public void flechaVolver() {
        flecha_izq.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), InicioActivity.class);
            intent.putExtra("EMAIL_MAIN", email);
            intent.putExtra("DESCRIPCION_ACTIVIDAD", descripcion);
            v.getContext().startActivity(intent);
        });
    }

    private void guardarDatosPista() {
        // Obtener la instancia de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        int id_pista = intent.getIntExtra("ID_PISTA_RESERVA", 0);
        String imagen = intent.getStringExtra("IMAGEN_RESERVA");
        String hora = intent.getStringExtra("HORARIO_RESERVA");
        String descripcion = intent.getStringExtra("DESCRIPCION_RESERVA");
        String ubicacion = intent.getStringExtra("UBICACION_RESERVA");
        int capacidad = intent.getIntExtra("CAPACIDAD", 0);
        email = intent.getStringExtra("EMAIL_RESERVA");
        String fechaStr = intent.getStringExtra("FECHA_RESERVA");

        LocalDateTime fecha = LocalDateTime.parse(fechaStr);
        Timestamp timestamp = Timestamp.valueOf(fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Crear un mapa con los atributos del usuario
        Map<String, Object> user = new HashMap<>();
        user.put("fecha_reserva", timestamp);
        user.put("horario_reservado", hora);
        user.put("precio_pagado", precio_pagado);
        user.put("id_pista", id_pista);
        user.put("id_cliente", email);

        // Añadir el documento a Firestore (la colección "usuarios" no tiene por qué existir previamente)
        db.collection("ReservaPista").add(user)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Documento creado con ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error al crear el documento", e));
    }

    private void guardarDatosActividad() {
        // Obtener la instancia de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        int id_actividad = intent.getIntExtra("ID_RESERVA_ACTIVIDAD", 0);
        String hora = intent.getStringExtra("HORARIO_RESERVA_ACTIVIDAD");
        email = intent.getStringExtra("EMAIL_RESERVA_ACTIVIDAD");
        String fechaStr = intent.getStringExtra("FECHA_RESERVA_ACTIVIDAD");

        LocalDateTime fecha = LocalDateTime.parse(fechaStr);
        Timestamp timestamp = Timestamp.valueOf(fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Crear un mapa con los atributos del usuario
        Map<String, Object> user = new HashMap<>();
        user.put("fecha_reserva", timestamp);
        user.put("horario_reservado", hora);
        user.put("id_actividad", id_actividad);
        user.put("id_cliente", email);

        // Añadir el documento a Firestore (la colección "usuarios" no tiene por qué existir previamente)
        db.collection("ReservaActividad").add(user)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Documento creado con ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error al crear el documento", e));
    }

    public void seleccionarIluminacion() {
        TextView con_iluminacion, sin_iluminacion;
        precio_pagado = 0;
        con_iluminacion = findViewById(R.id.tv_con_iluminación);
        sin_iluminacion = findViewById(R.id.tv_sin_iluminación);

        con_iluminacion.setOnClickListener(v -> {
            precio_pagado = 12.00;
            con_iluminacion.setBackgroundColor(getResources().getColor(R.color.teal_200));
            sin_iluminacion.setBackgroundColor(getResources().getColor(R.color.white));
        });

        sin_iluminacion.setOnClickListener(v -> {
            precio_pagado = 9.00;
            con_iluminacion.setBackgroundColor(getResources().getColor(R.color.white));
            sin_iluminacion.setBackgroundColor(getResources().getColor(R.color.teal_200));
        });
    }

    public boolean descripcionPista() {
        Intent intent = getIntent();
        descripcion = intent.getStringExtra("DESCRIPCION_RESERVA");
        if (descripcion != null) {
            if (descripcion.equalsIgnoreCase("Pádel")
                    || descripcion.equalsIgnoreCase("Tenis")
                    || descripcion.equalsIgnoreCase("Baloncesto")
                    || descripcion.equalsIgnoreCase("Fútbol sala")) {
                return true;
            }
        }
        return false;
    }

    public boolean descripcionActividad() {
        Intent intent = getIntent();
        descripcion = intent.getStringExtra("NOMBRE_RESERVA_ACTIVIDAD");
        if (descripcion != null) {
            if (descripcion.equalsIgnoreCase("Spinning")
                    || descripcion.equalsIgnoreCase("Zumba")
                    || descripcion.equalsIgnoreCase("Pilates")
                    || descripcion.equalsIgnoreCase("Boxeo")
                    || descripcion.equalsIgnoreCase("Karate")
                    || descripcion.equalsIgnoreCase("Natación")
                    || descripcion.equalsIgnoreCase("Waterpolo")
                    || descripcion.equalsIgnoreCase("AquaGim")
                    || descripcion.equalsIgnoreCase("Sincronizada")) {
                return true;
            }
        }
        return false;
    }

}