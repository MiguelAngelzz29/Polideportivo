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

import org.w3c.dom.Text;

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
import es.miguel.polideportivo_v2.adaptador.ReservaAdapter;
import es.miguel.polideportivo_v2.dominio.Actividad;
import es.miguel.polideportivo_v2.dominio.Pista;
import es.miguel.polideportivo_v2.dominio.ReservaActividad;
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class ReservaActivity extends AppCompatActivity {

    private RecyclerView recyclerView_actividad;
    private ReservaAdapter pistaAdapter;
    private FirebaseFirestore mFirestore;

    private String opcion, email;
    private Bundle extra;

    private String diaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        recyclerView_actividad = findViewById(R.id.rv1_reserva);
        extra = getIntent().getExtras();
        email = extra.getString("EMAIL_ACTIVIDAD");
        cambiarColorDias();
        recibirDatos();

    }

      public void recibirDatos(){

        mFirestore = FirebaseFirestore.getInstance();
        recyclerView_actividad.setHasFixedSize(true);
        recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        pistaAdapter = new ReservaAdapter(listaParaReservarPádel(),email,this);
        recyclerView_actividad.setAdapter(pistaAdapter);
    }

     public void cambiarColorDias(){


         LocalDate hoy = LocalDate.now(); // Obtener fecha actual
         DayOfWeek dia = hoy.getDayOfWeek(); // Obtener día de la semana actual


         for (TextView textView : diasSemana()) {
             if(textView.getText().toString().equalsIgnoreCase(dia.getDisplayName(TextStyle.SHORT, Locale.getDefault()))){ // Si el TextView corresponde al día actual
                 textView.setTextColor(getResources().getColor(R.color.teal_200)); // Establecer color blanco
                 diaSeleccionado = textView.getText().toString();
                 recibirDatos();
             } else {
                 textView.setTextColor(getResources().getColor(R.color.white)); // Establecer color teal_200
             }

             textView.setOnClickListener( v -> {
                 // Cambiar color del TextView cuando es seleccionado
                 for (TextView tv : diasSemana()) {
                     if(tv == textView) {
                         tv.setTextColor(getResources().getColor(R.color.teal_200));
                         diaSeleccionado = textView.getText().toString();
                         recibirDatos();
                         System.out.println("cccccccccccccccccccccccccccc"+diaSeleccionado);
                     } else {
                         tv.setTextColor(getResources().getColor(R.color.white));
                     }
                 }
             });
         }
     }

    public ArrayList<TextView> diasSemana() {
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

        return (ArrayList<TextView>) listaDias;
    }

    public ArrayList<ReservaPista> listaParaReservarPádel(){
        Bundle extra = getIntent().getExtras();
        opcion =  extra.getString("DESCRIPCION_ACTIVIDAD");
        Pista pista1=null,pista2=null;

        switch (opcion.toLowerCase()) {
            case "pádel":
                pista1 = new Pista(1, "Pádel", "Pista 1", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fpista_padel.jpg?alt=media&token=8d20e0a1-27e9-4ad7-b407-e1a1cb882842");
                pista2 = new Pista(2, "Pádel", "Pista 2", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fpista_padel.jpg?alt=media&token=8d20e0a1-27e9-4ad7-b407-e1a1cb882842");
                break;
            case "tenis":
                pista1 = new Pista(3, "Tenis", "Pista 1", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ftenis.jpg?alt=media&token=9849df9a-6081-4bf3-83cc-27b5a18d9f45");
                pista2 = new Pista(4, "Tenis", "Pista 2", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ftenis.jpg?alt=media&token=9849df9a-6081-4bf3-83cc-27b5a18d9f45");
                break;
            case "baloncesto":
                pista1 = new Pista(5, "Baloncesto", "Pista 1", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fbaloncesto.jpg?alt=media&token=2480e347-a5b8-4561-b701-78be14aea452");
                pista2 = new Pista(6, "Baloncesto", "Pista 2", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fbaloncesto.jpg?alt=media&token=2480e347-a5b8-4561-b701-78be14aea452");
                break;
            default:
                pista1 = new Pista(7, "Fútbol Sala", "Pista 1", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ffutbol_sala.jpg?alt=media&token=d0479127-2560-4a12-bd76-ec7021977f3c");
                pista2 = new Pista(8, "Fútbol Sala", "Pista 2", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ffutbol_sala.jpg?alt=media&token=d0479127-2560-4a12-bd76-ec7021977f3c");
                break;
        }

        ArrayList<ReservaPista> lista = new ArrayList<>();
        lista.addAll(horarioPistas(pista1, pista2));

         // Ordenamiento de la lista por el horario de reserva
         Collections.sort(horarioPistas(pista1,pista2));


        return lista;
     }

     public ArrayList<ReservaPista> horarioPistas(Pista pista1, Pista pista2) {
         ArrayList<ReservaPista> lista = new ArrayList<>();

         ReservaPista horario1Pista1 = new ReservaPista("10:00 - 11:30", pista1);
         ReservaPista horario2Pista1 = new ReservaPista("11:30 - 13:00", pista1);
         ReservaPista horario3Pista1 = new ReservaPista("15:00 - 16:30", pista1);
         ReservaPista horario4Pista1 = new ReservaPista("16:30 - 18:00", pista1);
         ReservaPista horario5Pista1 = new ReservaPista("19:30 - 21:00", pista1);
         ReservaPista horario6Pista1 = new ReservaPista("21:00 - 22:30", pista1);

         ReservaPista horario1Pista2 = new ReservaPista("10:00 - 11:30", pista2);
         ReservaPista horario2Pista2 = new ReservaPista("11:30 - 13:00", pista2);
         ReservaPista horario3Pista2 = new ReservaPista("15:00 - 16:30", pista2);
         ReservaPista horario4Pista2 = new ReservaPista("16:30 - 18:00", pista2);
         ReservaPista horario5Pista2 = new ReservaPista("19:30 - 21:00", pista2);
         ReservaPista horario6Pista2 = new ReservaPista("21:00 - 22:30", pista2);
         System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrr" + diaSeleccionado);
         if (diaSeleccionado.equalsIgnoreCase("SÁB.")
                 || diaSeleccionado.equalsIgnoreCase("DOM.")) {
             lista.add(horario1Pista1);
             lista.add(horario2Pista1);

             lista.add(horario1Pista2);
             lista.add(horario2Pista2);

         } else {

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
         }
         return lista;
     }

    public ArrayList<ReservaActividad> listaParaReservarActividades(){
        Bundle extra = getIntent().getExtras();
        opcion =  extra.getString("DESCRIPCION_ACTIVIDAD");
        Actividad actividad = null;

        switch (opcion.toLowerCase()) {
            case "spinning":
                pista1 = new Pista(1, "Pádel", "Pista 1", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fpista_padel.jpg?alt=media&token=8d20e0a1-27e9-4ad7-b407-e1a1cb882842");
                pista2 = new Pista(2, "Pádel", "Pista 2", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fpista_padel.jpg?alt=media&token=8d20e0a1-27e9-4ad7-b407-e1a1cb882842");
                break;
            case "zumba":
                pista1 = new Pista(3, "Tenis", "Pista 1", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ftenis.jpg?alt=media&token=9849df9a-6081-4bf3-83cc-27b5a18d9f45");
                pista2 = new Pista(4, "Tenis", "Pista 2", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ftenis.jpg?alt=media&token=9849df9a-6081-4bf3-83cc-27b5a18d9f45");
                break;
            case "pilates":
                pista1 = new Pista(5, "Baloncesto", "Pista 1", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fbaloncesto.jpg?alt=media&token=2480e347-a5b8-4561-b701-78be14aea452");
                pista2 = new Pista(6, "Baloncesto", "Pista 2", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Fbaloncesto.jpg?alt=media&token=2480e347-a5b8-4561-b701-78be14aea452");
                break;
            default:
                pista1 = new Pista(7, "Fútbol Sala", "Pista 1", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ffutbol_sala.jpg?alt=media&token=d0479127-2560-4a12-bd76-ec7021977f3c");
                pista2 = new Pista(8, "Fútbol Sala", "Pista 2", "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot.com/o/fotos%2Ffutbol_sala.jpg?alt=media&token=d0479127-2560-4a12-bd76-ec7021977f3c");
                break;
        }

        ArrayList<ReservaActividad> lista = new ArrayList<>();
       // lista.addAll(horarioPistas(pista1, pista2));

        // Ordenamiento de la lista por el horario de reserva
      //  Collections.sort(horarioPistas(pista1,pista2));


        return lista;
    }

     }

