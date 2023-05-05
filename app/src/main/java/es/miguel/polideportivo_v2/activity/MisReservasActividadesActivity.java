package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.MisReservasActividadesAdapter;
import es.miguel.polideportivo_v2.adaptador.MisReservasAdapter;
import es.miguel.polideportivo_v2.adaptador.SinReservasAdapter;
import es.miguel.polideportivo_v2.data.ConexionDB;
import es.miguel.polideportivo_v2.dominio.ReservaActividad;
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class MisReservasActividadesActivity extends AppCompatActivity {


    private ArrayList<ReservaActividad> listaReservas;
    private RecyclerView rv;
    private MisReservasActividadesAdapter misReservas;
    private static final String TAG = "MisReservasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL_INICIO");
        listaReservas = new ArrayList<>();
        Log.d(TAG, "Lista de reservas recibida: " + listaReservas.toString());
        System.out.println("ccccccccccccccccccccccccccccccccc1 " + listaReservas);
        ConexionDB.getListaReservasActividades(email, new ConexionDB.ResultadoReservasActividadCallback() {
            @Override
            public void onResultadoReservasActividad(ArrayList<ReservaActividad> reservas) {
                listaReservas = reservas;
                rv = findViewById(R.id.rv_mis_reservas);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                if (listaReservas.size() > 0) {

                    Collections.sort(listaReservas);
                    misReservas = new MisReservasActividadesAdapter(listaReservas, email, MisReservasActividadesActivity.this);
                    rv.setAdapter(misReservas);
                } else {
                    // Si no hay reservas, mostrar algún mensaje o hacer alguna acción

                    SinReservasAdapter sinReserva = new SinReservasAdapter();
                    rv.setAdapter(sinReserva);

                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "Error al obtener lista de reservas: ", t);
            }
        });
    }

}

