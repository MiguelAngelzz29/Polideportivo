package es.miguel.polideportivo_v2.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.ReservaActividadAdapter;
import es.miguel.polideportivo_v2.adaptador.ReservaAdapter;
import es.miguel.polideportivo_v2.data.ConexionDB;
import es.miguel.polideportivo_v2.dominio.Actividad;
import es.miguel.polideportivo_v2.dominio.Pista;
import es.miguel.polideportivo_v2.dominio.ReservaActividad;
import es.miguel.polideportivo_v2.dominio.ReservaPista;


public class ReservaActivity extends AppCompatActivity {

    private RecyclerView recyclerView_actividad;
    private ReservaAdapter pistaAdapter;
    private ReservaActividadAdapter actividadAdapter;
    private FirebaseFirestore mFirestore;

    private String opcion, email;
    private Bundle extra;
    private boolean seleccionaGim, seleccionaPista, seleccionaPiscina;

    private String diaSeleccionado;
    private LocalDateTime fecha_reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        mFirestore = FirebaseFirestore.getInstance();
        recyclerView_actividad = findViewById(R.id.rv1_reserva);
        extra = getIntent().getExtras();
        email = extra.getString("EMAIL_ACTIVIDAD");
        seleccionaGim = extra.getBoolean("GIM");
        seleccionaPista = extra.getBoolean("PISTA");
        seleccionaPiscina = extra.getBoolean("PISCINA");
        cambiarColorDias();
        recibirDatos();

        //cogen la conexioxDB getproximos7 dias, igual que en misReservas para conseguir una lista de las reservas de los próximos 7 días
        // para después filtrarla y quitar los horarios que sean iguales.

    }

    public void recibirDatos() {


        recyclerView_actividad.setHasFixedSize(true);
        recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (seleccionaGim || seleccionaPiscina) {
            actividadAdapter = new ReservaActividadAdapter(listaParaReservarActividades(), email, this);
            recyclerView_actividad.setAdapter(actividadAdapter);
        } else {
            pistaAdapter = new ReservaAdapter(listaParaReservarPistas(), email, this,fecha_reserva);
            recyclerView_actividad.setAdapter(pistaAdapter);
        }
    }

    public void cambiarColorDias() {


        LocalDate hoy = LocalDate.now(); // Obtener fecha actual
        DayOfWeek dia = hoy.getDayOfWeek(); // Obtener día de la semana actual


        for (TextView textView : diasSemana()) {
            if (textView.getText().toString().equalsIgnoreCase(dia.getDisplayName(TextStyle.SHORT, Locale.getDefault()))) { // Si el TextView corresponde al día actual
                textView.setTextColor(getResources().getColor(R.color.teal_200)); // Establecer color blanco
                diaSeleccionado = textView.getText().toString();
                seleccionarDia(textView);
                recibirDatos();
            } else {
                textView.setTextColor(getResources().getColor(R.color.white)); // Establecer color teal_200
            }

            textView.setOnClickListener(v -> {
                // Cambiar color del TextView cuando es seleccionado
                for (TextView tv : diasSemana()) {
                    if (tv == textView) {
                        tv.setTextColor(getResources().getColor(R.color.teal_200));
                        diaSeleccionado = textView.getText().toString();
                        seleccionarDia(tv);
                        recibirDatos();
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

    public ArrayList<ReservaPista> listaParaReservarPistas() {
        Bundle extra = getIntent().getExtras();
        opcion = extra.getString("DESCRIPCION_ACTIVIDAD");
        Pista pista1 = null, pista2 = null;

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
        lista.addAll(horarioPistas(pista1, pista2,diaSeleccionado));

        // Ordenamiento de la lista por el horario de reserva
        Collections.sort(horarioPistas(pista1, pista2,diaSeleccionado));


        return lista;
    }

    public ArrayList<ReservaPista> horarioPistas(Pista pista1, Pista pista2, String dia) {
        ArrayList<ReservaPista> lista = new ArrayList<>();

        String[] dias = {"LUN.", "MAR.", "MIÉ.", "JUE.", "VIE.", "SÁB.", "DOM."};
        String[] horarios = {"10:00 - 11:30", "11:30 - 13:00", "15:00 - 16:30", "16:30 - 18:00", "19:30 - 21:00", "21:00 - 22:30"};

        for (String nombreDia : dias) {
            if (nombreDia.equals(dia)) { // Verificar si el día actual coincide con el parámetro
                for (String horario : horarios) {
                    ReservaPista reservaPista1 = new ReservaPista(horario, pista1);
                    lista.add(reservaPista1);
                    ReservaPista reservaPista2 = new ReservaPista(horario, pista2);
                    lista.add(reservaPista2);
                }
            }
        }
        return lista;
    }

    public ArrayList<ReservaActividad> listaParaReservarActividades() {
        Bundle extra = getIntent().getExtras();
        opcion = extra.getString("DESCRIPCION_ACTIVIDAD");
        Actividad actividad = null;

        switch (opcion.toLowerCase()) {
            case "spinning":

                break;
            case "zumba":

                break;
            case "pilates":

                break;
            case "Boxeo":

                break;
            case "Karate":

                break;

            default:


                break;
        }

        ArrayList<ReservaActividad> lista = new ArrayList<>();
        // lista.addAll(horarioPistas(pista1, pista2));

        // Ordenamiento de la lista por el horario de reserva
        //  Collections.sort(horarioPistas(pista1,pista2));


        return lista;
    }

    public void seleccionarDia(TextView dia) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime lunes = ahora.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(10, 0));
        LocalDateTime martes = ahora.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).with(LocalTime.of(10, 0));
        LocalDateTime miercoles = ahora.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).with(LocalTime.of(10, 0));
        LocalDateTime jueves = ahora.with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).with(LocalTime.of(10, 0));
        LocalDateTime viernes = ahora.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).with(LocalTime.of(10, 0));
        LocalDateTime sabado = ahora.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).with(LocalTime.of(10, 0));
        LocalDateTime domingo = ahora.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(LocalTime.of(10, 0));

        // Matriz de días y horas
        LocalDateTime[][] dias = new LocalDateTime[][]{
                {ahora, martes, miercoles, jueves, viernes, sabado, domingo},
                {lunes, ahora, miercoles, jueves, viernes, sabado, domingo},
                {lunes, martes, ahora, jueves, viernes, sabado, domingo},
                {lunes, martes, miercoles, ahora, viernes, sabado, domingo},
                {lunes, martes, miercoles, jueves, ahora, sabado, domingo},
                {lunes, martes, miercoles, jueves, viernes, ahora, domingo},
                {lunes, martes, miercoles, jueves, viernes, sabado, ahora}
        };

        // Verificar el texto del día seleccionado
        String textoDia = dia.getText().toString();
        int indiceX = -1;
        int indiceY = -1;
        String[] diasDeLaSemana = new String[]{"LUN.", "MAR.", "MIÉ.", "JUE.", "VIE.", "SÁB.", "DOM."};
        for (int i = 0; i < diasDeLaSemana.length && indiceY == -1; i++) {
            if (textoDia.equalsIgnoreCase(diasDeLaSemana[i])) {
                indiceY = i;
            }
        }
        if (indiceY != -1) {
            indiceX = ahora.getDayOfWeek().getValue() - 1;
            LocalDateTime proximoDia = dias[indiceX][indiceY];

            fecha_reserva = proximoDia;
            //hacer algo con la fecha
            }
        }

    public void listaHorariosReservados() {

        // Fecha actual
        LocalDateTime today = LocalDateTime.now();

        // Fecha dentro de 7 días
        LocalDateTime weekLater = today.plusDays(7);
        ArrayList<ReservaPista> resultados = new ArrayList<>();
        // Consulta a Firebase
        CollectionReference reservasRef = mFirestore.collection("ReservaPista");
        Query query = reservasRef.whereGreaterThanOrEqualTo("fecha_reserva", Timestamp.valueOf(today.toString()))
                .whereLessThanOrEqualTo("fecha_reserva", Timestamp.valueOf(weekLater.toString()));



    }
}