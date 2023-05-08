package es.miguel.polideportivo_v2.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import es.miguel.polideportivo_v2.R;
import es.miguel.polideportivo_v2.adaptador.MisReservasActividadesAdapter;
import es.miguel.polideportivo_v2.adaptador.MisReservasAdapter;
import es.miguel.polideportivo_v2.adaptador.ReservaAdapter;
import es.miguel.polideportivo_v2.adaptador.SinReservasAdapter;
import es.miguel.polideportivo_v2.data.ConexionDB;
import es.miguel.polideportivo_v2.dominio.Cliente;
import es.miguel.polideportivo_v2.dominio.Pista;
import es.miguel.polideportivo_v2.dominio.ReservaPista;

public class MisReservasActivity extends AppCompatActivity {


    private ArrayList<ReservaPista> listaReservas;
    private RecyclerView rv;
    private MisReservasAdapter misReservas;
    private static final String TAG = "MisReservasActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

          Intent intent = getIntent();
          String email = intent.getStringExtra("EMAIL_INICIO");
          listaReservas = new ArrayList<>();
        ConexionDB.getListaReservas(email, new ConexionDB.ResultadoReservasCallback() {
            @Override
            public void onResultadoReservas(ArrayList<ReservaPista> reservas) {
                listaReservas = reservas;
                rv = findViewById(R.id.rv_mis_reservas);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                if (listaReservas.size() > 0) {
                Collections.sort(listaReservas);
                misReservas = new MisReservasAdapter(listaReservas, email, MisReservasActivity.this, listaReservas.get(0).getFecha_reserva());
                rv.setAdapter(misReservas);

            }else {
                    // Si no hay reservas, mostrar algún mensaje o hacer alguna acción
                    SinReservasAdapter sinReserva = new SinReservasAdapter();
                    rv.setAdapter(sinReserva);
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "Error al obtener lista de reservas: ", t);
            }
        });
    }
}