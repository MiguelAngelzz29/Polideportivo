package es.miguel.polideportivo_v2.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.MisReservasAdapter;
import es.miguel.polideportivo_v2.adaptador.ReservaAdapter;
import es.miguel.polideportivo_v2.dominio.Cliente;
import es.miguel.polideportivo_v2.dominio.Pista;
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class MisReservasActivity extends AppCompatActivity {

    private String email;
    private ArrayList<ReservaPista> listaReservas;
    private RecyclerView rv;
    private MisReservasAdapter misReservas;
    private static final String TAG = "MisReservasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);
        // obtenemos la referencia a las SharedPreferences
        SharedPreferences prefs = getSharedPreferences("EMAIL_PREF", MODE_PRIVATE);

        // obtenemos el valor de una variable
         email = prefs.getString("EMAIL_PRE", "valorPredeterminado");


        listaReservas = new ArrayList<>();
        GetListaReservas(new ResultadoReservasCallback() {
            @Override
            public void onResultadoReservas(ArrayList<ReservaPista> reservas) {
                listaReservas = reservas;
                rv = findViewById(R.id.rv_mis_reservas);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                misReservas = new MisReservasAdapter(listaReservas, email,MisReservasActivity.this);
                rv.setAdapter(misReservas);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "Error al obtener lista de reservas: ", t);
            }
        });


    }

    public void GetListaReservas(ResultadoReservasCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("ReservaPista");
        Query query = collection.whereEqualTo("id_cliente", email);
        LocalDate fechaActual = LocalDate.now();
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                ArrayList<ReservaPista> lista = new ArrayList<ReservaPista>();
                for (DocumentSnapshot doc : documents) {
                    Timestamp fecha_reserva = doc.getTimestamp("fecha_reserva");
                    Instant instant = fecha_reserva.toDate().toInstant();
                    LocalDateTime fecha = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    String horario_reservado = doc.getString("horario_reservado");
                    double precio_pagado = doc.getLong("precio_pagado").doubleValue();
                    String id_cliente = doc.getString("id_cliente");
                    int id_pista = doc.getLong("id_pista").intValue();
                    GetPista(id_pista, new ResultadoPistaCallback() {
                        @Override
                        public void onResultadoPista(Pista p) {
                            GetCliente(email, new ResultadoClienteCallback() {
                                @Override
                                public void onResultadoCliente(Cliente cliente) {
                                    // Crear la ReservaPista con los datos de la consulta
                                    ReservaPista reservaPista = new ReservaPista(fecha, horario_reservado, precio_pagado, p, cliente);
                                    // Agregar la reserva a la lista
                                    lista.add(reservaPista);

                                    // Verificar que se hayan cargado todas las reservas
                                    if (lista.size() == documents.size()) {
                                        callback.onResultadoReservas(lista);
                                    }
                                }

                                @Override
                                public void onError(Throwable t) {
                                    callback.onError(t);
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable t) {
                            callback.onError(t);
                        }
                    });
                }
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public void GetPista(int id, ResultadoPistaCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Pista");
        Query query = collection.whereEqualTo("id", id);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                for (DocumentSnapshot doc : documents) {
                    int id_pista = doc.getLong("id").intValue();
                    String imagen = doc.getString("imagen");
                    String tipo_deporte = doc.getString("tipo_deporte");
                    String ubicacion = doc.getString("ubicacion");
                    Pista p = new Pista(id_pista, tipo_deporte, ubicacion, imagen);
                    callback.onResultadoPista(p);
                }
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public void GetCliente(String email, ResultadoClienteCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Cliente");
        Query query = collection.whereEqualTo("email", email);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                for (DocumentSnapshot doc : documents) {
                    String id = doc.getId();
                    String nombre = doc.getString("nombre");
                    String primer_apellido = doc.getString("1apellido");
                    String segundo_apellido = doc.getString("2apellido");
                    String direccion = doc.getString("direccion");
                    String emailCliente = doc.getString("email");
                    String telefono = doc.getString("tlf_movil");
                    String tipo_abono = doc.getString("tipo_abono");
                    int abono = Integer.parseInt(tipo_abono);
                    Cliente cliente = new Cliente(id, nombre, primer_apellido, segundo_apellido, direccion, emailCliente, telefono, abono);
                    callback.onResultadoCliente(cliente);
                }
            } else {
                callback.onError(task.getException());
            }
        });
    }

    interface ResultadoReservasCallback {
        void onResultadoReservas(ArrayList<ReservaPista> reservas);

        void onError(Throwable t);
    }

    interface ResultadoPistaCallback {
        void onResultadoPista(Pista p);
        void onError(Throwable t);
    }

    interface ResultadoClienteCallback {
        void onResultadoCliente(Cliente cliente);
        void onError(Throwable t);
    }
}