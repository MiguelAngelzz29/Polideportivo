package es.miguel.polideportivo_v2.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.CalendarioAdapter;
import es.miguel.polideportivo_v2.adaptador.HorarioDisponibleAdapter;

public class HorarioDisponibleActivity extends AppCompatActivity {

    private RecyclerView rv_horario_disponible;
    Button btn_conLuz,btn_sinLuz;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_disponible);


        //Datos RecyclerView
        rv_horario_disponible = findViewById(R.id.rv_horario_disponible);
        rv_horario_disponible.setHasFixedSize(true);
        rv_horario_disponible.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        HorarioDisponibleAdapter horarioDisponibleAdapter = new HorarioDisponibleAdapter(listaDias());
        rv_horario_disponible.setAdapter(horarioDisponibleAdapter);



    }

    public List<String> listaDias (){

        List<String> listaDias = new ArrayList();
        String hoy = "hoy";
        String ayer = "ayer";
        String manana = "mañana";
        String hola = "hola";
        String adios = "adios";
        String bye = "bye";
        String hello = "hello";

        listaDias.add(hoy);
        listaDias.add(manana);
        listaDias.add(hola);
        listaDias.add(adios);
        listaDias.add(bye);
        listaDias.add(hello);

        return listaDias;
    }
}
