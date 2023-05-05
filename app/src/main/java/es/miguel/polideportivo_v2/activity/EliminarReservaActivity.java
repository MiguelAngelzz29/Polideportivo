package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.data.ConexionDB;
import es.miguel.polideportivo_v2.dominio.Actividad;
import es.miguel.polideportivo_v2.dominio.Pista;

public class EliminarReservaActivity extends AppCompatActivity {

    private LinearLayout reservar;
    private ImageView imageView;
    private EditText texto;
    private TextView tv_descripcion, tv_horario, tv_duracion, tv_ubicacion,tv_nombre;
    private TextView flecha_izq;
    private LinearLayout layout_capacidad, layout_iluminacion;
    private double precio_pagado = 0;
    private String email, nombre, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_reserva);

        flecha_izq = findViewById(R.id.confirmar_flecha_izq);
        imageView = findViewById(R.id.imageView_confirmar);
        tv_descripcion = findViewById(R.id.tv_confirmar_descripcion);
        tv_horario = findViewById(R.id.tv_confirmar_hora);
        tv_ubicacion = findViewById(R.id.tv_lugar);
        reservar = findViewById(R.id.layout_reservar_actividad);
        texto = findViewById(R.id.editText_confirmar);
        tv_duracion = findViewById(R.id.tv_confirmar_duracion);

        flechaVolver();
        if(descripcionPista()) {
            recibirDatosIntentPista();
        } else{
                recibirDatosIntentActividad();
            }

        reservar.setOnClickListener(v -> {

            if (descripcionPista()) {
                eliminarReservaPista();
                mostrarToastPersonalizado();
            }else{
                eliminarReservaActividad();
                mostrarToastPersonalizado();
            }
        });


    }

    public void eliminarReservaPista(){
        Intent intent = getIntent();
       String id_reserva = intent.getStringExtra("ID_RESERVA");
       ConexionDB.eliminarReservaPista(id_reserva);

    }
    public void eliminarReservaActividad(){
        Intent intent = getIntent();
        String id_reserva = intent.getStringExtra("ID_RESERVA_ACT");
        ConexionDB.eliminarReservaActividad(id_reserva);

    }
    public void recibirDatosIntentPista() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID_PISTA",0);
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
        tv_descripcion.setText(descripcion);

        // Conectar a la base de datos para recibir la descripción larga de la pista.
        ConexionDB.getPista(id, new ConexionDB.ResultadoPistaCallback() {
            @Override
            public void onResultadoPista(Pista p) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //  actualizar  con los datos de la actividad
                        texto.setText(p.getDescripcion());
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                // Aquí puedes manejar errores relacionados con la obtención de la actividad
            }
        });
    }

    public void recibirDatosIntentActividad() {

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID_RESERVA_ACTIVIDAD", 0);
        String imagen = intent.getStringExtra("IMAGEN_RESERVA_ACTIVIDAD");
        String hora = intent.getStringExtra("HORARIO_RESERVA_ACTIVIDAD");
        String ubicacion = intent.getStringExtra("UBICACION_RESERVA_ACTIVIDAD");
        String fechaStr = intent.getStringExtra("FECHA_RESERVA_ACTIVIDAD");
        String nombre = intent.getStringExtra("NOMBRE_RESERVA_ACTIVIDAD");

        LocalDateTime fecha = LocalDateTime.parse(fechaStr);
        Timestamp timestamp = Timestamp.valueOf(fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Glide.with(this)
                .load(imagen)
                .into(imageView);

        tv_horario.setText(hora);
        tv_ubicacion.setText(ubicacion);
        tv_duracion.setText("TIEMPO: 60'");
        tv_descripcion.setText(nombre);


        // Conexión a la base de datos para recibir la descripción larga de la actividad
        ConexionDB.getActividad(id, new ConexionDB.ResultadoActividadCallback() {

            @Override
            public void onResultadoActividad(Actividad a) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //  actualizar con los datos de la actividad
                        texto.setText(a.getDescripcion());
                    }
                });
            }
            @Override
            public void onError(Throwable t) {

            }
        });
    }

    public void flechaVolver() {
        flecha_izq.setOnClickListener(v -> {
            Intent intent1 = getIntent();
            if(descripcionPista()){
                email = intent1.getStringExtra("EMAIL_RESERVA");
            }else{
                email = intent1.getStringExtra("EMAIL_RESERVA_ACTIVIDAD");
            }

            Intent intent = new Intent(v.getContext(), InicioActivity.class);
            intent.putExtra("EMAIL_MAIN", email);
            intent.putExtra("DESCRIPCION_ACTIVIDAD", descripcion);
            v.getContext().startActivity(intent);
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

}