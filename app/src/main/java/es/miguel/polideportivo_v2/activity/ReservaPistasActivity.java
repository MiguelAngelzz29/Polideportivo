package es.miguel.polideportivo_v2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.CalendarioAdapter;
import es.miguel.polideportivo_v2.adaptador.HorarioAdapter;

public class ReservaPistasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDias,recyclerViewHoras;
    private Button btn_disponibilidad;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_pistas);



        // Datos RecyclerView días
        recyclerViewDias = findViewById(R.id.rv_listaDias);
        recyclerViewDias.setHasFixedSize(true);
        recyclerViewDias.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        CalendarioAdapter calendarioAdapter = new CalendarioAdapter(listaDias());
        recyclerViewDias.setAdapter(calendarioAdapter);

        //Datos RecyclerView horas

        recyclerViewHoras = findViewById(R.id.rv_listaHoras);
        recyclerViewHoras.setHasFixedSize(true);
        recyclerViewHoras.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        HorarioAdapter horarioAdapter = new HorarioAdapter(listaHoras());
        recyclerViewHoras.setAdapter(horarioAdapter);

        // Botón reserva

        btn_disponibilidad = findViewById(R.id.btn_horarios_disponibles);
        btn_disponibilidad.setOnClickListener(v -> {

                Intent intent = new Intent(ReservaPistasActivity.this,HorarioDisponibleActivity.class);
                startActivity(intent);

        });

    }

    public List<LocalDate> listaDias(){

        LocalDate hoy = LocalDate.now();
        List<LocalDate> listaDias = new ArrayList<>();

        for(int i = 0; i < 7; i++){
            listaDias.add(hoy.plusDays(i));
        }
        return listaDias;
    }
    public List<String> listaHoras (){

        List<String> listaDias = new ArrayList();
        String hoy = "12:00";
        String ayer = "13:00";
        String manana = "14:00";
        String hola = "15:00";
        String adios = "16:00";
        String bye = "17:00";
        String hello = "18:00";

        listaDias.add(hoy);
        listaDias.add(manana);
        listaDias.add(hola);
        listaDias.add(adios);
        listaDias.add(bye);
        listaDias.add(hello);

        return listaDias;
    }

}