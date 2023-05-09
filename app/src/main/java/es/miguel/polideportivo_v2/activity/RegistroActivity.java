package es.miguel.polideportivo_v2.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.miguel.polideportivo_v2.R;



public class RegistroActivity extends AppCompatActivity {

    private EditText nombre, primerApellido, segundoApellido, direccion, email, movil, password, repitePassword;
    private FloatingActionButton btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        declararVariables();

        // Configurar el botón de registro
        btn_registro.setOnClickListener(v -> {
            recogerDatos();
            limpiarDatos();
            btn_registro.setVisibility(View.GONE);
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        });

        // Escuchar cambios en los campos de entrada
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizarVisibilidadBotonRegistro();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        nombre.addTextChangedListener(watcher);
        primerApellido.addTextChangedListener(watcher);
        segundoApellido.addTextChangedListener(watcher);
        direccion.addTextChangedListener(watcher);
        email.addTextChangedListener(watcher);
        movil.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        repitePassword.addTextChangedListener(watcher);
    }

    private void declararVariables() {
        nombre = findViewById(R.id.registroNombre);
        primerApellido = findViewById(R.id.registro1Apellido);
        segundoApellido = findViewById(R.id.registro2Apellido);
        direccion = findViewById(R.id.registroDireccion);
        email = findViewById(R.id.registroEmail);
        movil = findViewById(R.id.registroMovil);
        password = findViewById(R.id.registroPassword);
        repitePassword = findViewById(R.id.registroRepitePassword);
        btn_registro = findViewById(R.id.btn_registro);

        // Configurar la visibilidad inicial del botón de registro
        actualizarVisibilidadBotonRegistro();
    }

    private void actualizarVisibilidadBotonRegistro() {
        boolean camposCompletos = !nombre.getText().toString().isEmpty()
                && !primerApellido.getText().toString().isEmpty()
                && !segundoApellido.getText().toString().isEmpty()
                && !direccion.getText().toString().isEmpty()
                && !email.getText().toString().isEmpty()
                && !movil.getText().toString().isEmpty()
                && !password.getText().toString().isEmpty()
                && !repitePassword.getText().toString().isEmpty();

        btn_registro.setVisibility(camposCompletos ? View.VISIBLE : View.GONE);
    }

    private void recogerDatos() {
        // Obtener la instancia de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Crear un mapa con los atributos del usuario
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", nombre.getText().toString());
        user.put("1apellido", primerApellido.getText().toString());
        user.put("2apellido", segundoApellido.getText().toString());
        user.put("direccion", direccion.getText().toString());
        user.put("email", email.getText().toString());
        user.put("tipo_abono", "0");

      /*  // Crear un mapa con los filtros de contraseÃ±a
        List<Map<String, String>> filters = new ArrayList<>();
        Map<String, String> filtro1 = new HashMap<>();
        filtro1.put("tipo", "Mayusculas");
        Map<String, String> filtro2 = new HashMap<>();
        filtro2.put("tipo", "Minusculas");
        filters.add(filtro1);
        filters.add(filtro2);
        Map<String, Object> password = new HashMap<>();
        password.put("password", "");
        password.put("filters", filters);*/
        user.put("password", password.getText().toString());
        user.put("tlf_movil", movil.getText().toString());

        // Añadir el documento a Firestore (la colección "usuarios" no tiene por qué existir previamente)
        db.collection("Cliente").add(user)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Documento creado con ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error al crear el documento", e));
    }

    private void limpiarDatos() {
        nombre.setText("");
        primerApellido.setText("");
        segundoApellido.setText("");
        direccion.setText("");
        email.setText("");
        movil.setText("");
        password.setText("");
        repitePassword.setText("");
    }
}