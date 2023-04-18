package es.miguel.polideportivo_v2.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.ReservaPistaAdapter;
import es.miguel.polideportivo_v2.dominio.Pista;
import es.miguel.polideportivo_v2.dominio.ReservaPistas;

public class ReservaActivity extends AppCompatActivity {

    private RecyclerView recyclerView_actividad;
    private ReservaPistaAdapter pistaAdapter;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        recyclerView_actividad = findViewById(R.id.rv1_reserva);
        cambiarColorDias();
        recibirDatosBaseDatos();
        datosFirestoreAarrayList();



    }

      public void recibirDatosBaseDatos(){

        mFirestore = FirebaseFirestore.getInstance();
        recyclerView_actividad.setHasFixedSize(true);
        recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

       /* Query query = mFirestore.collection("Pista");
        FirestoreRecyclerOptions<Pista> listaPistas = new FirestoreRecyclerOptions
                .Builder<Pista>().setQuery(query,Pista.class).build();*/


        pistaAdapter = new ReservaPistaAdapter(/*listaPistas*/listaParaReservarPádel());
        recyclerView_actividad.setAdapter(pistaAdapter);
    }



 /*   @Override
    protected void onStart() {
        super.onStart();
        pistaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pistaAdapter.stopListening();
    }*/


    public void datosFirestoreAarrayList(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Pista");

        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Map<String, Object>> dataList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("id", document.getId()); // Agrega el ID del documento al Map
                        data.putAll(document.getData()); // Agrega los datos del documento al Map
                        dataList.add(data); // Agrega el Map al ArrayList
                    }

                    // Aquí ya tienes los datos en forma de ArrayList de Maps
                    // dataList.get(0).get("nombre_del_campo") para obtener el valor de un campo
                } else {
                    Log.d(TAG, "Error al obtener los datos: ", task.getException());
                }
            }
        });
    }

     public void cambiarColorDias(){
        TextView lunes = findViewById(R.id.tv_lunes);
         TextView martes = findViewById(R.id.tv_martes);
         TextView miercoles = findViewById(R.id.tv_miercoles);
         TextView jueves = findViewById(R.id.tv_jueves);
         TextView viernes = findViewById(R.id.tv_viernes);
         TextView sabado = findViewById(R.id.tv_sabado);
         TextView domingo = findViewById(R.id.tv_domingo);

         List<TextView> listaDias = new ArrayList<>();
         listaDias.add(lunes);
         listaDias.add(martes);
         listaDias.add(miercoles);
         listaDias.add(jueves);
         listaDias.add(viernes);
         listaDias.add(sabado);
         listaDias.add(domingo);

         LocalDate hoy = LocalDate.now(); // Obtener fecha actual
         DayOfWeek dia = hoy.getDayOfWeek(); // Obtener día de la semana actual

         for (TextView textView : listaDias) {
             if(textView.getText().toString().equalsIgnoreCase(dia.getDisplayName(TextStyle.SHORT, Locale.getDefault()))){ // Si el TextView corresponde al día actual
                 textView.setTextColor(getResources().getColor(R.color.teal_200)); // Establecer color blanco
             } else {
                 textView.setTextColor(getResources().getColor(R.color.white)); // Establecer color teal_200
             }

             textView.setOnClickListener( v -> {
                 // Cambiar color del TextView cuando es seleccionado
                 for (TextView tv : listaDias) {
                     if(tv == textView) {
                         tv.setTextColor(getResources().getColor(R.color.teal_200));
                     } else {
                         tv.setTextColor(getResources().getColor(R.color.white));
                     }
                 }
             });
         }
     }

     public ArrayList<ReservaPistas> listaParaReservarPádel(){
        Bundle extra = getIntent().getExtras();
        String opcion =  extra.getString("DESCRIPCION");
        ArrayList<ReservaPistas> lista = new ArrayList<>();
         Pista pista1=null;
         Pista pista2=null;

        if(opcion.equalsIgnoreCase("Pádel")) {
            pista1 = new Pista("Pádel", "Pista 1", true, R.drawable.pista_padel);
            pista2 = new Pista("Pádel", "Pista 2", true, R.drawable.pista_padel);
        }else if(opcion.equalsIgnoreCase("Tenis")){
            pista1 = new Pista("Tenis","Pista 1",true,R.drawable.tenis);
            pista2 = new Pista("Tenis","Pista 2",true,R.drawable.tenis);
        }else if(opcion.equalsIgnoreCase("Baloncesto")){
             pista1 = new Pista("Baloncesto","Pista 1",true,R.drawable.baloncesto);
             pista2 = new Pista("Baloncesto","Pista 2",true,R.drawable.baloncesto);
        }else{
            pista1 = new Pista("Fútbol Sala","Pista 1",true,R.drawable.futbol_sala);
            pista2 = new Pista("Fútbol Sala","Pista 2",true,R.drawable.futbol_sala);
        }

         ReservaPistas horario1Pista1 = new ReservaPistas("10:00 - 11:30",pista1);
         ReservaPistas horario2Pista1 = new ReservaPistas("11:30 - 13:00",pista1);
         ReservaPistas horario3Pista1 = new ReservaPistas("15:00 - 16:30",pista1);
         ReservaPistas horario4Pista1 = new ReservaPistas("16:30 - 18:00",pista1);
         ReservaPistas horario5Pista1 = new ReservaPistas("19:30 - 21:00",pista1);
         ReservaPistas horario6Pista1 = new ReservaPistas("21:00 - 22:30",pista1);

         ReservaPistas horario1Pista2 = new ReservaPistas("10:00 - 11:30",pista2);
         ReservaPistas horario2Pista2 = new ReservaPistas("11:30 - 13:00",pista2);
         ReservaPistas horario3Pista2 = new ReservaPistas("15:00 - 16:30",pista2);
         ReservaPistas horario4Pista2 = new ReservaPistas("16:30 - 18:00",pista2);
         ReservaPistas horario5Pista2 = new ReservaPistas("19:30 - 21:00",pista2);
         ReservaPistas horario6Pista2 = new ReservaPistas("21:00 - 22:30",pista2);

         lista.add(horario1Pista1);
         lista.add(horario2Pista1);
         lista.add(horario3Pista1);
         lista.add(horario4Pista1);
         lista.add(horario5Pista1);
         lista.add(horario6Pista1);

         lista.add(horario1Pista2);
         lista.add(horario2Pista2);
         lista.add(horario3Pista2);
         lista.add(horario4Pista2);
         lista.add(horario5Pista2);
         lista.add(horario6Pista2);

         // Ordenamiento de la lista por el horario de reserva
         Collections.sort(lista);

        return lista;
     }

     }

