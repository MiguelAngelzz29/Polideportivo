package es.miguel.polideportivo_v2.data;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.miguel.polideportivo_v2.dominio.Actividad;
import es.miguel.polideportivo_v2.dominio.Cliente;
import es.miguel.polideportivo_v2.dominio.Pista;
import es.miguel.polideportivo_v2.dominio.ReservaActividad;
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class ConexionDB {
   //Consulta para misReservas
    public static void getListaReservas(String email, ResultadoReservasCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        com.google.firebase.Timestamp fechaHoy = null;
        CollectionReference collection = db.collection("ReservaPista");
        Query query = collection.whereEqualTo("id_cliente", email)
                .whereGreaterThanOrEqualTo("fecha_reserva", fechaHoy.now());
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) { // Verificar si no hay datos
                    callback.onResultadoReservas(new ArrayList<>());
                } else {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    ArrayList<ReservaPista> lista = new ArrayList<ReservaPista>();
                    for (DocumentSnapshot doc : documents) {
                        com.google.firebase.Timestamp fecha_reserva = doc.getTimestamp("fecha_reserva");
                        Instant instant = fecha_reserva.toDate().toInstant();
                        LocalDateTime fecha = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        String id_reserva = doc.getId();
                        String horario_reservado = doc.getString("horario_reservado");
                        double precio_pagado = doc.getLong("precio_pagado").doubleValue();
                        String id_cliente = doc.getString("id_cliente");
                        int id_pista = doc.getLong("id_pista").intValue();
                        getPista(id_pista, new ResultadoPistaCallback() {
                            @Override
                            public void onResultadoPista(Pista p) {
                                getCliente(email, new ResultadoClienteCallback() {
                                    @Override
                                    public void onResultadoCliente(Cliente cliente) {
                                        // Crear la ReservaPista con los datos de la consulta
                                        ReservaPista reservaPista = new ReservaPista(id_reserva,fecha, horario_reservado, precio_pagado, p, cliente);
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
                }
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public static void getPista(int id, ResultadoPistaCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Pista");
        Query query = collection.whereEqualTo("id", id);
        query.get().addOnCompleteListener((Task<QuerySnapshot> task) -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Pista pista = null;
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()){
                    // Verificar que los campos necesarios estén disponibles
                    if (snapshot.contains("tipo_deporte") && snapshot.contains("ubicacion") && snapshot.contains("imagen")) {
                        String tipo_deporte = snapshot.getString("tipo_deporte");
                        String ubicacion = snapshot.getString("ubicacion");
                        String descripcion = snapshot.getString("descripcion");
                        String imagen = snapshot.getString("imagen");
                        pista = new Pista(id, tipo_deporte, ubicacion,descripcion, imagen);
                    }
                }
                callback.onResultadoPista(pista);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public static void getCliente(String email, ResultadoClienteCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Cliente");
        Query query = collection.whereEqualTo("email", email);
        query.get().addOnCompleteListener((Task<QuerySnapshot> task) -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Cliente cliente = null;
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()){
                    // Verificar que los datos sean válidos antes de crear el objeto
                    if (snapshot.exists()) {
                        String id_cliente = snapshot.getId();
                        String nombre = snapshot.getString("nombre");
                        String primer_apellido = snapshot.getString("primer_apellido");
                        String segundo_apellido = snapshot.getString("segundo_apellido");
                        String direccion = snapshot.getString("direccion");
                        String email_cliente = snapshot.getString("email");
                        String telefono = snapshot.getString("telefono");
                       String tipo_abono = snapshot.getString("tipo_abono");

                        cliente = new Cliente(id_cliente,nombre, primer_apellido,segundo_apellido,
                                direccion,email_cliente,telefono,Integer.parseInt(tipo_abono));

                    }
                }
                callback.onResultadoCliente(cliente);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public static void getActividad(int id_act, ResultadoActividadCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Actividad");
        Query query = collection.whereEqualTo("id", id_act);
        query.get().addOnCompleteListener((Task<QuerySnapshot> task) -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Actividad actividad = null;
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()){
                    // Verificar que los campos necesarios estén disponibles
                    if (snapshot.contains("nombre") && snapshot.contains("capacidad") && snapshot.contains("imagen")) {

                        int id = snapshot.getLong("id").intValue();
                        String nombre1 = snapshot.getString("nombre");
                        String descripcion = snapshot.getString("descripcion");
                        int capacidad = snapshot.getLong("capacidad").intValue();
                        int numero_reservas = snapshot.getLong("numero_reservas").intValue();
                        String imagen = snapshot.getString("imagen");
                        String ubicacion = snapshot.getString("ubicacion");
                        int tipo_actividad = snapshot.getLong("tipo_actividad").intValue();
                        actividad = new Actividad(id,nombre1,descripcion,capacidad,numero_reservas,imagen,ubicacion,tipo_actividad);
                    }
                }
                callback.onResultadoActividad(actividad);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public static void getReservasPistasProximos7Dias(ResultadoReservasCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("ReservaPista");
        LocalDateTime today = LocalDateTime.now().minusDays(1);
        LocalDateTime sevenDaysAhead = today.plusDays(7);

        //Formatear las fechas en un string con formato correcto
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todayFormatted = today.format(formatter);
        String sevenDaysAheadFormatted = sevenDaysAhead.format(formatter);

        // Convertir las fechas formateadas en objetos Timestamp
        Timestamp todayTimestamp = Timestamp.valueOf(todayFormatted);
        Timestamp sevenDaysAheadTimestamp = Timestamp.valueOf(sevenDaysAheadFormatted);

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
                    getPista(id_pista, new ResultadoPistaCallback() {
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

    public static void getReservasActividadProximos7Dias(ResultadoReservasActividadCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("ReservaActividad");
        LocalDateTime today = LocalDateTime.now().minusDays(1);
        LocalDateTime sevenDaysAhead = today.plusDays(7);

        //Formatear las fechas en un string con formato correcto
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todayFormatted = today.format(formatter);
        String sevenDaysAheadFormatted = sevenDaysAhead.format(formatter);

        // Convertir las fechas formateadas en objetos Timestamp
        Timestamp todayTimestamp = Timestamp.valueOf(todayFormatted);
        Timestamp sevenDaysAheadTimestamp = Timestamp.valueOf(sevenDaysAheadFormatted);

        Query query = collection.whereGreaterThanOrEqualTo("fecha_reserva", todayTimestamp)
                .whereLessThanOrEqualTo("fecha_reserva", sevenDaysAheadTimestamp);
        query.get().addOnCompleteListener((Task<QuerySnapshot> task) -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                ArrayList<ReservaActividad> lista = new ArrayList<>();
                for (DocumentSnapshot doc : documents) {
                    com.google.firebase.Timestamp fecha_reserva = doc.getTimestamp("fecha_reserva");
                    Instant instant = fecha_reserva.toDate().toInstant();
                    LocalDateTime fecha = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    String horario_reservado = doc.getString("horario_reservado");
                    int id = doc.getLong("id_actividad").intValue();
                    getActividad(id, new ResultadoActividadCallback() {
                        @Override
                        public void onResultadoActividad(Actividad a) {
                            ReservaActividad reservaActividad = new ReservaActividad(fecha, horario_reservado, a);
                            lista.add(reservaActividad);
                            if (lista.size() == documents.size()) {
                                callback.onResultadoReservasActividad(lista);
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

    public static int getNumeroReservas(int id, Timestamp fecha, String horario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Task<QuerySnapshot> query = db.collection("ReservaActividad")
                .whereEqualTo("id_actividad", id)
                .whereEqualTo("fecha_reserva", fecha)
                .whereEqualTo("horario_reservado", horario)
                .get();

        // Esperar la respuesta asíncrona de la consulta
        try {
            QuerySnapshot querySnapshot = Tasks.await(query);
            int count = querySnapshot.size();
            Log.d("Reservas count", count + " reservas encontradas");
            return count;
        } catch (ExecutionException | InterruptedException e) {
            Log.w("Error", "Error al obtener las reservas.", e);
            return -1;
        }
    }

    public static void getListaReservasActividades(String email, ResultadoReservasActividadCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        com.google.firebase.Timestamp fechaHoy = null;
        CollectionReference collection = db.collection("ReservaActividad");
        Query query = collection.whereEqualTo("id_cliente", email)
                .whereGreaterThanOrEqualTo("fecha_reserva", fechaHoy.now());
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) { // Verificar si no hay datos
                    callback.onResultadoReservasActividad(new ArrayList<>());
                } else {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    ArrayList<ReservaActividad> lista = new ArrayList<>();
                    for (DocumentSnapshot doc : documents) {
                        com.google.firebase.Timestamp fecha_reserva = doc.getTimestamp("fecha_reserva");
                        Instant instant = fecha_reserva.toDate().toInstant();
                        LocalDateTime fecha = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        String id_reserva = doc.getId();
                        String horario_reservado = doc.getString("horario_reservado");
                        String id_cliente = doc.getString("id_cliente");
                        int id_actividad = doc.getLong("id_actividad").intValue();
                        getActividad(id_actividad, new ResultadoActividadCallback() {
                            @Override
                            public void onResultadoActividad(Actividad a) {
                                getCliente(email, new ResultadoClienteCallback() {
                                    @Override
                                    public void onResultadoCliente(Cliente cliente) {
                                        // Crear la ReservaActividad con los datos de la consulta
                                        ReservaActividad reservaActividad = new ReservaActividad(id_reserva,fecha, horario_reservado,a, cliente);
                                        // Agregar la reserva a la lista
                                        lista.add(reservaActividad);

                                        // Verificar que se hayan cargado todas las reservas
                                        if (lista.size() == documents.size()) {
                                            callback.onResultadoReservasActividad(lista);
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
                }
            } else {
                callback.onError(task.getException());
            }
        });
    }

    public static void eliminarReservaPista(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reservasRef = db.collection("ReservaPista");

        // Crear la referencia al documento que se va a eliminar
        DocumentReference reservaRef = reservasRef.document(id);

        reservaRef.delete().addOnSuccessListener(aVoid -> {
            Log.d(TAG, "Documento eliminado con éxito.");
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error al eliminar documento", e);
        });
    }

    public static void eliminarReservaActividad(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reservasRef = db.collection("ReservaActividad");

        // Crear la referencia al documento que se va a eliminar
        DocumentReference reservaRef = reservasRef.document(id);

        reservaRef.delete().addOnSuccessListener(aVoid -> {
            Log.d(TAG, "Documento eliminado con éxito.");
        }).addOnFailureListener(e -> {
            Log.w(TAG, "Error al eliminar documento", e);
        });
    }

    public interface ResultadoReservasCallback {
        void onResultadoReservas(ArrayList<ReservaPista> reservas);
        void onError(Throwable t);
    }

    public interface ResultadoReservasActividadCallback {
        void onResultadoReservasActividad(ArrayList<ReservaActividad> reservas);
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

    public interface ResultadoActividadCallback {
        void onResultadoActividad(Actividad actividad);
        void onError(Throwable t);
    }
}

