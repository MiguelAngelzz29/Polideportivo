package es.miguel.polideportivo_v2.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class ReservaActivity extends AppCompatActivity {

    private RecyclerView recyclerView_actividad;
    private ReservaPistaAdapter pistaAdapter;
    private FirebaseFirestore mFirestore;

    private String opcion, email;
    private Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        recyclerView_actividad = findViewById(R.id.rv1_reserva);
        extra = getIntent().getExtras();
        email = extra.getString("EMAIL_ACTIVIDAD");
        cambiarColorDias();
        recibirDatosBaseDatos();
      //  datosFirestoreAarrayList();



    }

      public void recibirDatosBaseDatos(){

        mFirestore = FirebaseFirestore.getInstance();
        recyclerView_actividad.setHasFixedSize(true);
        recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

       /* Query query = mFirestore.collection("Pista");
        FirestoreRecyclerOptions<Pista> listaPistas = new FirestoreRecyclerOptions
                .Builder<Pista>().setQuery(query,Pista.class).build();*/


        pistaAdapter = new ReservaPistaAdapter(/*listaPistas*/listaParaReservarPádel(),email);
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

     public ArrayList<ReservaPista> listaParaReservarPádel(){
        Bundle extra = getIntent().getExtras();
        opcion =  extra.getString("DESCRIPCION_ACTIVIDAD");


        ArrayList<ReservaPista> lista = new ArrayList<>();
         Pista pista1=null;
         Pista pista2=null;

        if(opcion.equalsIgnoreCase("Pádel")) {
            pista1 = new Pista(1,"Pádel", "Pista 1", R.drawable.pista_padel);
            pista2 = new Pista(2,"Pádel", "Pista 2",  R.drawable.pista_padel);
        }else if(opcion.equalsIgnoreCase("Tenis")){
            pista1 = new Pista(3,"Tenis","Pista 1",R.drawable.tenis);
            pista2 = new Pista(4,"Tenis","Pista 2",R.drawable.tenis);
        }else if(opcion.equalsIgnoreCase("Baloncesto")){
             pista1 = new Pista(5,"Baloncesto","Pista 1",R.drawable.baloncesto);
             pista2 = new Pista(6,"Baloncesto","Pista 2",R.drawable.baloncesto);
        }else{
            pista1 = new Pista(7,"Fútbol Sala","Pista 1",R.drawable.futbol_sala);
            pista2 = new Pista(8,"Fútbol Sala","Pista 2",R.drawable.futbol_sala);
        }

         ReservaPista horario1Pista1 = new ReservaPista("10:00 - 11:30",pista1);
         ReservaPista horario2Pista1 = new ReservaPista("11:30 - 13:00",pista1);
         ReservaPista horario3Pista1 = new ReservaPista("15:00 - 16:30",pista1);
         ReservaPista horario4Pista1 = new ReservaPista("16:30 - 18:00",pista1);
         ReservaPista horario5Pista1 = new ReservaPista("19:30 - 21:00",pista1);
         ReservaPista horario6Pista1 = new ReservaPista("21:00 - 22:30",pista1);

         ReservaPista horario1Pista2 = new ReservaPista("10:00 - 11:30",pista2);
         ReservaPista horario2Pista2 = new ReservaPista("11:30 - 13:00",pista2);
         ReservaPista horario3Pista2 = new ReservaPista("15:00 - 16:30",pista2);
         ReservaPista horario4Pista2 = new ReservaPista("16:30 - 18:00",pista2);
         ReservaPista horario5Pista2 = new ReservaPista("19:30 - 21:00",pista2);
         ReservaPista horario6Pista2 = new ReservaPista("21:00 - 22:30",pista2);

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

