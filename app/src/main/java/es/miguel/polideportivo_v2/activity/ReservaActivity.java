package es.miguel.polideportivo_v2.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

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

    private String diaSeleccionado;

    private LocalDateTime fecha_reserva;
    private ArrayList<ReservaPista> listaReservasPistas;
    private ArrayList<ReservaActividad> listaReservasActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        mFirestore = FirebaseFirestore.getInstance();
        recyclerView_actividad = findViewById(R.id.rv1_reserva);
        extra = getIntent().getExtras();
        email = extra.getString("EMAIL_ACTIVIDAD");

        cambiarColorDias();

        if(seleccionaPista()){
            recibirDatosPistas();
        }else if(seleccionaGim()){
            recibirDatosActividad();
        }else{
         recibirDatosPiscina();
        }



    }

    public interface ResultadoReservasCallback {
        void onResultadoReservas(ArrayList<ReservaPista> reservas);

        void onError(Throwable t);
    }

    public interface ResultadoReservasActividadCallback {
        void onResultadoReservasActividad(ArrayList<ReservaActividad> reservas);

        void onError(Throwable t);
    }

    public void listaHorariosReservadosPista(ResultadoReservasCallback callback) {
        ConexionDB.getReservasPistasProximos7Dias(new ConexionDB.ResultadoReservasCallback() {
            @Override
            public void onResultadoReservas(ArrayList<ReservaPista> reservas) {
                callback.onResultadoReservas(reservas);
            }

            @Override
            public void onError(Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void listaHorariosReservadosActividad(ResultadoReservasActividadCallback callback) {
    ConexionDB.getReservasActividadProximos7Dias(new ConexionDB.ResultadoReservasActividadCallback() {
        @Override
        public void onResultadoReservasActividad(ArrayList<ReservaActividad> reservas) {
            callback.onResultadoReservasActividad(reservas);
        }

        @Override
        public void onError(Throwable t) {
            callback.onError(t);
        }
    });
}


    public void recibirDatosPistas() {
        recyclerView_actividad.setHasFixedSize(true);
        recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Llamada al método listaHorariosReservados con un callback
        listaHorariosReservadosPista(new ResultadoReservasCallback() {
            @Override
            public void onResultadoReservas(ArrayList<ReservaPista> reservas) {
                listaReservasPistas = reservas;
                boolean repetido = false;
                ArrayList<ReservaPista> lista2 = new ArrayList<>();
                ArrayList<ReservaPista> listaDisponible = listaParaReservarPistas();
                ArrayList<ReservaPista> listaFinal = new ArrayList<>();
                // Comparación de listas para obtener los horarios disponibles
                for (ReservaPista reserva : listaReservasPistas) {
                    DayOfWeek nombreDia = reserva.getFecha_reserva().getDayOfWeek();
                    String dia = nombreDia.getDisplayName(TextStyle.SHORT, Locale.getDefault());
                    if (dia.equalsIgnoreCase(diaSeleccionado)
                            && reserva.getPista().getTipo_deporte().equals(opcion)) {
                        lista2.add(reserva);

                    }
                }

                if(!lista2.isEmpty()) {
                    for (ReservaPista reserva : listaDisponible) {
                        repetido = false;
                        for (ReservaPista reserva2 : lista2) {

                            if (reserva.getHorario_reservado().equals(reserva2.getHorario_reservado())
                                    && reserva.getPista().getTipo_deporte().equalsIgnoreCase(reserva2.getPista().getTipo_deporte())
                                    && reserva.getPista().getId_pista() == reserva2.getPista().getId_pista()) {

                                repetido = true;
                            }
                        }
                        if(!repetido){
                            listaFinal.add(reserva);
                        }
                    }
                }else{
                    listaFinal = listaDisponible;
                }
                    pistaAdapter = new ReservaAdapter(listaFinal, email, ReservaActivity.this, fecha_reserva);
                    recyclerView_actividad.setAdapter(pistaAdapter);

            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error al obtener la lista de reservas: " + t.getMessage());
            }
        });
    }

    public void cambiarColorDias() {
        LocalDate hoy = LocalDate.now();
        DayOfWeek dia = hoy.getDayOfWeek();

        for (TextView textView : diasSemana()) {
            if (textView.getText().toString().equalsIgnoreCase(dia.getDisplayName(TextStyle.SHORT, Locale.getDefault()))) {
                textView.setTextColor(getResources().getColor(R.color.teal_200));
                diaSeleccionado = textView.getText().toString();
                seleccionarDia(textView);

                if(seleccionaPista()){
                    recibirDatosPistas();
                }else if(seleccionaGim()){
                    recibirDatosActividad();
                }else{
                       recibirDatosPiscina();
                }

            } else {
                textView.setTextColor(getResources().getColor(R.color.white));
            }

            textView.setOnClickListener(v -> {
                for (TextView tv : diasSemana()) {
                    if (tv == textView) {
                        tv.setTextColor(getResources().getColor(R.color.teal_200));
                        diaSeleccionado = textView.getText().toString();
                        seleccionarDia(tv);

                        if(seleccionaPista()){
                            recibirDatosPistas();
                        }else if(seleccionaGim()){
                            recibirDatosActividad();
                        }else{
                             recibirDatosPiscina();
                        }

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
        extra = getIntent().getExtras();
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
        lista.addAll(horarioPistas(pista1, pista2, diaSeleccionado));

      //  Collections.sort(horarioPistas(pista1, pista2, diaSeleccionado));

        return lista;
    }

    public ArrayList<ReservaPista> horarioPistas(Pista pista1, Pista pista2, String dia) {
        ArrayList<ReservaPista> lista = new ArrayList<>();

        String[] dias = {"LUN.", "MAR.", "MIÉ.", "JUE.", "VIE.", "SÁB.", "DOM."};
        String[] horarios;
        if(dia.equalsIgnoreCase("SÁB.") || dia.equalsIgnoreCase("DOM.")) {
           horarios = new String[]{"10:00 - 11:30", "11:30 - 13:00"};
        }else{
            horarios = new String[]{"10:00 - 11:30", "11:30 - 13:00", "15:00 - 16:30", "16:30 - 18:00", "19:30 - 21:00", "21:00 - 22:30"};
        }

        for (String nombreDia : dias) {
            if (nombreDia.equals(dia)) {
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

    public ArrayList<ReservaActividad> horarioActividades(Actividad a, String dia) {
        ArrayList<ReservaActividad> lista = new ArrayList<>();

        String[] dias = {"LUN.", "MAR.", "MIÉ.", "JUE.", "VIE.", "SÁB.", "DOM."};
        String[] horarios;
        if (dia.equalsIgnoreCase("SÁB.") || dia.equalsIgnoreCase("DOM.")) {
            horarios = new String[]{"10:00 - 11:00", "12:00 - 13:00"};
        } else {
            horarios = new String[]{"10:00 - 11:00", "12:00 - 13:00", "17:00 - 18:00", "20:00 - 21:00"};
        }

        for (String nombreDia : dias) {
            if (nombreDia.equals(dia)) {
                for (String horario : horarios) {
                    ReservaActividad actividad = new ReservaActividad(horario, a);
                    lista.add(actividad);
                }
            }

        }
        return lista;
    }


    public ArrayList<ReservaActividad> listaParaReservarActividadesGim() {
        extra = getIntent().getExtras();
        opcion = extra.getString("DESCRIPCION_ACTIVIDAD");
        Actividad actividad = null;
        switch (opcion.toLowerCase()) {
            case "spinning":
                  actividad = new Actividad(1,"Spinning",20,
                          "https://firebasestorage.googleapis.com/v0/b/polideportivo-" +
                                  "5627a.appspot.com/o/fotos%2Fspinning2.jpeg?alt=media&token=" +
                                  "0453f015-677d-4dc0-899e-50a9444fceb9",
                          "Sala 1",1);
                break;
            case "zumba":
                actividad = new Actividad(8,"Zumba", 30,
                        "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a." +
                                "appspot.com/o/fotos%2Fzumba.jpg?alt=media&token=c3976a86-eca2-" +
                                "4de9-b481-d37e5120bc09","Sala 2",1);
                break;
            case "pilates":
                actividad = new Actividad(3,"Pilates",30,
                        "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a.appspot." +
                                "com/o/fotos%2Fpilates.jpg?alt=media&token=207bc70e-" +
                                "704e-4551-a7dc-4099d73475eb","Sala 3",1);
                break;
            case "boxeo":
                 actividad = new Actividad(2,"Boxeo",10,
                         "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a." +
                                 "appspot.com/o/fotos%2Fboxeo.jpg?alt=media&token=5f5bd7a5-" +
                                 "87ca-4e94-bfc0-09a375a76eb1","Sala 4",1);
                break;
            case "karate":
                  actividad = new Actividad(5,"Karate",20,
                          "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a." +
                                  "appspot.com/o/fotos%2Fkarate.jpg?alt=media&token=e017722c-" +
                                  "a8bf-4275-8498-fbf2b0530da9","Sala 5",1);
                break;

            default:

                break;
        }

        ArrayList<ReservaActividad> listaActividades = new ArrayList<>();
        listaActividades.addAll(horarioActividades(actividad,diaSeleccionado));

        // Ordenamiento de la lista por el horario de reserva
        //  Collections.sort(horarioActividades(actividad a));

        return listaActividades;
    }

    private ArrayList<ReservaActividad> listaParaReservarActividadesPiscina() {
        extra = getIntent().getExtras();
        opcion = extra.getString("DESCRIPCION_ACTIVIDAD");
        Actividad actividad = null;
        switch (opcion.toLowerCase()) {
            case "natación":
                actividad = new Actividad(7,"Natación",20,
                        "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a." +
                                "appspot.com/o/fotos%2Fnatacion.jpg?alt=media&token=97d591bb-" +
                                "20a6-45ad-b8cd-b08c60a69ff5",
                        "Piscina 1",2);
                break;
            case "waterpolo":
                actividad = new Actividad(6,"Waterpolo", 20,
                        "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a." +
                                "appspot.com/o/fotos%2Fwaterpolo.jpg?alt=media&token=7b2cf20a-" +
                                "7fed-4884-bd57-429e7f7d32c3","Piscina 2",2);
                break;
            case "aquagim":
                actividad = new Actividad(4,"AquaGim",20,
                        "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a." +
                                "appspot.com/o/fotos%2Faquagim.jpg?alt=media&token=c3a25d2a-" +
                                "cac3-4e7b-a1e5-e7c6c782951c","Piscina 3",2);
                break;
            case "sincronizada":
                actividad = new Actividad(2,"Sincronizada",20,
                        "https://firebasestorage.googleapis.com/v0/b/polideportivo-5627a." +
                                "appspot.com/o/fotos%2Fnatacion_sin.jpg?alt=media&token=e2444d3f-" +
                                "f5bc-4233-b9fa-e0b101dea541","Piscina 4",2);
                break;

            default:

                break;
        }

        ArrayList<ReservaActividad> listaActividades = new ArrayList<>();
        listaActividades.addAll(horarioActividades(actividad,diaSeleccionado));

        // Ordenamiento de la lista por el horario de reserva
        //  Collections.sort(horarioActividades(actividad a));

        return listaActividades;
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
        }
    }

   public void recibirDatosActividad() {
       ArrayList<ReservaActividad> listaDisponible = listaParaReservarActividadesGim();

           recyclerView_actividad.setHasFixedSize(true);
           recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
           actividadAdapter = new ReservaActividadAdapter(listaDisponible, email, ReservaActivity.this, fecha_reserva);
           recyclerView_actividad.setAdapter(actividadAdapter);
       }


   public void recibirDatosPiscina(){
       recyclerView_actividad.setHasFixedSize(true);
       recyclerView_actividad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


       ArrayList<ReservaActividad> listaDisponible = listaParaReservarActividadesPiscina();


       actividadAdapter = new ReservaActividadAdapter(listaDisponible, email, ReservaActivity.this, fecha_reserva);
       recyclerView_actividad.setAdapter(actividadAdapter);
   }

    public boolean seleccionaPista(){
        extra = getIntent().getExtras();
        opcion = extra.getString("DESCRIPCION_ACTIVIDAD");
        if(opcion.equalsIgnoreCase("Pádel")
                || opcion.equalsIgnoreCase("Tenis")
                || opcion.equalsIgnoreCase("Baloncesto")
                || opcion.equalsIgnoreCase("Fútbol Sala")){
            return true;
        }
        return false;
    }

    public boolean seleccionaGim(){
        extra = getIntent().getExtras();
        opcion = extra.getString("DESCRIPCION_ACTIVIDAD");
        if(opcion.equalsIgnoreCase("Spinning")
                || opcion.equalsIgnoreCase("Zumba")
                || opcion.equalsIgnoreCase("Pilates")
                || opcion.equalsIgnoreCase("Boxeo")
                || opcion.equalsIgnoreCase("Karate")){
            return true;
        }
        return false;
    }

    public boolean seleccionaPiscina(){
        extra = getIntent().getExtras();
        opcion = extra.getString("DESCRIPCION_ACTIVIDAD");
        if(opcion.equalsIgnoreCase("Natación")
                || opcion.equalsIgnoreCase("WaterPolo")
                || opcion.equalsIgnoreCase("AquaGim")
                || opcion.equalsIgnoreCase("Sincronizada")){
            return true;
        }
        return false;
    }

}
