package es.miguel.polideportivo_v2.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import es.miguel.polideportivo_v2.dominio.Cliente;
import es.miguel.polideportivo_v2.dominio.Pista;
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class ConexionDB {

    public static void GetListaReservas(String email, ResultadoReservasCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        com.google.firebase.Timestamp fechaHoy = null;
        CollectionReference collection = db.collection("ReservaPista");
        Query query = collection.whereEqualTo("id_cliente", email)
                .whereGreaterThanOrEqualTo("fecha_reserva", fechaHoy.now());
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                ArrayList<ReservaPista> lista = new ArrayList<ReservaPista>();
                for (DocumentSnapshot doc : documents) {
                    com.google.firebase.Timestamp fecha_reserva = doc.getTimestamp("fecha_reserva");
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

    public static void GetPista(int id, ResultadoPistaCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Pista");
        Query query = collection.whereEqualTo("id", id);
        query.get().addOnCompleteListener((Task<QuerySnapshot> task) -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Pista pista = null;
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()){
                    pista = snapshot.toObject(Pista.class);
                }
                callback.onResultadoPista(pista);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public static void GetCliente(String email, ResultadoClienteCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Cliente");
        Query query = collection.whereEqualTo("email", email);
        query.get().addOnCompleteListener((Task<QuerySnapshot> task) -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Cliente cliente = null;
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()){
                    cliente = snapshot.toObject(Cliente.class);
                }
                callback.onResultadoCliente(cliente);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public static void getReservasProximos7Dias(ResultadoReservasCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("ReservaPista");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime sevenDaysAhead = today.plusDays(7);
        Timestamp todayTimestamp = Timestamp.valueOf(today.toString());
        Timestamp sevenDaysAheadTimestamp = Timestamp.valueOf(sevenDaysAhead.toString());
        Query query = collection.whereGreaterThanOrEqualTo("fecha_reserva", todayTimestamp)
                .whereLessThanOrEqualTo("fecha_reserva", sevenDaysAheadTimestamp);
        query.get().addOnCompleteListener((Task<QuerySnapshot> task) -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                ArrayList<ReservaPista> lista = new ArrayList<>();
                for (DocumentSnapshot doc : documents) {
                    com.google.firebase.Timestamp fecha_reserva = doc.getTimestamp("fecha_reserva");
                    Instant instant = fecha_reserva.toDate().toInstant();
                    LocalDateTime fecha = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    String horario_reservado = doc.getString("horario_reservado");
                    double precio_pagado = doc.getLong("precio_pagado").doubleValue();
                    int id_pista = doc.getLong("id_pista").intValue();
                    GetPista(id_pista, new ResultadoPistaCallback() {
                        @Override
                        public void onResultadoPista(Pista p) {
                            ReservaPista reservaPista = new ReservaPista(fecha, horario_reservado, precio_pagado, p);
                            lista.add(reservaPista);
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
            } else {
                callback.onError(task.getException());
            }
        });
    }


    public interface ResultadoReservasCallback {
        void onResultadoReservas(ArrayList<ReservaPista> reservas);
        void onError(Throwable t);
    }

    public interface ResultadoPistaCallback {
        void onResultadoPista(Pista p);
        void onError(Throwable t);
    }

    public interface ResultadoClienteCallback {
        void onResultadoCliente(Cliente cliente);
        void onError(Throwable t);
    }
}