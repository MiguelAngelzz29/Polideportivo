package es.miguel.polideportivo_v2.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.SeleccionarActividadAdapter;
import es.miguel.polideportivo_v2.dominio.Actividad;

public class SeleccionarActividadActivity extends AppCompatActivity {

    private RecyclerView recyclerView_actividad;
    private boolean seleccionaPista,seleccionaGim,seleccionaPiscina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_actividad);

        seleccionaPista = getIntent().getBooleanExtra("PISTA",false);
        seleccionaGim = getIntent().getBooleanExtra("GIM",false);
        seleccionaPiscina = getIntent().getBooleanExtra("PISCINA",false);

        recyclerView_actividad = findViewById(R.id.rv_seleccionar_actividad);



        //Datos RecyclerView

        recyclerView_actividad.setHasFixedSize(true);
        recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        SeleccionarActividadAdapter seleccionarActividadesAdapter = new SeleccionarActividadAdapter (listaActividades());
        recyclerView_actividad.setAdapter(seleccionarActividadesAdapter);



    }

    public List<Actividad> listaActividades(){



        List<Actividad> lista = new ArrayList<>();

      if(seleccionaPista) {

            Actividad actividad1 = new Actividad("Pádel",30, R.drawable.pista_padel,"10:00 - 11:00");
            Actividad actividad2 = new Actividad("Tenis",20, R.drawable.tenis,"10:00 - 11:00");
            Actividad actividad3 = new Actividad("Baloncesto",30, R.drawable.baloncesto,"10:00 - 11:00");
            Actividad actividad4 = new Actividad("Fútbol sala",30, R.drawable.futbol_sala,"10:00 - 11:00");

            lista.add(actividad1);
            lista.add(actividad2);
            lista.add(actividad3);
            lista.add(actividad4);

      } else if(seleccionaGim){

            Actividad actividad1 = new Actividad("Spinning",30, R.drawable.spinning,"11:00 - 12:00");
            Actividad actividad2 = new Actividad("Zumba",20, R.drawable.zumba,"11:00 - 12:00");
            Actividad actividad3 = new Actividad("Pilates",30, R.drawable.pilates,"11:00 - 12:00");
            Actividad actividad4 = new Actividad("Boxeo",40, R.drawable.boxeo,"11:00 - 12:00");
            Actividad actividad5 = new Actividad("Karate",30, R.drawable.karate,"11:00 - 12:00");

            lista.add(actividad1);
            lista.add(actividad2);
            lista.add(actividad3);
            lista.add(actividad4);
            lista.add(actividad5);

        }else{

            Actividad actividad1 = new Actividad("Natación",30,R.drawable.natacion,"11:00 - 12:00");
            Actividad actividad2 = new Actividad("Waterpolo",20,R.drawable.waterpolo,"11:00 - 12:00");
            Actividad actividad3 = new Actividad("AquaGim",30,R.drawable.aquagim,"11:00 - 12:00");
            Actividad actividad4 = new Actividad("Sincronizada",20,R.drawable.natacion_sin,"11:00 - 12:00");

            lista.add(actividad1);
            lista.add(actividad2);
            lista.add(actividad3);
            lista.add(actividad4);

        }

        return lista;
    }

}