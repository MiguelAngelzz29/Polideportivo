package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    private String email,opcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_actividad);

        seleccionaPista = getIntent().getBooleanExtra("PISTA",false);
        seleccionaGim = getIntent().getBooleanExtra("GIM",false);
        seleccionaPiscina = getIntent().getBooleanExtra("PISCINA",false);
        email = getIntent().getStringExtra("EMAIL_INICIO");
        recyclerView_actividad = findViewById(R.id.rv_seleccionar_actividad);


        //Datos RecyclerView

        recyclerView_actividad.setHasFixedSize(true);
        recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        SeleccionarActividadAdapter seleccionarActividadesAdapter = new SeleccionarActividadAdapter (listaActividades(),email,this);
        recyclerView_actividad.setAdapter(seleccionarActividadesAdapter);

    }

    public List<Actividad> listaActividades(){



        List<Actividad> lista = new ArrayList<>();

      if(seleccionaPista) {


            Actividad actividad1 = new Actividad("Pádel","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fpista_padel.jpg?alt=media&token=8d20e0a1-27e9-4ad7-b407-e1a1cb882842");
            Actividad actividad2 = new Actividad("Tenis","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ftenis.jpg?alt=media&token=9849df9a-6081-4bf3-83cc-27b5a18d9f45");
            Actividad actividad3 = new Actividad("Baloncesto","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fbaloncesto.jpg?alt=media&token=2480e347-a5b8-4561-b701-78be14aea452");
            Actividad actividad4 = new Actividad("Fútbol sala","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ffutbol_sala.jpg?alt=media&token=d0479127-2560-4a12-bd76-ec7021977f3c");

            lista.add(actividad1);
            lista.add(actividad2);
            lista.add(actividad3);
            lista.add(actividad4);


      } else if(seleccionaGim){

            Actividad actividad1 = new Actividad("Spinning","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fspinning.jpg?alt=media&token=f4d7214c-43ec-4f00-8cb5-1be6abfb02ed");
            Actividad actividad2 = new Actividad("Zumba", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fzumba.jpg?alt=media&token=c3976a86-eca2-4de9-b481-d37e5120bc09");
            Actividad actividad3 = new Actividad("Pilates", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fpilates.jpg?alt=media&token=207bc70e-704e-4551-a7dc-4099d73475eb");
            Actividad actividad4 = new Actividad("Boxeo","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fboxeo.jpg?alt=media&token=5f5bd7a5-87ca-4e94-bfc0-09a375a76eb1");
            Actividad actividad5 = new Actividad("Karate","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fkarate.jpg?alt=media&token=e017722c-a8bf-4275-8498-fbf2b0530da9");

            lista.add(actividad1);
            lista.add(actividad2);
            lista.add(actividad3);
            lista.add(actividad4);
            lista.add(actividad5);

        }else{

            Actividad actividad1 = new Actividad("Natación","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fnatacion.jpg?alt=media&token=97d591bb-20a6-45ad-b8cd-b08c60a69ff5");
            Actividad actividad2 = new Actividad("Waterpolo","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fwaterpolo.jpg?alt=media&token=7b2cf20a-7fed-4884-bd57-429e7f7d32c3");
            Actividad actividad3 = new Actividad("AquaGim","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Faquagim.jpg?alt=media&token=c3a25d2a-cac3-4e7b-a1e5-e7c6c782951c");
            Actividad actividad4 = new Actividad("Sincronizada","https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fnatacion_sin.jpg?alt=media&token=e2444d3f-f5bc-4233-b9fa-e0b101dea541");

            lista.add(actividad1);
            lista.add(actividad2);
            lista.add(actividad3);
            lista.add(actividad4);

        }

        return lista;
    }

}