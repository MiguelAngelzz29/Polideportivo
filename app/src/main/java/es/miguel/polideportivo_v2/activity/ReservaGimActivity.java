package es.miguel.polideportivo_v2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.ActividadesGimAdapter;
import es.miguel.polideportivo_v2.adaptador.CalendarioAdapter;
import es.miguel.polideportivo_v2.adaptador.HorarioAdapter;
import es.miguel.polideportivo_v2.adaptador.HorarioDisponibleAdapter;
import es.miguel.polideportivo_v2.dominio.Actividad;

public class ReservaGimActivity extends AppCompatActivity {

    private RecyclerView rv_gim,rv_gim2,rv_gim3;
    private TextView seleccionaFecha, seleccionaHora;
    private Button btn_reservar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_gim);

        seleccionaFecha = findViewById(R.id.tv_fechaGim);
        seleccionaHora = findViewById(R.id.tv_horaGim);
        rv_gim = findViewById(R.id.rv1);
        rv_gim2 = findViewById(R.id.rv2);
        rv_gim3 = findViewById(R.id.rv3);
        btn_reservar = findViewById(R.id.btn_reservarGim);


        seleccionaFecha.setVisibility(View.GONE);
        seleccionaHora.setVisibility(View.GONE);

        //Datos RecyclerView 1

        rv_gim.setHasFixedSize(true);
        rv_gim.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ActividadesGimAdapter actividadesGimAdapter = new ActividadesGimAdapter (listaActividades());
        rv_gim.setAdapter(actividadesGimAdapter);

        //Datos RecyclerView 2

        rv_gim2.setVisibility(View.GONE);
        rv_gim2.setHasFixedSize(true);
        rv_gim2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        CalendarioAdapter calendarioAdapter = new CalendarioAdapter(listaDias());
        rv_gim2.setAdapter(calendarioAdapter);

        //Datos RecyclerView 3

        rv_gim3.setVisibility(View.GONE);
        rv_gim3.setHasFixedSize(true);
        rv_gim3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        HorarioAdapter horarioAdapter = new HorarioAdapter(listaHoras());
        rv_gim3.setAdapter(horarioAdapter);


        // Botón reservar

        btn_reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservaGimActivity.this,ConfirmarReservaGimActivity.class);
                startActivity(intent);
            }
        });

    }

    public List<Actividad> listaActividades(){

        List<Actividad> lista = new ArrayList<>();

        Actividad actividad1 = new Actividad("Spinning",R.drawable.spinning);
        Actividad actividad2 = new Actividad("Zumba",R.drawable.zumba);
        Actividad actividad3 = new Actividad("Pilates",R.drawable.pilates);
        Actividad actividad4 = new Actividad("Boxeo",R.drawable.boxeo);
        Actividad actividad5 = new Actividad("Karate",R.drawable.karate);

        lista.add(actividad1);
        lista.add(actividad2);
        lista.add(actividad3);
        lista.add(actividad4);
        lista.add(actividad5);

        return lista;
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